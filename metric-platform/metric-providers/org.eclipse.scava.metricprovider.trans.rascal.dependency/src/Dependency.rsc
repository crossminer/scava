
@license{
	Copyright (c) 2018 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
module Dependency

import IO;
import List;
import Relation;
import Set;
import String;

import Java;
import lang::java::m3::Core;
import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;


@memo
set[loc] getImportedPackagesBC(M3 m3) 
	= {*getClassPackages(d) | <e,d> <- m3.typeDependency, isClass(d)};

@memo
set[loc] getBundlePackagesBC(M3 m3) {
	pkgs = packages(m3);
	return {p | p <- pkgs, p != |java+package:///|};
}

@metric{numberRequiredPackagesInSourceCode}
@doc{Retrieves the number of required packages found in the project source code.}
@friendlyName{Number required packages in source code}
@appliesTo{java()}
int numberRequiredPackagesInSourceCode(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = (),
	rel[Language, loc, M3] m3s = {}) {
	
	M3 m3 = systemM3(m3s, delta = delta);
	return size(getImportedPackagesBC(m3));
}

// Packages from the java.* namespace are not considered according to the OSGi spec R.6.
private set[loc] getClassPackages(loc class) {
	pkgLoc = {};
	if(class.path != "/java" && !startsWith(class.path, "/java/") && size(findAll(class.path, "/")) > 1) {
		pkgLoc += |java+package:///| + substring(class.path, 1, findLast(class.path, "/"));
	}
	return pkgLoc;
}

// Transforms a ternary relation into a binary relation (last slot is a map).
rel[loc,loc] ternaryReltoBinaryRel(rel[loc,loc,map[str,str]] relation) 
	= {<x,y> | <x,y,z> <- relation};

// Transforms a ternary relation into a set (last slot is a map).
set[loc] ternaryReltoSet(rel[loc,loc,map[str,str]] relation) 
	= {y | <x,y,z> <- relation};