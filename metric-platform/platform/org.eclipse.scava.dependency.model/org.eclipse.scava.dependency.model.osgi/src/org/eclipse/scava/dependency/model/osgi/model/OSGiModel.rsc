module org::eclipse::scava::dependency::model::osgi::model::OSGiModel

import org::eclipse::scava::dependency::model::osgi::language::Syntax;

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
 * - headers: set with the parsed headers of the bundle's manifest.
 */
data OSGiModel = osgiModel (
	loc id,			
	rel[loc logical, loc physical, map[str,str] params] locations = {},	
	rel[loc bundle, loc reqBundle, map[str,str] params] requiredBundles = {},	
	rel[loc bundle, loc impPackage, map[str,str] params] importedPackages = {},
	rel[loc bundle, loc expPackage, map[str,str] params] exportedPackages = {},
	rel[loc bundle, loc dynImpPackage, map[str,str] params] dynamicImportedPackages = {},
	rel[loc bundle, set[Header] header] headers = {}
);