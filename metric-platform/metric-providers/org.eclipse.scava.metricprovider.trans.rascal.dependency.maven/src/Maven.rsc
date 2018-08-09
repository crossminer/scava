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
set[loc] allMavenDependencies (map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getMavenModelFromWorkingCopy(workingCopies[repo]);
		return (m.dependencies=={}?{}:m.dependencies.dependency);
	}
	return {};
}

@metric{allOptionalMavenDependencies}
@doc{Retrieves all the optional Maven dependencies.}
@friendlyName{All optional Maven dependencies}
@appliesTo{java()}
set[loc] allOptionalMavenDependencies (map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getMavenModelFromWorkingCopy(workingCopies[repo]);
		return {d | <p,d,params> <- m.dependencies, params["optional"]=="true"};
	}
	return {};
}

@metric{numberMavenDependencies}
@doc{Retrieves the number of Maven dependencies.}
@friendlyName{Number Maven dependencies}
@appliesTo{java()}
int numberMavenDependencies (map[loc, loc] workingCopies = ()) {
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
int numberUniqueMavenDependencies (map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getMavenModelFromWorkingCopy(workingCopies[repo]);
		return size(m.dependencies=={}?{}:m.dependencies.dependency);
	}
	return 0;
}

@metric{ratioOptionalMavenDependencies}
@doc{Retrieves the ratio of optional Maven dependencies.}
@friendlyName{Ratio optional Maven dependencies}
@uses = ("allOptionalMavenDependencies" : "optionalDep")
@appliesTo{java()}
real ratioOptionalMavenDependencies (
	map[loc, loc] workingCopies = (),
	set[loc] optionalDep = {}) {
	if(repo <- workingCopies) {
		m = getMavenModelFromWorkingCopy(workingCopies[repo]);
		return (size(m.dependencies) > 0) ? 0.0 + (size(optionalDep) / size(m.dependencies)): 0.0;
	}
	return 0.0;
}

@metric{isUsingTycho}
@doc{Checks if the current project is a Tycho project.}
@friendlyName{Is using Tycho}
@appliesTo{java()}
bool isUsingTycho (map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getMavenModelFromWorkingCopy(workingCopies[repo]);
		if(<p,d,params> <- m.locations, params["packaging"]=="eclipse-plugin") {
			return true;
		}
	}
	return false;
}