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
    //failsafes
    |java+class:///After|,
    |java+class:///Before|,
    |java+class:///AfterClass|,
    |java+class:///BeforeClass|
  };
  
set[loc] jUnit4TestAnnotation = { |java+interface:///org/junit/Test|,
                                  |java+class:///Test| //fail safe
                                };

@memo
private set[loc] methodsWithIgnoreAnnotation(M3 m) {
  // We collect all methods that have ignore annotation as well as methods in classes that have ignore annotations
  set[loc] result = {};
  set[loc] jUnit4IgnoreAnnotation = { |java+interface:///org/junit/Ignore|, |java+class:///Ignore| };
  for (loc entity <- m@annotations) {
    if (!(isEmpty(jUnit4IgnoreAnnotation & m@annotations[entity]))) {
      if (isClass(entity)) {
        result += { method | method <- m@containment[entity], isMethod(method) };
      } else if (isMethod(entity)){
        result += entity;
      }
    }
  }
  return result;
}

@memo
set[loc] getJUnit4TestMethods(M3 m) {
  return getJUnit3TestMethods(m) + { testMethod | testMethod <- m@declarations<0>, isMethod(testMethod), !(isEmpty(jUnit4TestAnnotation & m@annotations[testMethod])) } - methodsWithIgnoreAnnotation(m);
}

set[loc] getJUnit4SetupMethods(M3 m) {
  return getJUnit3SetupMethods(m) + { testMethod | testMethod <- m@declarations<0>, isMethod(testMethod), !(isEmpty(m@annotations[testMethod] & jUnit4TestSetupAnnotations)) } - methodsWithIgnoreAnnotation(m);
}
