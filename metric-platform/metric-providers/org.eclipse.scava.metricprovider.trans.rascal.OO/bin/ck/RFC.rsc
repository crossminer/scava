@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module ck::RFC

import Set;

@doc{
	Response for class (nr of methods reachable by class methods (direct and indirect))
}
public map[loc, int] RFC(rel[loc, loc] calls, map[loc, set[loc]] typeMethods, set[loc] allTypes) {
	callsTrans = toMap(calls+);

	return ( t : size({ *(callsTrans[m]?{}) | m <- typeMethods[t]?{} } - typeMethods[t]?{}) | t <- allTypes );
}


/*
public map[loc, int] RFC(M3 m) {
  map[loc, int] RFC = ();
  
  set[loc] declaredClasses = classes(m@containment);
  
  for (class <- declaredClasses) {
    set[loc] classChildren = reach(m@containment, {class});
    RFC[class] = size({ accessedMethod | loc accessedMethod <- range(domainR(m@methodInvocation, classChildren)) + classChildren, isMethod(accessedMethod) });
  }
  
  return RFC;
}
*/
