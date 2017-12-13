@license{
Copyright (c) 2014 CROSSMETER Partners.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
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
