@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
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
