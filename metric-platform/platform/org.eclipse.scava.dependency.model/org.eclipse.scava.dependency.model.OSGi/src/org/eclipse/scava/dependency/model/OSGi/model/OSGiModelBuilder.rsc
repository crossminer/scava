module org::eclipse::scava::dependency::model::OSGi::model::OSGiModelBuilder

import IO;
import Set;
import Relation;
import ValueIO;
import lang::java::m3::Core;

import org::eclipse::scava::dependency::model::OSGi::language::Load;
import org::eclipse::scava::dependency::model::OSGi::language::Syntax;
import org::eclipse::scava::dependency::model::OSGi::model::resolvers::BundleResolver;
import org::eclipse::scava::dependency::model::OSGi::model::resolvers::M3Resolver;
import org::eclipse::scava::dependency::model::OSGi::model::resolvers::PackageResolver;
import org::eclipse::scava::dependency::model::OSGi::util::OSGiUtil;
import org::eclipse::scava::dependency::model::OSGi::util::FileHandler;


/*
 * - id: identifier. Points to the physical location of the bundle.
 * - requiredBundles: gathers all the bundle's required bundles. Header related 
 * parameters are included.
 * - importedPackages: gathers all the bundle's imported packages. Header related 
 * parameters are included.
 * - exportedPackages: gathers all the bundle's exported packages. Header related 
 * parameters are included.
 * - dynamicImportedPackages: gathers all the bundle's dynamically imported packages. 
 * Header related parameters are included.
 * - importedPackagesBC: gathers all the logical location of packages used in the 
 * bundle bytecode (cf. M3). 
 * - bundlePackagesBC: gathers all the logical location of packages of the bundle (cf. M3). 
 * - headers: set with the parsed headers of the bundle's manifest.
 */
data OSGiModel = osgiModel (
	loc id,			
	rel[loc logical, map[str,str] params] location = {},	
	rel[loc reqBundle, map[str,str] params] requiredBundles = {},	
	rel[loc impPackage, map[str,str] params] importedPackages = {},
	rel[loc expPackage, map[str,str] params] exportedPackages = {},
	rel[loc dynImpPackage, map[str,str] params] dynamicImportedPackages = {},
	set[loc] importedPackagesBC = {},
	set[loc] bundlePackagesBC = {},
	set[Header] headers = {}
);


public OSGiModel createOSGiModelFromFile (str file, M3 m3) {
	OSGiModel model = osgiModel(|file:///| + file);
	
	// Set location and manifest headers
	manifest = parseManifest(model.id);
	model.headers = {h | /Header h := manifest};
	model.location += getBundleLocation(model);
	
	// Set dependencies
	model.requiredBundles += getRequiredBundles(model);
	model.importedPackages += getImportPackages(model);
	model.dynamicImportedPackages += getDynamicImportPackages(model);
	model.exportedPackages += getExportPackages(model);
	
	// Set M3 relations
	model.importedPackagesBC += getImportedPackagesBC(m3);
	model.bundlePackagesBC += getBundlePackagesBC(m3);
	
	return model;
}
