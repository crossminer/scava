module OSGiSourcecode

import IO;
import List;
import Relation;
import String;
import ValueIO;

import lang::java::m3::Core;
import org::eclipse::scava::dependency::model::osgi::model::OSGiModelBuilder;

//TODO: move to an independent plugin (shared between OSGi and Maven plugins).
@memo
public rel[loc,loc] getImportedPackagesBC(M3 m3) 
	= {*getClassPackages(depend) | <loc element, loc depend> <- m3.typeDependency, isClass(depend)};

@memo
public rel[loc,loc] getBundlePackagesBC(M3 m3) {
	packages = packages(m3);
	return {pkg | pkg <- packages, pkg != |java+package:///|};
}

// Packages from the java.* namespace are not considered according to the OSGi spec R.6.
private set[loc] getClassPackages(loc class) {
	pkgLoc = {};
	if(class.path != "/java" && !startsWith(class.path, "/java/") && size(findAll(class.path, "/")) > 1) {
		pkgLoc += |java+package:///| + substring(class.path, 1, findLast(class.path, "/"));
	}
	return pkgLoc;
}

/*
 * Transforms a ternary relation into a binary relation.
 * The last slot of the original relation has a map.
 */
public rel[loc,loc] toBinaryRelation(rel[loc,loc,map[str,str]] relation) 
	= {<x,y> | <x,y,z> <- relation};