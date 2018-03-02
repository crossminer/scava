@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module org::eclipse::scava::metricprovider::diff::MethodComparison

import IO;
import List;
import Set;
import Type;
import String;
import Relation;

import util::ValueUI;

import lang::java::m3::Core;
import lang::java::m3::AST;

import org::eclipse::scava::metricprovider::diff::DataType;

public set[MethodChange] getMethodChanges(M3 new) {
  return { added(m) | m <- new@declarations<0>, isMethod(m) };
}

public set[MethodChange] getMethodChanges(M3 old, M3 new) {
  
  map[loc, set[loc]] modelHierarchyOld = getModelHierarchy(old);
  map[loc, set[loc]] modelHierarchyNew = getModelHierarchy(new);
  
  //set[loc] deprecatedMethodsOld = findDeprecations(old);
  //set[loc] deprecatedMethodsNew = findDeprecations(new);
  
  loc class = |file:///|;
  loc package = |file:///|;
  
  set[MethodChange] methodTransitions = {};
  for (loc classOrInterface <- modelHierarchyOld) {
    class = classOrInterface;
    assert isClass(class) || isInterface(class) || class.scheme == "java+enum";
    package = getClassPackage(old, class);
    //assert isPackage(package);
    
    set[loc] methodsInClassOrInterfaceOld = modelHierarchyOld[classOrInterface];
    if (classOrInterface notin modelHierarchyNew) {
      //TODO: find out if we need to count class change here.
      continue;
    }
    
    set[loc] changedMethods = {};
    
    set[loc] methodsInClassOrInterfaceNew = modelHierarchyNew[classOrInterface];
    for (loc method <- methodsInClassOrInterfaceOld) {
        
        //Signature changes
        if (method notin methodsInClassOrInterfaceNew) {
          loc newMethod = findSignatureChange(method, methodsInClassOrInterfaceOld, methodsInClassOrInterfaceNew);
          if (newMethod != |file:///|) {
            MethodChange signatureChanged = signatureChanged(method, newMethod);
            methodTransitions += signatureChanged;
            changedMethods += method;
          }
        }
        
        ////Newly deprecated methods
        //if (method notin deprecatedMethodsOld && method in deprecatedMethodsNew) {
        //  MethodChange deprecated = deprecated(method);
        //  methodTransitions += deprecated;
        //            changedMethods += method;
        //      }
        //      
        ////Newly deprecated methods
        //if (isUndeprecated(method, deprecatedMethodsOld, deprecatedMethodsNew, methodsInClassOrInterfaceNew)) {
        //  MethodChange undeprecated = undeprecated(method);
        //  methodTransitions += undeprecated;
        //            changedMethods += method;
        //      }
              
              //Return type changed
              tuple[bool changed, TypeSymbol oldType, TypeSymbol newType] returnTypeComparison = findMethodReturnTypeChange(method, toMap(old@types), toMap(new@types));
              if (returnTypeComparison.changed) {
                MethodChange returnTypeChanged = returnTypeChanged(method, returnTypeComparison.oldType, returnTypeComparison.newType);
                methodTransitions += returnTypeChanged;
                changedMethods += method;
              }
              
              //Modifiers changes
              set[Modifier] oldMethodModifiers = old@modifiers[method];
              set[Modifier] newMethodModifiers = new@modifiers[method];
              if (method in methodsInClassOrInterfaceNew && oldMethodModifiers != newMethodModifiers) {
                MethodChange modifierChanged = modifierChanged(method, oldMethodModifiers, newMethodModifiers);
                methodTransitions += modifierChanged;
                changedMethods += method;
              }
              
              //Unchanged method
        if(method notin changedMethods) {
          MethodChange unchanged = unchanged(method);
          methodTransitions += unchanged;
        }
    }
    
    set[loc] addedMethods = methodsInClassOrInterfaceNew - methodsInClassOrInterfaceOld - changedMethods;
    for (loc addedMethod <- addedMethods) {
      MethodChange added = added(addedMethod);
      methodTransitions += added;;
    }

    set[loc] deletedMethods = methodsInClassOrInterfaceOld - methodsInClassOrInterfaceNew - changedMethods;
    for (loc deletedMethod <- deletedMethods) {
      methodTransitions += deleted(deletedMethod);
    }
  }
  
  return methodTransitions;
}

private loc findSignatureChange(loc method, set[loc] oldMethods, set[loc] newMethods) {
   tuple[str methodName, list[str] parameters] methodNameAndParametersOld = extractMethodNameAndParameters(method);
   for (loc newMethod <- newMethods) {
     tuple[str methodName, list[str] parameters] methodNameAndParametersPossibleNew = extractMethodNameAndParameters(newMethod);
     if (methodNameAndParametersPossibleNew.methodName == methodNameAndParametersOld.methodName && newMethod notin oldMethods) {
       return newMethod;
     }
   }
   return |file:///|; //Not found.
}

private tuple[bool changed, TypeSymbol old, TypeSymbol new] findMethodReturnTypeChange(loc method, map[loc, set[TypeSymbol]] oldTypes, map[loc, set[TypeSymbol]] newTypes) {
  try {
    set[TypeSymbol] oldMethodTypes = oldTypes[method];
    set[TypeSymbol] newMethodTypes = newTypes[method];
    TypeSymbol oldReturn = getOneFrom(oldMethodTypes).returnType;
    TypeSymbol newReturn = getOneFrom(newMethodTypes).returnType;
    return <oldReturn != newReturn, oldReturn, newReturn>;
  }
  catch x: {
    //println("Trouble analysing return types for method <method>.");
    return <false, \void(), \void()>;
  }
}

private tuple[str, list[str]] extractMethodNameAndParameters(loc method) {
  str fullUri = method.uri;
  
  str methodSegment = last(split("/", fullUri));
  int openingBracket = findFirst(methodSegment, "("), closingBracket = findFirst(methodSegment, ")");
  str methodName = substring(methodSegment, 0, openingBracket);
  str parametersSegment = substring(methodSegment, openingBracket+1, closingBracket);
  
  list[str] parameters = split(",", parametersSegment);
  return <methodName, parameters>;
}

public map[loc, set[loc]] getModelHierarchy(M3 model) {
    map[loc class, set[loc] methods] methodsPerClassInterface = ();
    set[loc] emptySet = {};
        /*
        TODO: find out if interface and class inner classes and methods are represented
        set[loc] projectInnerClasses = nestedClasses();
    */
      set[loc] projectClassesAndInterfaces = { c | c <- model@declarations<0>, isClass(c) || isInterface(c) || c.scheme == "java+enum" };
      //methodModifiersMap = toMap(model@modifiers);

    for(loc method <- {m | m <- model@declarations<0>, isMethod(m)}) {
    loc c = toLocation(replaceFirst(replaceFirst(method.parent.uri, "java+method", "java+class"), "java+constructor", "java+class"));
    loc i = toLocation(replaceFirst(replaceFirst(method.parent.uri, "java+method", "java+interface"), "java+constructor", "java+interface"));
    loc e = toLocation(replaceFirst(replaceFirst(method.parent.uri, "java+method", "java+enum"), "java+constructor", "java+enum"));
    loc parent = |file:///|;  
    
    if (c in projectClassesAndInterfaces) {
      parent = c; 
    } else if (i in projectClassesAndInterfaces) {
      parent = i; 
    } else if (e in projectClassesAndInterfaces) {
      parent = e; 
    } else {
      continue;
    }
    
    if (isClass(parent) || isInterface(parent) || parent.scheme == "java+enum") {
          //if (\public() in (methodModifiersMap[method]? ? methodModifiersMap[method] : {})) {
            methodsPerClassInterface[parent]? emptySet += { method };
          //}
        }
  }
    return methodsPerClassInterface;
}

@memo private map[loc,set[loc]] parents(M3 m) = toMap(invert(m@containment));

@doc { Return the package URI for a given class URI. }
private loc getClassPackage(M3 m, loc c) {
  set[loc] parents = parents(m)[c];
  if (isEmpty(parents)) {
    return |file:///|;
  }
  loc parent = getUniqueElement(parents);
  return isPackage(parent) ? parent : getClassPackage(m, parent);
}

private &T getUniqueElement(set[&T] s) {
  //assert size(s) == 1;
  return getOneFrom(s);
}
