@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
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

