@license{
	Copyright (c) 2019 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
module API

import IO;
import ValueIO;
import lang::java::m3::Core;
import Set;
import Relation;
import Java;

import org::maracas::Maracas;
import org::maracas::delta::Delta;
import org::maracas::Nope;

import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;

@metric{dummyApiMetric}
@doc{For testing purposes}
@friendlyName{Dummy API metric}
@appliesTo{java()}
@historic{}
int dummyApiMetric(map[loc, loc] workingCopies = (), ProjectDelta projectDelta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}, map[loc, loc] scratchFolders = ()) {
	int ret = 0;

	if (proj <- scratchFolders) {
		loc scratch = scratchFolders[proj];
		loc previous = scratch + "Previous.m3";
		M3 m3 = systemM3(m3s, delta = projectDelta);
		
		println("Looking for <previous>");
		
		if (isFile(previous)) { // Not first day 
			println("Found previous, loading M3");
			M3 previousM3 = readBinaryValueFile(#M3, previous);
			println("Computing new M3");
			
			
			Delta d = deltaFromM3(previousM3, m3);
		
			println("delta=<delta>");
		
			//Delta bc = breakingChanges(d);
		
			//println("bc=<bc>");
			
			ret = size(d.accessModifiers)
				+ size(d.finalModifiers)
				+ size(d.staticModifiers)
				+ size(d.abstractModifiers)
				+ size(d.paramLists)
				+ size(d.types)
				+ size(d.extends)
				+ size(d.implements)
				+ size(d.deprecated)
				+ size(d.renamed)
				+ size(d.moved)
				+ size(d.removed)
				+ size(d.added);
		} else { // First day
			println("Previous not found, skipping");
		}
		
		println("Serializing today\'s M3");
		writeBinaryValueFile(previous, m3);
	}
	
	return ret;
}
