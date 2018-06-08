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
import org::eclipse::scava::dependency::model::osgi::model::OSGiModelBuilder;
import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;


@metric{allOSGiBundleDependencies}
@doc{Retrieves all the OSGi bunlde dependencies (i.e. Require-Bundle dependencies).}
@friendlyName{All OSGi bundle dependencies}
@appliesTo{java()}
set[loc] allOSGiBundleDependencies(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = ()) {
	
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
set[loc] allOSGiPackageDependencies(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = ()) {
	
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		return (m.importedPackages=={}?{}:m.importedPackages.impPackage) + 
			(m.dynamicImportedPackages=={}?{}:m.dynamicImportedPackages.dynImpPackage);
	}
	return {};
}

@metric{numberOSGiPackageDependencies}
@doc{Retrieves the number of OSGi package dependencies (i.e. Import-Package and DynamicImport-Package dependencies).}
@friendlyName{Number of all OSGi package dependencies}
@appliesTo{java()}
int numberOSGiPackageDependencies(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = ()) {
	
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
int numberOSGiBundleDependencies(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = ()) {
	
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		return size(m.requiredBundles=={}?{}:m.requiredBundles.reqBundle);
	}
	return 0;
}

@metric{numberUsedOSGiImportedPackagesInSourceCode}
@doc{Retrieves the number of OSGi imported packages USED in the project source code.}
@friendlyName{Number OSGi imported packages in source code}
@appliesTo{java()}
int numberUsedOSGiImportedPackagesInSourceCode(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = (),
	rel[Language, loc, M3] m3s = {}) {
	
	M3 m3 = systemM3(m3s, delta = delta);
	
	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo]);
		return size(
			(ternaryReltoSet(m3.importedPackages) 
			+ ternaryReltoSet(m3.dynamicImportedPackages)) 
			- (getImportedPackagesBC(m3)));
	}
	return 0;
}