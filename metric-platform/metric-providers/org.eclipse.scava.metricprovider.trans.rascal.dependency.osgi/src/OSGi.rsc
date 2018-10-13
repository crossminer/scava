@license{
	Copyright (c) 2018 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
module OSGi

import IO;
import lang::java::m3::Core;
import Set;

import Dependency;
import Java;
import org::eclipse::scava::dependency::model::osgi::OSGi;
import org::eclipse::scava::dependency::model::osgi::model::OSGiModel;
import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;


@metric{allOSGiBundleDependencies}
@doc{Retrieves all the OSGi bunlde dependencies (i.e. Require-Bundle dependencies).}
@friendlyName{All OSGi bundle dependencies}
@appliesTo{java()}
set[loc] allOSGiBundleDependencies (map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		return (m.requiredBundles=={}?{}:m.requiredBundles.reqBundle);
	}
	return {};
}

@metric{allOSGiPackageDependencies}
@doc{Retrieves all the OSGi package dependencies (i.e. Import-Package and DynamicImport-Package dependencies).}
@friendlyName{All OSGi package dependencies}
@appliesTo{java()}
set[loc] allOSGiPackageDependencies (map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		return (m.importedPackages=={}?{}:m.importedPackages.impPackage) + 
			(m.dynamicImportedPackages=={}?{}:m.dynamicImportedPackages.dynImpPackage);
	}
	return {};
}

@metric{allOSGiDynamicImportedPackages}
@doc{Retrieves all the OSGi dynamically imported packages. If returned value != {} a smell exists in the Manifest file.}
@friendlyName{All OSGi dynamically imported packages}
@appliesTo{java()}
set[loc] allOSGiDynamicImportedPackages (map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		return (m.dynamicImportedPackages=={}) ? {} : m.dynamicImportedPackages.dynImpPackage;
	}
	return {};
}

@metric{numberOSGiPackageDependencies}
@doc{Retrieves the number of OSGi package dependencies (i.e. Import-Package and DynamicImport-Package dependencies).}
@friendlyName{Number of all OSGi package dependencies}
@appliesTo{java()}
int numberOSGiPackageDependencies (map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		return size((m.importedPackages=={}?{}:m.importedPackages.impPackage) + 
			(m.dynamicImportedPackages=={}?{}:m.dynamicImportedPackages.dynImpPackage));
	}
	return 0;
}

@metric{numberOSGiBundleDependencies}
@doc{Retrieves the number of OSGi bunlde dependencies (i.e. Require-Bundle dependencies).}
@friendlyName{Number all OSGi bundle dependencies}
@appliesTo{java()}
int numberOSGiBundleDependencies (map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		return size(m.requiredBundles=={} ? {} : m.requiredBundles.reqBundle);
	}
	return 0;
}

@metric{unusedOSGiImportedPackages}
@doc{Retrieves the set of unused OSGi imported packages. If set != {} then developers are importing more packages than needed (smell).}
@friendlyName{Unused OSGi imported packages}
@appliesTo{java()}
set[loc] unusedOSGiImportedPackages (
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = (),
	rel[Language, loc, M3] m3s = {}) {
	
	M3 m3 = systemM3(m3s, delta = delta);
	
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		impPkgs = ternaryReltoSet(m.importedPackages);
		dynImpPkgs = ternaryReltoSet(m.dynamicImportedPackages);
		expPkgs = ternaryReltoSet(m.exportedPackages);
		
		return (impPkgs + dynImpPkgs) 
				- getImportedPackagesBC(m3)
				- expPkgs;
	}
	return {};
}	

@metric{usedOSGiUnimportedPackages}
@doc{Retrieves the set of used but unimported packages. This metric does not consider packages implicitly imported through the Bundle-Require header.
If set != {} then developers may be depending on the execution environment (smell).}
@friendlyName{Used OSGi unimported packages}
@appliesTo{java()}
set[loc] usedOSGiUnimportedPackages(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = (),
	rel[Language, loc, M3] m3s = {}) {
	
	M3 m3 = systemM3(m3s, delta = delta);
	
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		impPkgs = ternaryReltoSet(m.importedPackages);
		dynImpPkgs = ternaryReltoSet(m.dynamicImportedPackages);
		expPkgs = ternaryReltoSet(m.exportedPackages);
		
		return getImportedPackagesBC(m3)
				- impPkgs 
				- dynImpPkgs
				- getBundlePackagesBC(m3);
	}
	return {};
}

@metric{ratioUnusedOSGiImportedPackages}
@doc{Retrieves the ratio of unused OSGi imported packages with regards to the whole set of imported and dynamically imported OSGi packages.}
@friendlyName{Ratio of unused OSGi imported packages}
@uses = ("unusedOSGiImportedPackages" : "unusedPkgs")
@appliesTo{java()}
real ratioUnusedOSGiImportedPackages (
	map[loc, loc] workingCopies = (),
	set[loc] unusedPkgs = {}) {
	
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		impPkgs = ternaryReltoSet(m.importedPackages);
		dynImpPkgs = ternaryReltoSet(m.dynamicImportedPackages);
		
		return (size(impPkgs) > 0) ? 0.0 + (size(unusedPkgs) / size((impPkgs + dynImpPkgs))) : 0.0;
	}
	return 0.0;
}

@metric{ratioUsedOSGiImportedPackages}
@doc{Retrieves the ratio of used imported packages. If ratio == 0.0 all imported packages have been used in the project code.}
@friendlyName{Ratio of used OSGi imported packages}
@appliesTo{java()}
real ratioUsedOSGiImportedPackages(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = (),
	rel[Language, loc, M3] m3s = {}) {
	
	M3 m3 = systemM3(m3s, delta = delta);
	
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		impPkgs = ternaryReltoSet(m.importedPackages);
		expPkgs = ternaryReltoSet(m.exportedPackages);
		
		usedPkgs = (impPkgs & getImportedPackagesBC(m3)) + (impPkgs & expPkgs & getBundlePackagesBC(m3));
		return (size(m.importedPackages) > 0) ? 0.0 + (size(usedPkgs) / size(impPkgs)) : 0.0;
	}
	return 0.0;
}


@metric{numberOSGiSplitImportedPackages}
@doc{Retrieves the number of split imported packages. If returned value > 0 there is a smell in the Manifest.}
@friendlyName{Number OSGi split imported packages}
@appliesTo{java()}
int numberOSGiSplitImportedPackages (map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		return numberSplitPackages(m.importedPackages);
	}
	return 0;
}

@metric{numberOSGiSplitExportedPackages}
@doc{Retrieves the number of split exported packages. If returned value > 0 there is a smell in the Manifest.}
@friendlyName{Number OSGi split exported packages}
@appliesTo{java()}
public int numberOSGiSplitExportedPackages (map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		return numberSplitPackages(m.exportedPackages);
	}
	return 0;
}

// Works with the following relations of the OSGiModel: importedPackages and exportedPackages
private int numberSplitPackages (rel[loc,loc,map[str,str]] relation) 
	= (0 | it + 1 | <bundle,pkg,params> <- relation, params["split"] != "none");
	
@metric{unversionedOSGiRequiredBundles}
@doc{Retrieves the set of unversioned OSGi required bundles (declared in the Require-Bundle header). 
If returned value != {} there is a smell in the Manifest.}
@friendlyName{Unversioned OSGi required bundles}
@appliesTo{java()}
set[loc] unversionedOSGiRequiredBundles (map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		return unversionedDependencies(m.requiredBundles);
	}
	return {};
}

@metric{ratioUnversionedOSGiRequiredBundles}
@doc{Retrieves the ratio of unversioned OSGi required bundles.}
@friendlyName{Ratio unversioned OSGi required bundles}
@uses = ("unversionedOSGiRequiredBundles" : "unversBundles")
@appliesTo{java()}
real ratioUnversionedOSGiRequiredBundles (
	map[loc, loc] workingCopies = (),
	set[loc] unversBundles = {}) {
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		
		// Note: we assume that there is only one version per required bundle.
		return (size(m.requiredBundles) > 0) ? 0.0 + (size(unversBundles) / size(m.requiredBundles.reqBundle)) : 0.0;
	}
	return 0.0;
}

@metric{unversionedOSGiImportedPackages}
@doc{Retrieves the set of unversioned OSGi imported packages (declared in the Import-Package header). 
If returned value != {} there is a smell in the Manifest.}
@friendlyName{Unversioned OSGi imported packages}
@appliesTo{java()}
set[loc] unversionedOSGiImportedPackages (map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		return unversionedDependencies(m.importedPackages);
	}
	return {};
}
	
@metric{ratioUnversionedOSGiImportedPackages}
@doc{Retrieves the ratio of unversioned OSGi imported packages.}
@friendlyName{Ratio unversioned OSGi imported packages}
@uses = ("unversionedOSGiImportedPackages" : "unversPackgs")
@appliesTo{java()}
real ratioUnversionedOSGiImportedPackages (
	map[loc, loc] workingCopies = (),
	set[loc] unversPackgs = {}) {
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		
		// Note: we assume that there is only one version per imported package.
		return (size(m.importedPackages) > 0) ? 0.0 + (size(unversPackgs) / size(m.importedPackages.impPackage)) : 0.0;
	}
	return 0.0;
}

@metric{unversionedOSGiExportedPackages}
@doc{Retrieves the set of unversioned OSGi exported packages (declared in the Export-Package header). 
If returned value != {} there is a smell in the Manifest.}
@friendlyName{Unversioned OSGi exported packages}
@appliesTo{java()}
set[loc] unversionedOSGiExportedPackages (map[loc, loc] workingCopies = ()) {
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		return unversionedDependencies(m.exportedPackages);
	}
	return {};
}

@metric{ratioUnversionedOSGiExportedPackages}
@doc{Retrieves the ratio of unversioned OSGi exported packages.}
@friendlyName{Ratio of unversioned OSGi exported packages}
@uses = ("unversionedOSGiExportedPackages" : "unversPackgs")
@appliesTo{java()}
real ratioUnversionedOSGiExportedPackages (
	map[loc, loc] workingCopies = (),
	set[loc] unversPackgs = {}) {
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		
		// Note: we assume that there is only one version per imported package.
		return (size(m.exportedPackages) > 0) ? 0.0 + (size(unversPackgs) / size(m.exportedPackages.expPackage)) : 0.0;
	}
	return 0.0;
}

// Works with the following relations of the OSGiModel: requiredBundles, importedPackages, exportedPackages, and dynamicImportedPackages
private set[loc] unversionedDependencies(rel[loc,loc,map[str,str]] relation) 
	= {depend | <bundle, depend, params> <- relation, params["version-spec"] == "none"};