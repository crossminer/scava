@license{
	Copyright (c) 2018 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
module org::eclipse::scava::dependency::model::maven::model::MavenModelBuilder

import IO;
import lang::xml::DOM;
import Set;
import String;

import org::eclipse::scava::dependency::model::maven::model::MavenModel;
import org::eclipse::scava::dependency::model::maven::model::resolvers::ProjectResolver;


public str POM_FILE = "pom.xml";

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