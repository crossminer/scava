@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module mood::PF

import util::Math;
import Set;

@doc{
	Polymorphism Factor (nr of overrides / nr of overrides possible)
}
public real PF(rel[loc subtype, loc supertype] superTypes,
				rel[loc submethod, loc supermethod] overrides,
				rel[loc \type, loc method] overridableMethods,
				set[loc] allTypes) {

	ancestors = superTypes+;

	numPossibleOverrides = ( 0 | it + size(overridableMethods[ancestors[c]]) | c <- allTypes );
	
	if (numPossibleOverrides > 0) {
		return round(size(overrides) / toReal(numPossibleOverrides), 0.01);
	}
	
	return 0.0;
}
