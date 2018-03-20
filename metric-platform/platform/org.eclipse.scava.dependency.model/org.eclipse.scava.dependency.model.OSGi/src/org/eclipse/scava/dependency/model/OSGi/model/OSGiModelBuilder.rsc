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


//--------------------------------------------------------------------------------
// Model
//--------------------------------------------------------------------------------

/*
 * - locations: maps from the logical location of a bundle to its physical location.
 * - requiredBundles: maps from the logical location of a bundle to one of its required 
 * bundles logical location. Header related parameters are included.
 * - importedPackages: maps from the logical location of a bundle to one of its imported 
 * packages logical location. Header related parameters are included.
 * - exportedPackages: maps from the logical location of a bundle to one of its exported 
 * packages logical location. Header related parameters are included.
 * - dynamicImportedPackages: maps from the logical location of a bundle to one of its 
 * dynamically imported packages logical location. Header related parameters are included.
 * - importedPackagesBC: maps from the logical location of a bundle to the logical location 
 * of packages used in the bytecode (cf. M3). 
 * - bundlePackagesBC: maps from the logical location of a bundle to the logical location 
 * of packages of the bundle (cf. M3). 
 * - manifests: maps from the logical location of a bundle to a set with the parsed headers 
 * of its manifest.
 */
data OSGiModel = osgiModel (
	loc id,
	rel[loc logical, loc physical, map[str,str] params] locations = {},								
	rel[loc bundle, loc reqBundle, map[str,str] params] requiredBundles = {},	
	rel[loc bundle, loc impPackage, map[str,str] params] importedPackages = {},
	rel[loc bundle, loc expPackage, map[str,str] params] exportedPackages = {},
	rel[loc bundle, loc dynImpPackage, map[str,str] params] dynamicImportedPackages = {},
	rel[loc bundle, loc impPackage] importedPackagesBC = {},
	rel[loc bundle, loc package] bundlePackagesBC = {},
	rel[loc bundle, set[Header] headers] manifests = {}
);


//--------------------------------------------------------------------------------
// Functions
//--------------------------------------------------------------------------------

/*
 * Returns an OSGiModel with the information of all JAR files present in the given
 * directory.
 */
public OSGiModel createOSGiModelFromDirectory (loc directory, bool m3=false, loc m3Path=M3_PATH) {
	OSGiModel model = osgiModel(directory);
	model = addOSGiModelMetadataRelations(model);
	model = addOSGiModelM3Relations(model, m3, m3Path);
	return model;
}

/*
 * Method for creating an OSGi model fro a single model (resolution
 * will laways be false).
 * TODO: consider M3 models.
 */
public OSGiModel createSingleOSGiModelFromString(loc path, str manifest) {
	OSGiModel model = osgiModel(path);
	start[Manifest] pm = parseManifest(manifest);
	heads = {h | /Header h := pm};
	println(hoi());
	tuple[loc logical, loc physical, map[str,str] params] location = getBundleLocation(path, pm);
	
	model.locations += {location};
	model.manifests += {<location.logical, heads>};
	model.requiredBundles += getRequiredBundles(location.logical, model);
	model.importedPackages += getImportPackages(location.logical, model);
	model.exportedPackages += getExportPackages(location.logical, model);
	model.dynamicImportedPackages += getDynamicImportPackages(location.logical, model);
	
	return model;
}

/*
 * Adds metadata relations to an OSGiModel with the information of all 
 * JAR files present in the given directory (model.id).
 */
public OSGiModel addOSGiModelMetadataRelations (OSGiModel model) {
	println("Creating locations and manifests relations...");
	model = setOSGiModelLocs(model.id, model);
	
	println("Creating required bundle and exported package relations...");
	model = setOSGiModelReqBundlesExpPackages(model);	
	
	println("Creating imported and dynamic imported package relations...");
	model = setOSGiModelImpPackages(model);
	
	return model;
}

/*
 * Creates a OSGiModel from a binary value file.
 */
public OSGiModel createOSGiModelFromBinaryFile(loc file) 
	= readBinaryValueFile(#OSGiModel, file);
	
/*
 * Returns the model received as parameter with the locations and manifests relations.
 */
private OSGiModel setOSGiModelLocs(loc directory, OSGiModel model) {
	jars = getJarsLoc(directory);
	
	for(loc jar <- jars) {
		println(jar);
		start[Manifest] pm = parseManifest(jar);
		heads = {h | /Header h := pm};
		tuple[loc logical, loc physical, map[str,str] params] location = getBundleLocation(jar,pm);
		model.locations += {location};
		model.manifests += {<location.logical,heads>};
	}
	
	return model;
}

/*
 * Returns the model received as parameter with the requiredBundles and exportedPackages 
 * relations.
 */
public OSGiModel setOSGiModelReqBundlesExpPackages(OSGiModel model) {
	for(location <- model.locations) {
		model.requiredBundles += getRequiredBundles(location.logical, model);
		model.exportedPackages += getExportPackages(location.logical, model);
	}
	return model;
}

/*
 * Returns the model received as parameter with the importedPackages relation.
 */
public OSGiModel setOSGiModelImpPackages(OSGiModel model) {	
	for(location <- model.locations) {
		model.importedPackages += getImportPackages(location.logical, model);
		model.dynamicImportedPackages += getDynamicImportPackages(location.logical, model);
	}
	return model;
}

/*
 * Adds M3 relations to  a OSGiModel with the information of all 
 * JAR files present in the given directory (model.id).
 */
public OSGiModel addOSGiModelM3Relations(OSGiModel model, bool m3Models, loc m3Path) {	
	if(m3Models){
		println("Creating M3 models from Jar files...");
		createOSGiModelM3s(model, m3Path);
	}
	
	println("Creating M3 imported and bundle package relations...");
	model = setOSGiModelM3Relations(model, m3Path);
	
	return model;
}

/*
 * Creates M3 models as binary files given the loc of the OSGiModel folder.
 */
private void createOSGiModelM3s(OSGiModel model, loc m3Path) {
	jars = getJarsLoc(model.id);
	i = 0;
	for(loc jar <- jars){
		println("<i>: <jar>");
		logical = getOneFrom(invert(model.locations)[_,jar]);
		writeBinaryValueFile(m3Path + "/<logical.path>", createM3FromJar(jar));
		i+=1;
	}
}

/*
 * Returns the model received as parameter with the bytecode imported and bundle
 * packages relations.
 */
private OSGiModel setOSGiModelM3Relations(OSGiModel model, loc m3Path) {
	i=0;
	for(logical <- model.locations.logical) {
		println(i);
		m3Model = readBinaryValueFile(#M3, m3Path + "/<logical.path>");
		model.importedPackagesBC += getImportedPackagesBC(logical, m3Model);
		model.bundlePackagesBC += getBundlePackagesBC(logical, m3Model);
		i+=1;
	}
	return model;
}

/*
 * Serializes an OSGi model in a binary value file.
 */
//public void serializeOSGiModel(OSGiModel model, loc file=OSGI_MODEL_PATH)
//	= writeBinaryValueFile(file, model);