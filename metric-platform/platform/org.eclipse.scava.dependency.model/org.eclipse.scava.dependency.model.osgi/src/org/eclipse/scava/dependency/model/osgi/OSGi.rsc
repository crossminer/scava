module org::eclipse::scava::dependency::model::osgi::OSGi

import IO;
import lang::java::m3::Core;

import Java;
import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;

import org::eclipse::scava::dependency::model::osgi::model::OSGiModelBuilder;


@metric{OSGiAllPackageDependencies}
@doc{Retrieves all the OSGi package dependencies (i.e. Import-Package and DynamicImport-Package dependencies).}
@friendlyName{OSGi all package dependencies}
@appliesTo{java()}
set[loc] allOSGiDependencies(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopies = (),
	rel[Language, loc, M3] m3s = {}) {
	
	M3 m3 = systemM3(m3s, delta = delta);
	
	if(repo <- workingCopies) {
		m = createOSGiModelFromWorkingCopy(workingCopies[repo], m3);
		return m.importedPackages.impPackage + m.dynamicImportedPackages.dynImpPackage;
	}
	else {
		return {};
	}
}
