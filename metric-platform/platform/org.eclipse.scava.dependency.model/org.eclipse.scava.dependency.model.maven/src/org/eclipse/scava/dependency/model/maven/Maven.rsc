module org::eclipse::scava::dependency::model::maven::Maven

import IO;
import lang::java::m3::Core;

import Java;
import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;

import org::eclipse::scava::dependency::model::maven::model::MavenModelBuilder;

@memo
private MavenModel getMavenModelFromWorkingCopy(loc workingCopy, M3 m3) =
	createMavenModelFromWorkingCopy(workingCopy, m3);

@metric{allMavenDependencies}
@doc{Retrieves all the Maven dependencies.}
@friendlyName{All Maven dependencies}
@appliesTo{java()}
set[loc] allMavenDependencies(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = (),
	rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);

	if(repo <- workingCopies) {
		m = getMavenModelFromWorkingCopy(workingCopies[repo], m3);
		return (m.dependencies=={}?{}:m.dependencies.dependency);
	}
	else {
		return {};
	}
}