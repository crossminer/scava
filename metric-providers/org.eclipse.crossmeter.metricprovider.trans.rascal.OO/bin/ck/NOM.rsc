@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module ck::NOM

import Set;

@doc{
	Number of methods per class
}
public map[loc, int] NOM(map[loc \type, set[loc] methods] typeMethods, set[loc] allTypes) {
	return ( t : size(typeMethods[t]?{}) | t <- allTypes );
}


/*
int NOM(M3 model) = (0 | it + 1 | entity <- model@declarations<0>, isMethod(entity));
                                  
map[loc class, int methodCount] NOMperClass(M3 model) {
  map[loc, int] result = ();
  for (<class, method> <- model@containment, canContainMethods(class), isMethod(method)) {
    result[class] ? 0 += 1;
  }
  return result;
}

// Generic method for all number of methods related metrics
public map[loc, int] NOM(M3 m, set[Modifier] modifiers = {}) {
  classMethods = declaredMethods(m, checkModifiers = modifiers);
  classMethodsMap = toMap(classMethods);
  return (class : size(classMethodsMap[class]) | class <- classMethodsMap);
}
*/
