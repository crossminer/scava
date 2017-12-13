@license{
Copyright (c) 2014 CROSSMETER Partners.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
}
module ck::LCOM

import Set;
import List;

@doc{
	Lack of Cohesion of Methods per class
}
map[loc, int] LCOM(
	map[loc method, set[loc] fields] fieldAccesses,
	map[loc \type, set[loc] methods] methods,
	map[loc \type, set[loc] fields] fields,
	set[loc] allTypes) {
	
	map[loc, int] lcom = ();
	
	for (t <- allTypes) {
		fs = fields[t]?{};
		ms = toList(methods[t]?{});
		
		P = 0;
		Q = 0;
		
		for (i <- [0..size(ms)]) {
		    fieldsI = (fieldAccesses[ms[i]]?{}) & fs; 
			for (j <- [i+1 .. size(ms)]) {
				if ((fieldsI & (fieldAccesses[ms[j]]?{})) == {}) {
					P += 1;
				} else {
					Q += 1;
				}
			}		
		}
		
		lcom[t] = (P > Q) ? (P - Q) : 0;
	}
	
	return lcom;
}

