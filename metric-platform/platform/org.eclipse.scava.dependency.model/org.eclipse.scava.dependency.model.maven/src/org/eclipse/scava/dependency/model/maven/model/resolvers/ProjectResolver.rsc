@license{
	Copyright (c) 2018 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
module org::eclipse::scava::dependency::model::maven::model::resolvers::ProjectResolver

import IO;
import String;

import lang::xml::DOM;

import org::eclipse::scava::dependency::model::maven::model::MavenModel;
import org::eclipse::scava::dependency::model::maven::util::MavenUtil;
import org::eclipse::scava::dependency::model::maven::util::FileHandler;
import org::eclipse::scava::dependency::model::maven::util::MavenVersionHandler;


public rel[loc,loc,map[str,str]] getProjectLocation(loc physical, Node dom) {
	if(/Node p:element(_,"project",_) := dom) {
		groupId = getElementFromDOM("groupId",p);
		artifactId = getElementFromDOM("artifactId",p);
		version = getElementFromDOM("version",p);
		
		// Check if gruopId or version values are inherited from the parent pom.
		if(groupId == NONE && /Node pa:element(_,"parent",_) := dom) {
			groupId = getElementFromDOM("groupId",pa);
		}
		if(version == NONE && /Node pa:element(_,"parent",_) := dom) {
			version = getElementFromDOM("version",pa);
		}
		
		return {<createProjectLogicalLoc(groupId,artifactId,version), physical, getProjectParams(p)>};
	}
	else 
		throw "No project element was found.";
}

// Returns a map with <project> related parameters.
private map[str,str] getProjectParams(Node project) {
	packaging = getElementFromDOM("packaging",project);
	classifier = getElementFromDOM("classifier",project);
	
	return ( 
		"packaging" : (packaging == NONE) ? "jar" : packaging,
		"classifier" : classifier);
}

public rel[loc,loc,map[str,str]] getProjectDependencies(loc logical, Node dom) {
	rel[loc,loc,map[str,str]] dependencies = {};
	if(/Node ds:element(_,"dependencies",_) := dom) {
		for(/Node d:element(_,"dependency",_) := ds) {
			groupId = getElementFromDOM("groupId",d);
			artifactId = getElementFromDOM("artifactId",d);
			version = getElementFromDOM("version",d);
			
			dependencies += {<logical, createProjectLogicalLoc(groupId,artifactId,version), getDependencyParams(d)>};
		}
	}
	return dependencies;
}

// Returns a map with <dependency> related parameters.
private map[str,str] getDependencyParams(Node dependency) {
	version = getElementFromDOM("version",dependency);
	classifier = getElementFromDOM("classifier",dependency);
	typ = getElementFromDOM("type",dependency);
	scop = getElementFromDOM("scope",dependency);
	optional = getElementFromDOM("optional",dependency);
	systemPath = getElementFromDOM("systemPath",dependency);
	
	return (
		"resolved" : "false", 
		"version" : version,
		"classifier" : classifier,
		"type" : (typ == NONE) ? "jar" : typ,
		"scope" : (scop == NONE) ? "compile" : scop,
		"optional" : (optional == NONE) ? "false" : optional,
		"systemPath" : systemPath);
}

 // Ex: elementName: groupId && parentName: project.
private str getElementFromDOM(str elementName, Node parentNode) {
	e = NONE;
	if(/Node g:element(_,elementName,_) := parentNode) {
		e = (/Node t:charData(_) := g.children) ? toLowerCase(t.text) : e;
	}
	return e;
}