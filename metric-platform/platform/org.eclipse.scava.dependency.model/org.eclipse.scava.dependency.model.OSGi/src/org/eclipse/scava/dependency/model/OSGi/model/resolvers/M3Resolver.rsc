module org::eclipse::scava::dependency::model::OSGi::model::resolvers::M3Resolver

import IO;
import List;
import Relation;
import String;
import ValueIO;

import lang::java::m3::Core;
import org::eclipse::scava::dependency::model::OSGi::model::OSGiModelBuilder;
import org::eclipse::scava::dependency::model::OSGi::util::OSGiUtil;


//--------------------------------------------------------------------------------
// Functions
//--------------------------------------------------------------------------------

/*
 * Creates a relation that maps bundle logical URI to bytecode used packages logical 
 * locations.
 */
public rel[loc,loc] getImportedPackagesBC(loc logical, M3 m3Model) {
	m3ImportedPackages = getM3ImportedPackages(m3Model);
	return {<logical, pkg> | pkg <- m3ImportedPackages};
}

/*
 * Creates a relation that maps bundle logical URI to bytecode packages logical 
 * locations.
 */
public rel[loc,loc] getBundlePackagesBC(loc logical, M3 m3Model) {
	set[loc] packages = packages(m3Model);
	return {<logical, pkg> | pkg <- packages, pkg != |java+package:///|};
}

/*
 * Returns used packages in a given M3 model.
 */
private set[loc] getM3ImportedPackages(M3 m3Model) 
	= {*getClassPackages(depend, m3Model) | <loc element, loc depend> <- m3Model.typeDependency, isClass(depend)};

/*
 * Returns a class related packages logical locations.
 */
 //TODO: optimize (consumes 64.5% of the time when creating M3 relations (~5.5min))
private set[loc] getClassPackages(loc class, M3 m3Model) {
	set[loc] pkgLoc = {};
	//Packages from the java.* namespace are not considered according to the OSGi spec R.6.
	if(class.path != "/java" && !startsWith(class.path, "/java/") && size(findAll(class.path, "/")) > 1) {
		pkgLoc += |java+package:///| + substring(class.path, 1, findLast(class.path, "/"));
		//list[str] pkgs = split("/", substring(class.path, 1, findLast(class.path, "/")));
		//loc currentPkg = |java+package:///|;
		//for(p <- pkgs) {
		//	currentPkg += "/<p>";
		//	pkgsLocs += currentPkg;
		//}
	}
	return pkgLoc;
}