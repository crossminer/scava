@license{
Copyright (c) 2014 CROSSMETER Partners.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
}
module LOC

import lang::java::m3::Core;
import analysis::graphs::Graph;
import IO;
import List;
import String;
import Set;
import Manager;

alias locResult = tuple[int total, int comment, int empty, int source];

locResult countLoc(unknownFileType(int lines)) {
  return <lines, -1, -1, lines>;
}

locResult countLoc(M3 fileM3) {
  return <fileM3.total, fileM3.comments, fileM3.empty, fileM3.source>;
}
