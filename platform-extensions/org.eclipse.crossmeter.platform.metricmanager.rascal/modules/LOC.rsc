@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
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
