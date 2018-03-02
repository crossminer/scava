@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module LOCJava

import lang::java::m3::Core;
import analysis::graphs::Graph;
import org::eclipse::scava::metricprovider::Manager;
import org::eclipse::scava::metricprovider::ProjectDelta;

import analysis::statistics::Frequency;
import analysis::statistics::Inference;

import Prelude;

import LOC;

real giniLOCOverClass(set[M3] m3s, bool(loc) isClass) {
  classLines = (lc : sc.end.line - sc.begin.line + 1 | m3 <- m3s, <lc, sc> <- m3@declarations, isClass(lc));
  return giniLOC(classLines);
}

@metric{LOCoverJavaClass}
@doc{The distribution of physical lines of code over Java classes, interfaces and enums explains how complexity is distributed over the design elements of a system.}
@friendlyName{Distribution of physical lines of code over Java classes, interfaces and enums}
@appliesTo{java()}
real giniLOCOverClassJava(rel[Language, loc, M3] m3s = {}) {
  return giniLOCOverClass({m3 | <java(), _, m3> <- m3s}, 
  	bool(loc lc) {
  		return lang::java::m3::Core::isInterface(lc) || lang::java::m3::Core::isClass(lc) || lc.scheme == "java+enum";
  	});
}
