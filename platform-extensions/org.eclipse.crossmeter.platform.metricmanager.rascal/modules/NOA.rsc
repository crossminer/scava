@license{
Copyright (c) 2014 CROSSMETER Partners.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
}
module NOA

import lang::java::m3::Core;
import List;
import String;
import Map;
import Manager;

int numberOfAttributes(loc cl, M3 model) = size([ m | m <- model@containment[cl], isField(m)]);

map[str class, int nom] getNOA(M3 fileM3) {
  return (replaceAll(replaceFirst(cl.path, "/", ""), "/", "."):numberOfAttributes(cl, fileM3.model) | <cl,_> <- fileM3.model@containment, isClass(cl));
}

map[str class, int nom] getNOA(unknownFileType(int lines)) {
  return ("": -1);
}
