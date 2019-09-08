@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module JUnit3

import lang::java::m3::Core;
import Set;
import String;
import Relation;

set[loc] jUnit3BaseClass = {
	|java+class:///junit/framework/TestCase|,
	|java+class:///TestCase| // failsafe
};

@memo
private set[loc] getTestClasses(M3 m) {
  return { candidate | <candidate, baseClass> <- m.extends+, baseClass in jUnit3BaseClass };
}

@memo
set[loc] getJUnit3TestMethods(M3 m) {
  rel[loc, str] invNames = invert(m.names);

  return
  	{ candidate | testClass <- getTestClasses(m), candidate <- methods(m, testClass),
  		isTestMethod(getOneFrom(invNames[candidate]) ? "") };
}

@memo
set[loc] getJUnit3SetupMethods(M3 m) {
  rel[loc, str] invNames = invert(m.names);

  return
  	{ candidate | testClass <- getTestClasses(m), candidate <- methods(m, testClass),
  		isTestSetup(getOneFrom(invNames[candidate]) ? "") };
}

private bool isTestMethod(str name) {
  return startsWith(name, "test");
}

private bool isTestSetup(str name) {
  return name == "setUp" || name == "tearDown";
}
