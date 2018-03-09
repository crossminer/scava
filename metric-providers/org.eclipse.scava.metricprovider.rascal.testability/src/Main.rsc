@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module Main

import Set;
import Relation;
import IO;
import util::ValueUI;
import util::Math;
import analysis::graphs::Graph;
extend lang::java::m3::Core;
import lang::java::m3::AST;
import JUnit4;
import Java;
import org::eclipse::scava::metricprovider::MetricProvider;
import String;


@metric{TestCoverage}
@doc{This is a static over-estimation of test coverage: which code is executed in the system when all JUnit test cases are executed? We approximate
this by using the static call graphs and assuming every method which can be called, will be called. This leads to an over-approximation,
as compared to a dynamic code coverage analysis, but the static analysis does follow the trend and a low code coverage here is an good indicator
for a lack in testing effort for the project.}
@friendlyName{Static Estimation of test coverage}
@appliesTo{java()}
@historic{}
real estimateTestCoverage(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
  m = systemM3(m3s, delta = delta);
  implicitContainment = getImplicitContainment(m);
  implicitCalls = getImplicitCalls(m, implicitContainment);
  inverseContainment = m@containment<1,0>;
  fullContainment = toMap(m@containment + implicitContainment);
  
  liftedInvocations = {};
  for (<caller, callee> <- m@methodInvocation) {
    if (isMethod(caller)) {
      liftedInvocations += { <caller, callee> };
      continue;
    }

    if (caller.scheme == "java+initializer") {
      assert size(inverseContainment[caller]) == 1 : "Error: Multiple parents for 1 entity";
      caller = getOneFrom(inverseContainment[caller]);
    }
    if (caller.scheme == "java+class" || caller.scheme == "java+anonymousClass" || caller.scheme == "java+enum") {
      for (meth <- (fullContainment[caller]?{}), meth.scheme == "java+constructor") {
        liftedInvocations += { <meth, callee> };
      }
    }
  }
  
  fullCallGraph = liftedInvocations + implicitCalls + m@methodOverrides<1,0>;
  allTestMethods = getJUnit4TestMethods(m) + getJUnit4SetupMethods(m);
  interfaceMethods = { meth | <entity, meth> <- m@containment, isMethod(meth), isInterface(entity) };
  declarations = { meth | meth <- m@declarations<0>, isMethod(meth) } - interfaceMethods - allTestMethods;
  set[loc] reachableMethods = { meth | meth <- reach(fullCallGraph, allTestMethods), meth in declarations };
  return round((100.0 * size(reachableMethods)) / size(declarations), 0.01);
}

/*  
 * Adding implicit calls between a constructor and its super
 */
private rel[loc, loc] getImplicitCalls(M3 m, rel[loc, loc] implicitContainment) {
  fullContainment = toMap(m@containment + implicitContainment);  
  
  rel[loc, loc] implicitCalls = {};
  
  impCont = implicitContainment<0>;
  
  for (<ch, par> <- m@extends) {
    if (par in impCont) {
      for (con <- (fullContainment[ch]?{}), con.scheme == "java+constructor") {
        assert(size(implicitContainment[par]) == 1) : "Found more than one implicit constuctor";
        implicitCalls += <con, getOneFrom(implicitContainment[par])>;
      }
    }
  }
  
  return implicitCalls;
}

/*
 * Adding implicit constructors to all classes that don't define any constructor
 */
private rel[loc, loc] getImplicitContainment(M3 m) {
  rel[loc, loc] implicitContainment = {};
  decls = m@declarations<0>;
  nameSet = toMap(m@names<1,0>);
  cMap = toMap(m@containment);
  for (cl <- decls, cl.scheme == "java+class" || cl.scheme == "java+anonymousClass" || cl.scheme == "java+enum", \abstract() notin m@modifiers[cl]) {
    allMeths = { candidate | candidate <- cMap[cl]?{}, isMethod(candidate) };
    
    if (!any(meth <- allMeths, meth.scheme == "java+constructor")) {
      possibleNames = nameSet[cl]? {};
      if(size(possibleNames) > 1) {
        println("Found more than one simple name entry for qualified name <cl>: <possibleNames>");
        println("Picking the closest match");
        possibleNames = { split("/", cl.path)[-1] };
      }
      className = isEmpty(possibleNames) ? "" : getOneFrom(possibleNames);
      defaultConstructorLOC = (cl+"<className>()")[scheme="java+constructor"];
      implicitContainment += <cl, defaultConstructorLOC>;
    }
  }
  
  return implicitContainment;
}

@metric{TestOverPublicMethods}
@doc{Number of JUnit tests averaged over the total number of public methods. Ideally all public methods are tested. With this number we
compute how far from the ideal situation the project is.}
@friendlyName{Number of JUnit tests averaged over the total number of public methods}
@appliesTo{java()}
@historic{}
real percentageOfTestedPublicMethods(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
  m = systemM3(m3s, delta = delta);
  onlyTestMethods = getJUnit4TestMethods(m);
  supportTestMethods = getJUnit4SetupMethods(m);
  interfaceMethods = { meth | <entity, meth> <- m@containment, isMethod(meth), isInterface(entity) };
  declarations = {meth | meth <- m@declarations<0>, isMethod(meth) } - interfaceMethods - onlyTestMethods - supportTestMethods;
  mMap = toMap(m@modifiers);
  allPublicMethods = { meth | meth <- declarations, \public() in (mMap[meth]?{}) };
  directlyCalledFromTestMethods = domainR(m@methodInvocation, onlyTestMethods);
  pbSet = directlyCalledFromTestMethods + (directlyCalledFromTestMethods o m@methodOverrides<1,0>);
  testedPublicMethods = rangeR(pbSet, allPublicMethods);
  return round((100.0 * size(range(testedPublicMethods)))/size(allPublicMethods), 0.01);
}

@metric{NumberOfTestMethods}
@doc{Number of JUnit test methods}
@friendlyName{Number of JUnit test methods. This is an intermediate absolute metric used to compute others. The bare metric is hard to compare between projects.}
@appliesTo{java()}
@historic
int numberOfTestMethods(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
  return size(getJUnit4TestMethods(systemM3(m3s, delta = delta)));
}


@metric{JavaUnitTestCoverage}
@doc{How well do the project's unit tests cover its code? A static approximation is done, measuring the code which would be executed if all JUnit tests are run. 
This analysis may produce a higher number than a dynamic analysis would (due to dynamic dispatch and overriding) but it indicates bad coverage easily and it follows the trend.}
@friendlyName{Java unit test coverage}
@uses{("TestOverPublicMethods": "testOverPublicMethods", "TestCoverage": "testCoverage", "TestCoverage.historic": "history")}
@appliesTo{java()}
Factoid JavaUnitTestCoverage(real testOverPublicMethods = -1.0, real testCoverage = -1.0, rel[datetime, real] history = {}) {
  sl = historicalSlope(history, 6);
                         
  expect = "";
                            
  switch (<sl < 0.1, -0.1 >= sl && sl <= 0.1, sl > 0.1>) {
    case <true   , _      , _     > : { expect = "The situation is getting worse in the last six months";  }
    case <_      , true   , _     > : { expect = "This situation is stable";  }
    case <_      , _      , true  > : { expect = "This situation is improving over the last six months";  }
  }
   
  if (testOverPublicMethods == -1.0 || testCoverage == -1.0) {
    throw undefined("Not enough test coverage data available", |tmp:///|);
  }

  stars = 1 + toInt(testCoverage / 25.0);

  if (stars > 4) {
    stars = 4;
  }

  txt = "The percentage of methods covered by unit tests is estimated at <round(testCoverage, 0.01)>%. <expect>. The estimated coverage of public methods is <round(testOverPublicMethods, 0.01)>%";
  
  return factoid(txt, starLookup[stars]); 
}
