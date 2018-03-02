@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module ck::DIT

import Set;
import Relation;
import analysis::graphs::Graph;


@doc{
	Depth in Inheritance Tree per type
}
public map[loc, int] DIT (rel[loc, loc] superTypes, set[loc] allTypes) {
	return ( c : getDepth(c, superTypes) | c <- allTypes );
}

private int getDepth(loc \type, rel[loc, loc] superTypes) {
	parents = superTypes[\type];
	depth = 0;
	while (parents != {}) {
		depth += 1;
		parents = superTypes[parents];
	}
	return depth;
}


/*
public map[loc, int] DIT(M3 m) {
  map[loc, int] classWiseDIT = ();
  rel[loc, loc] inheritances = m@extends<1,0>;
    
  return (class : getDepth(class, inheritances) | class <- carrier(inheritances));
}*/

/*int getDepth(loc curClass, rel[loc, loc] inheritances) {
  set[loc] parents = predecessors(inheritances, curClass);
  if (isEmpty(parents))
    return 0;
  return 1 + max({getDepth(parent, inheritances) | loc parent <- parents});
}*/
