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
	rel[loc logical, loc physical, map[str,str] params] locations = {},	
	rel[loc bundle, loc reqBundle, map[str,str] params] requiredBundles = {},	
	rel[loc bundle, loc impPackage, map[str,str] params] importedPackages = {},
	rel[loc bundle, loc expPackage, map[str,str] params] exportedPackages = {},
	rel[loc bundle, loc dynImpPackage, map[str,str] params] dynamicImportedPackages = {},
	rel[loc bundle, loc impPackageBC] importedPackagesBC = {},
	rel[loc bundle, loc bundlePackageBC] bundlePackagesBC = {},
	rel[loc bundle, set[Header] header] headers = {}
);


public OSGiModel createOSGiModelFromFile (str file, M3 m3) {
	OSGiModel model = osgiModel(|file:///| + file);
	
	// Set location and manifest headers
	manifest = parseManifest(model.id);
	model.locations += getBundleLocation(model);
	logical = getOneFrom(model.locations).logical;
	model.headers = <logical, {h | /Header h := manifest}>;
	
	// Set dependencies
	model.requiredBundles += getRequiredBundles(logical, model);
	model.importedPackages += getImportPackages(logical, model);
	model.dynamicImportedPackages += getDynamicImportPackages(logical, model);
	model.exportedPackages += getExportPackages(logical, model);
	
	// Set M3 relations
	//TODO: uncomment once we solve M3 error.
	//model.importedPackagesBC += getImportedPackagesBC(m3);
	//model.bundlePackagesBC += getBundlePackagesBC(m3);
	
	return model;
}
