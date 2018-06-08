module org::eclipse::scava::dependency::model::maven::model::MavenModelBuilder

import IO;
import lang::xml::DOM;
import Set;
import String;

import org::eclipse::scava::dependency::model::maven::model::resolvers::ProjectResolver;


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

MavenModel createMavenModelFromWorkingCopy(loc workingCopy) {
	pomFiles = pomLocations(workingCopy,{});
	models = {createMavenModel(workingCopy, f) | f <- pomFiles};
	return composeMavenModels(workingCopy, models);
}

MavenModel createMavenModel(loc id, loc pom) {
	MavenModel model = mavenModel(id);
	dom = parseXMLDOM(readFile(pom));
	
	model.locations += getProjectLocation(model.id, dom);
	logical = getOneFrom(model.locations).logical;
	model.dependencies += getProjectDependencies(logical, dom);
	return model;
}

MavenModel composeMavenModels(loc id, set[MavenModel] models) {
	MavenModel model = mavenModel(id);
	model.locations = {*m.locations | m <- models};
	model.dependencies = {*m.dependencies | m <- models};
	return model;
}

// TODO: repeated code (check OSGi plugin)
private set[loc] pomLocations(loc workingCopy, set[loc] pomFiles) {
	list[loc] files = workingCopy.ls;
	if(files == []) {
		return pomFiles;
	}
	for(f <- files) {
		if(isFile(f) && endsWith(f.path, "/<POM_FILE>")) {
			pomFiles += f;
		}
		if(isDirectory(f)){
			pomFiles += pomLocations(f, pomFiles);
		}
	}
	return pomFiles;
}