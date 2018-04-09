module org::eclipse::scava::dependency::model::maven::DependencyMaven

import org::eclipse::scava::dependency::model::maven::model::MavenModelBuilder;

import lang::java::m3::Core;


@metric{MavenDependencyModelforJava}
@doc{The Maven dependency model stores all Maven-related dependencies of a given project. This information can be used by other metric providers.}
@friendlyName{Maven dependency model for Java projects}
@appliesTo{java()}
public MavenModel createMavenModel(str pom) 
	= createMavenModelFromProject(pom);
//public MavenModel createMavenModel(loc pom)
//	= createMavenModelFromProject(pom);

public MavenModel composeJavaMavenModels(loc id, set[MavenModel] models) 
	= composeMavenModels(id, models);