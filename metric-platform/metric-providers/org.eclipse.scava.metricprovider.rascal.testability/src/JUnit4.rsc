@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module JUnit4

extend JUnit3;
import lang::java::m3::Core;
import Set;

set[loc] jUnit4TestSetupAnnotations = {
	|java+interface:///org/junit/After|,
	|java+interface:///org/junit/Before|,
	|java+interface:///org/junit/AfterClass|,
	|java+interface:///org/junit/BeforeClass|,
	// failsafes
	|java+class:///After|,
	|java+class:///Before|,
	|java+class:///AfterClass|,
	|java+class:///BeforeClass|
};
  
set[loc] jUnit4TestAnnotation = {
	|java+interface:///org/junit/Test|,
	// failsafe
	|java+class:///Test|
};

@memo
private set[loc] methodsWithIgnoreAnnotation(M3 m) {
  // We collect all methods that have ignore annotation as well as methods in classes that have ignore annotations
  set[loc] result = {};
  set[loc] jUnit4IgnoreAnnotation = { |java+interface:///org/junit/Ignore|, |java+class:///Ignore| };
  for (loc entity <- m.annotations) {
    //if (!(isEmpty(jUnit4IgnoreAnnotation & m.annotations[entity]))) {
    if (hasAnnotation(m, entity, jUnit4IgnoreAnnotation)) {
      if (isClass(entity)) {
        result += methods(m, entity);
      } else if (isMethod(entity)) {
        result += entity;
      }
    }
  }
  return result;
}

@memo
set[loc] getJUnit4TestMethods(M3 m) {
  return
  	  getJUnit3TestMethods(m)
  	+ { testMethod | testMethod <- methods(m), hasAnnotation(m, testMethod, jUnit4TestAnnotation) }
  	- methodsWithIgnoreAnnotation(m);
}

@memo
set[loc] getJUnit4SetupMethods(M3 m) {
  return
  	  getJUnit3SetupMethods(m)
  	+ { testMethod | testMethod <- methods(m), hasAnnotation(m, testMethod, jUnit4TestSetupAnnotations) }
  	- methodsWithIgnoreAnnotation(m);
}

// Ugly, but *much* faster
private bool hasAnnotation(M3 m, loc l, set[loc] annos) {
	for (loc ann <- annos)
		if (<l, ann> in m.annotations)
			return true;

	return false;
}
