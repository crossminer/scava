module org::eclipse::scava::dependency::model::OSGi::DependencyOSGi

import org::eclipse::scava::dependency::model::OSGi::model::OSGiModelBuilder;

import lang::java::m3::Core;


@metric{OSGiDependencyModelforJava}
@doc{The OSGI dependency model stores all OSGi-related dependencies of a given project. This information can be used by other metric providers.}
@friendlyName{OSGi dependency model for Java projects}
@appliesTo{java()}
public OSGiModel createOSGiModel(str manifest) 
	= createOSGiModelFromFile(manifest, emptyM3(|file:///|));
//public OSGiModel createOSGiModel(loc manifest, rel[Language, loc, M3] m3s = {})
//	= createOSGiModelFromFile(manifest, systemM3(m3s));

public OSGiModel composeJavaOSGiModels(loc id, set[OSGiModel] models) 
	= composeOSGiModels(id,models);


