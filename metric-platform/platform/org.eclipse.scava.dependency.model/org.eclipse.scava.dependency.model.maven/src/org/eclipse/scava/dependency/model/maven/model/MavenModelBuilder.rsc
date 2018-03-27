module org::eclipse::scava::dependency::model::maven::model::MavenModelBuilder

import IO;
import lang::xml::DOM;

import org::eclipse::scava::dependency::model::maven::model::resolvers::ProjectResolver;
import org::eclipse::scava::dependency::model::maven::util::FileHandler;


/*
 * - locations: maps from the logical location of a project to its physical location.
 * - dependencies: gathers all the project's dependencies. Dependency related 
 *   parameters are included.
 */
data MavenModel = mavenModel (
	loc id,
	rel[loc logical, map[str,str] params] locations = {},
	rel[loc dependency, map[str,str] params] dependencies = {}
);

public MavenModel createMavenModelFromProject (loc project) {
	MavenModel model = mavenModel(directory);
	pom = getFileFromProject(project, "pom.xml");
	dom = parseXMLDOM(readFile(pom));
	
	model.locations += getProjectLocation(dom);
	model.dependencies += getProjectDependencies(dom);
	return model;
}