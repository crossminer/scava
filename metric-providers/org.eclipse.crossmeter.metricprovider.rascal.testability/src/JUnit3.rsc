@license{
Copyright (c) 2014 CROSSMETER Partners.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
}
module JUnit3

import lang::java::m3::Core;
import Set;

set[loc] jUnit3BaseClass = { |java+class:///junit/framework/TestCase|,
                             |java+class:///TestCase| // failsafe
                           };

@memo
private set[loc] getTestClasses(M3 m) {
  return { candidate | <candidate, baseClass> <- m@extends+, baseClass in jUnit3BaseClass };
}

@memo
set[loc] getJUnit3TestMethods(M3 m) {
  set[loc] result = {};
  for (testClass <- getTestClasses(m)) {
    set[loc] candidateMethods = { candidate | candidate <- m@containment[testClass], isMethod(candidate) };
    rel[loc, str] invertedNamesRel = m@names<1,0>;
    for (candidate <- candidateMethods) {
      if (nameStartsWithTest(getOneFrom(invertedNamesRel[candidate]))) {
        result += candidate;
      }
    }
  }
  return result;
}

set[loc] getJUnit3SetupMethods(M3 m) {
  set[loc] result = {};
  for (testClass <- getTestClasses(m)) {
    set[loc] candidateMethods = { candidate | candidate <- m@containment[testClass], isMethod(candidate) };
    rel[loc, str] invertedNamesRel = m@names<1,0>;
    for (candidate <- candidateMethods) {
      if (isTestSetup(getOneFrom(invertedNamesRel[candidate]))) {
        result += candidate;
      }
    }
  }
  return result;
}

private bool nameStartsWithTest(str name) {
  return /^test.*/ := name;
}

private bool isTestSetup(str name) {
  return name == "setUp" || name == "tearDown";
}
