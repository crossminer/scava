module org::eclipse::scava::dependency::model::osgi::model::OSGiModelBuilder

import IO;
import Set;
import String;
import Relation;

import org::eclipse::scava::dependency::model::osgi::language::Load;
import org::eclipse::scava::dependency::model::osgi::language::Syntax;
import org::eclipse::scava::dependency::model::osgi::model::resolvers::BundleResolver;
import org::eclipse::scava::dependency::model::osgi::model::resolvers::PackageResolver;


public str MANIFEST_FILE = "MANIFEST.MF";

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


OSGiModel createOSGiModelFromWorkingCopy(loc workingCopy) {
	manifestFiles = manifestLocations(workingCopy,{});
	models = {createOSGimodel(workingCopy, f) | f <- manifestFiles};
	return composeOSGiModels(workingCopy, models);
}

OSGiModel createOSGimodel(loc id, loc manifest) {
	OSGiModel model = osgiModel(id);
	
	// Set location and manifest headers
	manifest = parseManifest(manifest);
	model.locations += getBundleLocation(model);
	logical = getOneFrom(model.locations).logical;
	model.headers = <logical, {h | /Header h := manifest}>;
	
	// Set dependencies
	model.requiredBundles += getRequiredBundles(logical, model);
	model.importedPackages += getImportPackages(logical, model);
	model.dynamicImportedPackages += getDynamicImportPackages(logical, model);
	model.exportedPackages += getExportPackages(logical, model);
	
	return model;
}

OSGiModel composeOSGiModels(loc id, set[OSGiModel] models) {
	OSGiModel model = osgiModel(id);
	model.locations = {*m.locations | m <- models};
	model.headers = {*m.headers | m <- models};
	model.requiredBundles = {*m.requiredBundles | m <- models};
	model.importedPackages = {*m.importedPackages | m <- models};
	model.exportedPackages = {*m.exportedPackages | m <- models};
	model.dynamicImportedPackages = {*m.dynamicImportedPackages | m <- models};
	return model;
}

// TODO: repeated code (check Maven plugin)
private set[loc] manifestLocations(loc workingCopy, set[loc] manifestFiles) {
	list[loc] files = workingCopy.ls;
	if(files == []) {
		return manifestFiles;
	}
	for(f <- files) {
		if(isFile(f) && endsWith(f.path, "/<MANIFEST_FILE>")) {
			manifestFiles += f;
		}
		if(isDirectory(f)){
			manifestFiles += manifestLocations(f, manifestFiles);
		}
	}
	return manifestFiles;
}