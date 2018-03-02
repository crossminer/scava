@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module ck::NOC

import Relation;
import Set;

@doc{
	Number of (direct) children per type
}
public map[loc, int] NOC(rel[loc, loc] superTypes, set[loc] allTypes) {
	subTypes = invert(superTypes);
	return ( t : size(subTypes[t]) | t <- allTypes );
}


/*
public map[loc, int] NOC(M3 m) {
  map[loc, int] classWiseChildren = ();
  map[loc, set[loc]] inheritanceMap = toMap(m@extends<1,0>);
  
  return (class : size(inheritanceMap[class]) | class <- inheritanceMap);
}*/
