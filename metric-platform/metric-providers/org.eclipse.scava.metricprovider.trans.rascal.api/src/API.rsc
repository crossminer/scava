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
import DateTime;

import org::maracas::Maracas;
import org::maracas::delta::Delta;

import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;

//
// METRICS
//

@memo
tuple[M3 old, M3 new, Delta delta] computeDelta(ProjectDelta projectDelta, rel[Language, loc, M3] m3s, map[loc, loc] scratchFolders) {
	M3 previousM3 = loadPreviousM3(scratchFolders, projectDelta.date);
	M3 newM3 = systemM3(m3s, delta = projectDelta);
	if (previousM3.id == |unknown:///|)
		previousM3 = newM3;
	Delta d = deltaFromM3(previousM3, newM3);
	serializeNewM3(scratchFolders, newM3, projectDelta.date);

	t = <previousM3, newM3, d>;

	return t;
}

@metric{numberOfChanges}
@doc{TO BE WRITTEN}
@friendlyName{Number of changes}
@appliesTo{java()}
@resetOnEmptyDelta{}
@historic{}
int numberOfChanges(ProjectDelta projectDelta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}, map[loc, loc] scratchFolders = ()) {
	tuple[M3 old, M3 new, Delta delta] t = computeDelta(projectDelta, m3s, scratchFolders);

	return countChanges(t.delta);
}

@metric{numberOfBreakingChanges}
@doc{TO BE WRITTEN}
@friendlyName{Number of breaking changes}
@appliesTo{java()}
@resetOnEmptyDelta{}
@historic{}
int numberOfBreakingChanges(ProjectDelta projectDelta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}, map[loc, loc] scratchFolders = ()) {
	tuple[M3 old, M3 new, Delta delta] t = computeDelta(projectDelta, m3s, scratchFolders);

	return countChanges(breakingChanges(t.delta, t.old, t.new));
}

@metric{changedMethods}
@doc{TO BE WRITTEN}
@friendlyName{Changed methods}
@appliesTo{java()}
@resetOnEmptyDelta{}
@historic{}
set[loc] changedMethods(ProjectDelta projectDelta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}, map[loc, loc] scratchFolders = ()) {
	tuple[M3 old, M3 new, Delta delta] t = computeDelta(projectDelta, m3s, scratchFolders);
	Delta methodDelta = getMethodDelta(t.delta);

	return
		domain(methodDelta.accessModifiers) +
		domain(methodDelta.finalModifiers) +
		domain(methodDelta.staticModifiers) +
		domain(methodDelta.abstractModifiers) +
		domain(methodDelta.paramLists) +
		domain(methodDelta.types) +
		domain(methodDelta.extends) +
		domain(methodDelta.implements) +
		domain(methodDelta.deprecated) +
		domain(methodDelta.renamed) +
		domain(methodDelta.moved) +
		domain(methodDelta.removed) +
		domain(methodDelta.added);
}

//
// UTILS
//

M3 loadPreviousM3(map[loc, loc] scratchFolders, datetime analysisDate) {
	if (proj <- scratchFolders) {
		loc scratch = scratchFolders[proj];
		loc previousM3 = scratch + "Previous.m3";

		if (isFile(previousM3))
			return readBinaryValueFile(#M3, previousM3);
		else {
			println("No previous M3. Returning today\'s.");
			return m3(|unknown:///|);
		}
	} else throw "Couldn\'t parse scratchFolders";
}

void serializeNewM3(map[loc, loc] scratchFolders, M3 new, datetime analysisDate) {
	if (proj <- scratchFolders) {
		loc scratch = scratchFolders[proj];
		loc previousM3 = scratch + "Previous.m3";

		writeBinaryValueFile(previousM3, new);
	} else throw "Couldn\'t parse scratchFolders";
}

int countClassChanges(Delta d) = countChanges(getClassDelta(d));
int countMethodChanges(Delta d) = countChanges(getMethodDelta(d));
int countFieldChanges(Delta d) = countChanges(getFieldDelta(d));

int countChanges(Delta d) =
	  size(d.accessModifiers)
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
