module org::eclipse::scava::dependency::model::maven::model::MavenModel


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