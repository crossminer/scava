@license{
Copyright (c) 2014 CROSSMETER Partners.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
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
