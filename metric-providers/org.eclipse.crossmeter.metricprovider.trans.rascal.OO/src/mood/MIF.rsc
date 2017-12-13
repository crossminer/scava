@license{
Copyright (c) 2014 CROSSMETER Partners.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
}
module mood::MIF

import Set;
import util::Math;

@doc{
	Method Inheritance Factor (can also be used for Attribute Inheritance Factor)	
}
public map[loc, real] MIF(
	map[loc \type, set[loc] methods] typeMethods,
	rel[loc \type, loc method] inheritableConcreteMethods,
	rel[loc subtype, loc supertype] superTypes,
	set[loc] allTypes) {
	
	map[loc, real] mif = ();
	
	ancestors = superTypes+;
	
	for (t <- allTypes) {
		newMethods = size(typeMethods[t]);
		inheritedMethods = size(inheritableConcreteMethods[ancestors[t]]);
		totalMethods = newMethods + inheritedMethods;
		
		if (totalMethods > 0) {
			mif[t] = round(inheritedMethods / toReal(totalMethods), 0.01);
		} else {
			mif[t] = 0.0;
		}
	}
	
	return mif;
}
