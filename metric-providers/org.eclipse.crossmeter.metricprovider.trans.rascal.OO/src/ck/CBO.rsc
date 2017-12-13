@license{
Copyright (c) 2014 CROSSMETER Partners.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
}
module ck::CBO

import Set;
import Relation;


@doc{
	Coupling between objects
}
public map[loc, int] CBO(rel[loc type1, loc type2] typeDependencies, set[loc] allTypes) {
  coupledTypes = typeDependencies + invert(typeDependencies);
  
  return ( t : size(coupledTypes[t]) | t <- allTypes );
}
  


/*
public map[loc, int] CBO(M3 m) {
  set[loc] declaredClasses = classes(m@containment);
  int count = 0;
  map[loc, int] classCoupling = ();

  for (loc class <- declaredClasses) {
    // get a set of all elements in the class
    set[loc] classChildren = reach(m@containment, {class}); // (m@containment+)[class]
    // resolve the types/containment of all the children and the types they use
    set[loc] usedTypes = { usedType | usedType <- reach(m@typeDependency, classChildren), isClass(usedType) || isInterface(usedType) };
    usedTypes -= reach(m@typeInheritance, {class}) + class;
    classCoupling[class] = size(usedTypes);
  }
  
  return classCoupling;
}*/
