module org::eclipse::scava::dependency::model::maven::util::MavenUtil

import String;


public str NONE = "none";

//The URI follows the form: |bundle://maven/<groupId>/<artifactId>/<version>|
public loc createProjectLogicalLoc(str groupId, str artifactId, str version)
	= |bundle://maven| + "/<groupId>/<artifactId>/<version>";
	
// Returns the value of the "<groupId>/<artifactId>" of a given project 
public str getProjectGroupArtifactId(loc logical)
	= "<split("/", logical.path)[1]>/<split("/", logical.path)[2]>";