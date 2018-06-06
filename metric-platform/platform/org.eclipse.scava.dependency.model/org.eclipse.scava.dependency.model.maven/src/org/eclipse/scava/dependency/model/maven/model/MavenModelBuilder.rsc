module org::eclipse::scava::dependency::model::maven::model::MavenModelBuilder

import IO;
import lang::xml::DOM;
import Set;

import org::eclipse::scava::dependency::model::maven::model::resolvers::ProjectResolver;
import org::eclipse::scava::dependency::model::maven::util::FileHandler;


public str POM_FILE = "pom.xml";

/*
 * - locations: maps from the logical location of a project to its physical location.
 * - dependencies: gathers all the project's dependencies. Dependency related 
 *   parameters are included.
 */
data MavenModel = mavenModel (
	loc id,
	rel[loc logical, loc physical, map[str,str] params] locations = {},
	rel[loc project, loc dependency, map[str,str] params] dependencies = {}
);

public MavenModel createMavenModelFromWorkingCopy(loc workingCopy, M3 m3) {
	pomFiles = getFileFromProject(workingCopy, POM_FILE);
	models = {createOSGimodel(workingCopy, f, m3) | f <- pomFiles};
	return composeMavenModels(workingCopy, models);
}

public MavenModel createMavenModel(loc id, loc pom) {
	MavenModel model = mavenModel(id);
	dom = parseXMLDOM(readFile(pom));
	
	model.locations += getProjectLocation(model.id, dom);
	logical = getOneFrom(model.locations).logical;
	model.dependencies += getProjectDependencies(logical, dom);
	return model;
}

public MavenModel composeMavenModels(loc id, set[MavenModel] models) {
	MavenModel model = mavenModel(id);
	model.locations = {*m.locations | m <- models};
	model.dependencies = {*m.dependencies | m <- models};
	return model;
}