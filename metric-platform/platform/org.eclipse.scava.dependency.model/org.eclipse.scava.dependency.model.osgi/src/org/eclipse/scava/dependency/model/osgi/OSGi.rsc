module org::eclipse::scava::dependency::model::osgi::OSGi

import IO;
import lang::java::m3::Core;

import Java;
import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;

import org::eclipse::scava::dependency::model::osgi::model::OSGiModelBuilder;

//TODO: Consider changing this plugin to a Java extractor
@memo
OSGiModel getOSGiModelFromWorkingCopy(loc workingCopy, M3 m3) =
	createOSGiModelFromWorkingCopy(workingCopy, m3);
	
@metric{allOSGiPackageDependencies}
@doc{Retrieves all the OSGi package dependencies (i.e. Import-Package and DynamicImport-Package dependencies).}
@friendlyName{All OSGi package dependencies}
@appliesTo{java()}
set[loc] allOSGiDependencies(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = (),
	rel[Language, loc, M3] m3s = {}) {
	
	M3 m3 = systemM3(m3s, delta = delta);

	if(repo <- workingCopies) {
		m = getOSGiModelFromWorkingCopy(workingCopies[repo], m3);
		return (m.importedPackages=={}?{}:m.importedPackages.impPackage) + 
			(m.dynamicImportedPackages=={}?{}:m.dynamicImportedPackages.dynImpPackage);
	}
	else {
		return {};
	}
}
