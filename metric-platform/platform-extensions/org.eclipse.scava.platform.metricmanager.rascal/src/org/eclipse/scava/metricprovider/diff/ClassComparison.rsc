@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module org::eclipse::scava::metricprovider::diff::ClassComparison

import IO;
import ValueIO;
import Map;
import List;
import Set;
import util::Math;
import DateTime;
import Relation;

import lang::java::m3::Core;
import org::eclipse::scava::metricprovider::diff::DataType;

public set[ClassChange] getClassChanges(M3 newModel) = { addedClass(c) | c <- newModel@declarations<0>, isClass(c) || isInterface(c) || c.scheme == "java+enum" };

public set[ClassChange] getClassChanges(M3 oldModel, M3 newModel, set[FieldChange] fieldChanges, set[MethodChange] methodChanges) {
    
  set [ClassChange] classChanges = {};
  set [ClassChange] tempClasses = getChangedAddedRemovedClasses(oldModel, newModel) 
                  + getClassesWithContentChanges(oldModel, newModel, fieldChanges, methodChanges);
  for (aClass <- sanitizeClassChanges(tempClasses) ) { classChanges += aClass; }
  return classChanges;
}

// Return the set of ClassChanges for added and removed classes, and also 
// for the classes for which modifiers have changed or are deprecated
private set [ClassChange] getChangedAddedRemovedClasses(M3 oldModel, M3 newModel) {
    set [ClassChange] changedClassesSet = {};
  set [loc] oldClasses ={ c | c <- oldModel@declarations<0>, isClass(c) || isInterface(c) || c.scheme == "java+enum" };
  set [loc] newClasses = { c | c <- newModel@declarations<0>, isClass(c) || isInterface(c) || c.scheme == "java+enum" };
  set [loc] addedClasses = newClasses - oldClasses;
  set [loc] removedClasses = oldClasses  - newClasses;
  set [loc] commonClasses = oldClasses & newClasses;
  
  //set[loc] oldDeprecations = findDeprecations(oldModel);
  //set[loc] newDeprecations = findDeprecations(newModel);

  map[loc definition, set[Modifier] modifier] oldModifiers = index(oldModel@modifiers);
  map[loc definition, set[Modifier] modifier] newModifiers = index(newModel@modifiers);
  
  for ( aClass <- addedClasses) { changedClassesSet += addedClass(aClass); }
  for ( rClass <- removedClasses) { changedClassesSet +=  deletedClass(rClass); }
  //  //
  for (loc oneClass <- commonClasses) {   
    set[Modifier] oldClassModifiers = oneClass in oldModifiers ? oldModifiers[oneClass] : {};
      set[Modifier] newClassModifiers = oneClass in newModifiers ? newModifiers[oneClass] : {};
    
    if  (oldClassModifiers != newClassModifiers) { 
      changedClassesSet += classModifierChanged(oneClass, oldClassModifiers, newClassModifiers);
    } 
    //else {
    //  if ( isDeprecated(oneClass, oldDeprecations, newDeprecations))  {
    //    changedClassesSet += classDeprecated(oneClass);
    //  }
    //  else 
    //    if ( isUndeprecated(oneClass, oldDeprecations, newDeprecations, newClasses)) {
    //    changedClassesSet += classUndeprecated(oneClass);         
    //  }
    //}
  }
  return changedClassesSet;
}

// Get the classes which will be marked as changed because they contain
// a changed, deleted or removed field/method.
private set [ClassChange] getClassesWithContentChanges(M3 oldModel, M3 newModel,
                          set[FieldChange] fieldChanges, 
                          set[MethodChange] methodChanges) {
    map[loc classLoc, set[loc] contentLocs] changes = ();
    set[loc] emptySet = {};
  map[loc enclosed, loc enclosing] oldEnclosings = getEnclosings(oldModel@containment);
  map[loc enclosed, loc enclosing] newEnclosings = getEnclosings(newModel@containment);
  
  for (FieldChange fieldChange <- fieldChanges) {
    visit(fieldChange) {
      case fieldModifierChanged(locator, _, _) : {
        loc classLocator = oldEnclosings[locator];
        changes[classLocator]? emptySet += {locator};
      }
      case fieldTypeChanged(locator, _, _) : {
        loc classLocator = oldEnclosings[locator];
        changes[classLocator]? emptySet += {locator};
      }
      case fieldDeprecated(locator) : {
        loc classLocator = oldEnclosings[locator];
        changes[classLocator]? emptySet += {locator};
      }
      case fieldUndeprecated(locator) : {
        loc classLocator = oldEnclosings[locator];
        changes[classLocator]? emptySet += {locator};
      }
      case addedField(locator): {
        loc classLocator = newEnclosings[locator];
        changes[classLocator]? emptySet += {locator};
      }
      case deletedField(locator): {
        loc classLocator = oldEnclosings[locator];
        changes[classLocator]? emptySet += {locator};
      }
    }
  }
  
  for (MethodChange methodChange <- methodChanges) {
    visit(methodChange) {
      case deprecated(locator): {
        loc classLocator = oldEnclosings[locator];
        changes[classLocator]? emptySet += {locator};
      }
      case undeprecated(locator): {
        loc classLocator = oldEnclosings[locator];
        changes[classLocator]? emptySet += {locator};
      }
      case signatureChanged(old,_): {
        loc classLocator = oldEnclosings[old];
        changes[classLocator]? emptySet += {locator};
      }
      case returnTypeChanged(locator, _, _): {
        loc classLocator = oldEnclosings[locator];
        changes[classLocator]? emptySet += {locator};
      }
      case modifierChanged(locator, _, _): {
        loc classLocator = oldEnclosings[locator];
        changes[classLocator]? emptySet += {locator};
      }
      case added(locator): {
        loc classLocator = newEnclosings[locator];
        changes[classLocator]? emptySet += {locator};
      }
      case deleted(locator): {
        loc classLocator = oldEnclosings[locator];
        changes[classLocator]? emptySet += {locator};
      }
    }
  }
  return { classContentChanged(classLocator, changes[classLocator]) | loc classLocator <- changes };
}

private set [ClassChange] sanitizeClassChanges(set [ClassChange] inputSet) {
    set [loc] addedClasses = {};
  set [loc] deletedClasses = {};
  set [loc] changedClasses = {};
  set [ClassChange] returnSet = {};
  visit (inputSet) {
    case addedClass(c) : addedClasses += c;
    case deletedClass(c): deletedClasses += c;         
    case classContentChanged (c, _) : changedClasses += c;
    case classModifierChanged (c, _, _) : changedClasses += c;
    case classDeprecated(c): changedClasses += c;
    case classUndeprecated(c): changedClasses += c;   
  } 
  visit (inputSet) {
    case cAdded:addedClass(_) : returnSet += cAdded;
    case cDeleted:deletedClass(_): returnSet += cDeleted;        
    case cChanged:classContentChanged (c, _): { 
      if (c notin addedClasses && c notin deletedClasses) { returnSet += cChanged;};      
    }
    case cChanged:classModifierChanged (c, _, _) : {
       returnSet += cChanged;
    }
    case cChanged:classDeprecated (c): { 
       returnSet += cChanged;     
    }
    case cChanged:classUndeprecated (c): { 
       returnSet += cChanged;     
    }
  }
  return returnSet;
}

public map[loc enclosed, loc enclosing] getEnclosings(rel[loc from, loc to] containment) {
	map[loc enclosed, loc enclosing] enclosings = ();
	for (tuple[loc from, loc to] r <- containment) {
	enclosings += (r.to : r.from);
	}
	return enclosings;
}
