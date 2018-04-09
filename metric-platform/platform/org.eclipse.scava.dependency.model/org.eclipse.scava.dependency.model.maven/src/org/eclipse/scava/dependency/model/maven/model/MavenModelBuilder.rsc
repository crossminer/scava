module org::eclipse::scava::dependency::model::maven::model::MavenModelBuilder

import IO;
import lang::xml::DOM;
import Set;

import org::eclipse::scava::dependency::model::maven::model::resolvers::ProjectResolver;
import org::eclipse::scava::dependency::model::maven::util::FileHandler;


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

public MavenModel createMavenModelFromProject (loc project) {
	MavenModel model = mavenModel(directory);
	pom = getFileFromProject(project, "pom.xml");
	dom = parseXMLDOM(readFile(pom));
	
	model.locations += getProjectLocation(model.id, dom);
	logical = getOneFrom(model.locations).logical;
	model.dependencies += getProjectDependencies(logical, dom);
	return model;
}