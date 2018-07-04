@license{
	Copyright (c) 2018 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
module Maven

import IO;
import lang::java::m3::Core;
import Set;

import Dependency;
import Java;
import org::eclipse::scava::dependency::model::maven::Maven;
import org::eclipse::scava::dependency::model::maven::model::MavenModel;
import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;

//TODO: How to make the MavenModel visible without importing an internal module?

@metric{allMavenDependencies}
@doc{Retrieves all the Maven dependencies.}
@friendlyName{All Maven dependencies}
@appliesTo{java()}
set[loc] allMavenDependencies(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getMavenModelFromWorkingCopy(workingCopies[repo]);
		return (m.dependencies=={}?{}:m.dependencies.dependency);
	}
	return {};
}

@metric{numberMavenDependencies}
@doc{Retrieves the number of Maven dependencies.}
@friendlyName{Number Maven dependencies}
@appliesTo{java()}
int numberMavenDependencies(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getMavenModelFromWorkingCopy(workingCopies[repo]);
		return size(m.dependencies);
	}
	return 0;
}

@metric{numberUniqueMavenDependencies}
@doc{Retrieves the number of unique Maven dependencies.}
@friendlyName{Number unique Maven dependencies}
@appliesTo{java()}
int numberUniqueMavenDependencies(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getMavenModelFromWorkingCopy(workingCopies[repo]);
		return size(m.dependencies=={}?{}:m.dependencies.dependency);
	}
	return 0;
}

@metric{numberOptionalMavenDependencies}
@doc{Retrieves the number of optional Maven dependencies.}
@friendlyName{Number optional Maven dependencies}
@appliesTo{java()}
int numberOptionalMavenDependencies(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getMavenModelFromWorkingCopy(workingCopies[repo]);
		return (0 | it + 1 | <p,d,params> <- m.dependencies, params["optional"]=="true");
	}
	return 0;
}