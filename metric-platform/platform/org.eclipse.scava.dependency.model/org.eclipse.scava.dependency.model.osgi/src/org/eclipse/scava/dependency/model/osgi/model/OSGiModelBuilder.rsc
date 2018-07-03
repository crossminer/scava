@license{
	Copyright (c) 2018 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
module org::eclipse::scava::dependency::model::osgi::model::OSGiModelBuilder

import IO;
import Set;
import String;
import Relation;

import org::eclipse::scava::dependency::model::osgi::language::Load;
import org::eclipse::scava::dependency::model::osgi::language::Syntax;
// TODO: Uncomment once the Rascal bug is fixed (managing imports).
//import org::eclipse::scava::dependency::model::osgi::model::resolvers::BundleResolver;
//import org::eclipse::scava::dependency::model::osgi::model::resolvers::PackageResolver;

import List;
import Type;

import org::eclipse::scava::dependency::model::osgi::util::LocationHandler;
import org::eclipse::scava::dependency::model::osgi::util::VersionsHelper;

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
	model.headers = {<logical, {h | /Header h := manifest}>};
	
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

//----------------------------------------------------
// TODO: Move to the BundleResolver module once the Rascal bug is fixed (managing imports).
// If no version is specified it is set to 0.0.0 as defined in the OSGi specification R.6.
public rel[loc,loc,map[str,str]] getBundleLocation(OSGiModel model) {
	symbolicName = (/HeaderBundleSymbolicName h := model.headers) ? "<h.name>" : "";
	vers = (/HeaderBundleVersion h := model.headers) ? "<h.version>" : "0.0.0";	
	return {<createBundleLogicalLoc(symbolicName, version=vers), model.id, ("version" : vers, "symbolic-name" : symbolicName)>};
}

public rel[loc,loc,map[str,str]] getRequiredBundles(loc logical, OSGiModel model) {
	requiredBundles = {};
	for(/RequireBundle reqBundle := model.headers) {
		params = setRequiredBundlesParams(reqBundle, model);
		bundleLoc = createBundleLogicalLoc("<reqBundle.symbolicName>", 
			version="<params["lower-version"]>-<params["upper-version"]>");
		requiredBundles += <logical, bundleLoc, params>;
	}
	
	return requiredBundles;
}

// A version == -1 represents infinity. 
// All versions start either with '[' (inclusive) or '('(exclusive).
private map[str,str] setRequiredBundlesParams(RequireBundle reqBundle, OSGiModel model) {
	params = (
		"lower-version" : "[0.0.0", 
		"upper-version" : "(-1", 
		"visibility" : "private", 
		"resolution" : "mandatory",
		"version-spec" : "none");
		
	if(/(RequireBundleParameter)`bundle-version = <QuotedHybridVersion v>` := reqBundle) {
		params = getReqBundleVersionRange(v,params);
	}
	if(/DirVisibilityKeyword visibility := reqBundle){
		params += ("visibility" : "<visibility>");
	}
	if(/DirResolutionKeyword resolution := reqBundle){
		params += ("resolution" : "<resolution>");
	}
		
	return params;
}

private map[str,str] getReqBundleVersionRange(QuotedHybridVersion ver, map[str,str] params) {
	vRange;
	vSpec;
	
	if((QuotedHybridVersion)`"<Version version>"` := ver) {
		vRange = <"[<version>", "(-1">;
		vSpec = "strict";
	}
	else if ((QuotedHybridVersion)`<VersionRange versionRange>` := ver) {
		vRange = (/(VersionRange)`"[<Version vLB1>, <Version vUB1>]"` := versionRange) ? <"[<vLB1>","[<vUB1>"> :
				(/(VersionRange)`"[<Version vLB2>, <Version vUB2>)"` := versionRange) ? <"[<vLB2>","(<vUB2>"> :
				(/(VersionRange)`"(<Version vLB3>, <Version vUB3>]"` := versionRange) ? <"(<vLB3>","[<vUB3>"> :
				(/(VersionRange)`"(<Version vLB4>, <Version vUB4>)"` := versionRange) ? <"(<vLB4>","(<vUB4>"> :
				<"[0.0.0","(-1">;
		vSpec = "range";
	}
	else {
		vRange = <"[0.0.0","(-1">;
		vSpec = "none";
	}
	
	params += ("lower-version" : vRange[0]);
	params += ("upper-version" : vRange[1]);
	params += ("version-spec" : vSpec);
	return params;
}

//----------------------------------------------------
// TODO: Move to the PackageResolver module once the Rascal bug is fixed (managing imports).
// It returns one tuple per imported package name.
public rel[loc,loc,map[str,str]] getExportPackages(loc logical, OSGiModel model) {
	exportPackages = {};
	for(/ExportPackage expPackage := model.headers) {
		params = setExportPackagesParams(expPackage);
		exportPackages += {<logical, createPackageLogicalLoc("<name>"), params> | name <- expPackage.packageNames};
	}
	return exportPackages;
}

// If no version is specified it is set to 0.0.0 as defined in the OSGi specification R.6.
private map[str,str] setExportPackagesParams(ExportPackage expPackage) {
	params = (
		"version" : "0.0.0", 
		"split" : "none", 
		"x-internal" : "false", 
		"x-friends" : "none",
		"version-spec" : "none");
			
	if(/(ExportPackageParameter)`version = <SimpleVersion group>` := expPackage ||
		/(ExportPackageParameter)`version := <SimpleVersion group>` := expPackage) {
		params += ("version" : (/(SimpleVersion)`<QuotedVersion v>` := group) ? "<v>"[1..-1] : "<group>");
		params += ("version-spec" : "strict");
	}
	if(/(ExportPackageParameter)`<QualifiedName bundleSymbolicName> = <AttributeSplit split>` := expPackage) {
		params += ("split" : "<bundleSymbolicName>");;
	}
	if(/(ExportPackageParameter)`x-internal := <Boolean xInternal>` := expPackage) {
		params += ("x-internal" : "<xInternal>");
	}
	if(/(ExportPackageParameter)`x-friends := <HybridQualifiedName xFriends>` := expPackage) {
		params += ("x-friends" : ("" | it + "<name>," | /QualifiedName name := xFriends));
	}
		
	return params;
}

// It returns one tuple per imported package name.
public rel[loc,loc,map[str,str]] getImportPackages(loc logical, OSGiModel model) {
	importPackages = {};
	for(/ImportPackage impPackage := model.headers) {
		params = setImportPackagesParams(impPackage);
		importPackages += {<logical, createPackageLogicalLoc("<name>"), params> | name <- impPackage.packageNames};
	}
	return importPackages;
}

// If no version is specified it is set to 0.0.0 as defined in the OSGi specification R.6.
// A version == -1 represents infinity. 
// All versions start either with '[' (inclusive) or '('(exclusive).
private map[str,str] setImportPackagesParams(ImportPackage impPackage) {
	params = (
		"lower-version" : "[0.0.0", 
		"upper-version" : "(-1", 
		"resolution" : "mandatory", 
		"split" : "none", 
		"x-installation" : "none",
		"version-spec" : "none");
		
	if(/(ImportPackageParameter)`version = <UnrestrictedHybridVersion v>` := impPackage ||
		/(ImportPackageParameter)`version := <UnrestrictedHybridVersion v>` := impPackage) {
		params = getImpPackageVersionRange(v,params);
	}
	if(/DirResolutionKeyword resolution := impPackage) {
		params += ("resolution" : "<resolution>");
	}
	if(/(ImportPackageParameter) `<QualifiedName bundleSymbolicName> = <AttributeSplit split>` := impPackage) {
		params += ("split" : "<bundleSymbolicName>");;
	}
	if(/DirectiveXInstallation xInstallation := impPackage) {
		params += ("x-installation" : "<xInstallation>");
	}

	return params;
}

private map[str,str] getImpPackageVersionRange(UnrestrictedHybridVersion ver, map[str,str] params) {
	vRange;
	vSpec;
	
	if((UnrestrictedHybridVersion)`"<Version version>"` := ver || 
	(UnrestrictedHybridVersion)`<Version version>` := ver) {
		vRange = <"[<version>", "(-1">;
		vSpec = "strict";
	}
	else if ((UnrestrictedHybridVersion)`<VersionRange versionRange>` := ver) {
		vRange = (/(VersionRange)`"[<Version vLB1>, <Version vUB1>]"` := versionRange) ? <"[<vLB1>","[<vUB1>"> :
				(/(VersionRange)`"[<Version vLB2>, <Version vUB2>)"` := versionRange) ? <"[<vLB2>","(<vUB2>"> :
				(/(VersionRange)`"(<Version vLB3>, <Version vUB3>]"` := versionRange) ? <"(<vLB3>","[<vUB3>"> :
				(/(VersionRange)`"(<Version vLB4>, <Version vUB4>)"` := versionRange) ? <"(<vLB4>","(<vUB4>"> :
				<"[0.0.0","(-1">;
		vSpec = "range";
	}
	else {
		vRange = <"[0.0.0","(-1">;
		vSpec = "none";
	}
	
	params += ("lower-version" : vRange[0]);
	params += ("upper-version" : vRange[1]);
	params += ("version-spec" : vSpec);
	return params;
}

// It returns one tuple per imported package name.
public rel[loc,loc,map[str,str]] getDynamicImportPackages(loc logical, OSGiModel model) {
	dynamicImportPackages = {};
	for(/DynamicImportDescription dynImpDescription := model.headers) {
		params = setDynamicImportPackagesParams(dynImpDescription);
		names = {"<packages>" | /QualifiedName packages := dynImpDescription};
		if(names != {}) {
			dynamicImportPackages += {<logical, createPackageLogicalLoc(name), params> | name <- names};
		}
	}
	return dynamicImportPackages;
}

// If no version is specified it is set to 0.0.0 as defined in the OSGi specification R.6.
// A version == -1 represents infinity. 
// All versions start either with '[' (inclusive) or '('(exclusive).
private map[str,str] setDynamicImportPackagesParams(DynamicImportDescription dynImpDescription) {
	params = (
		"lower-version" : "[0.0.0", 
		"upper-version" : "(-1", 
		"bundle-lower-version" : "[0.0.0", 
		"bundle-upper-version" : "(-1", 
		"bundle-symbolic-name" : "none",
		"version-spec" : "none",
		"bundle-version-spec" : "none");
		
	if(/(DynamicImportPackageParameter)`version = <QuotedHybridVersion v>` := dynImpDescription) {
		params = getDynImpPackageVersionRange(v,params,"lower-version","upper-version","version-spec");
	}
	if(/(DynamicImportPackageParameter)`bundle-version = <QuotedHybridVersion v>` := dynImpDescription) {
		params = getDynImpPackageVersionRange(v,params,"bundle-lower-version","bundle-upper-version","bundle-version-spec");
	}
	if(/(DynamicImportPackageParameter) `bundle-symbolic-name = <QualifiedName bundleSymbolicName>` := dynImpDescription) {
		params += ("bundle-symbolic-name" : "<bundleSymbolicName>");
	}

	return params;
}

private map[str,str] getDynImpPackageVersionRange(QuotedHybridVersion ver, map[str,str] params, 
	str paramLVersion, str paramUVersion, str paramSpec) {
	vRange;
	vSpec;
	
	if((QuotedHybridVersion)`"<Version version>"` := ver) {
		vRange = <"[<version>", "(-1">;
		vSpec = "strict";
	}
	else if ((QuotedHybridVersion)`<VersionRange versionRange>` := ver) {
		vRange = (/(VersionRange)`"[<Version vLB1>, <Version vUB1>]"` := versionRange) ? <"[<vLB1>","[<vUB1>"> :
				(/(VersionRange)`"[<Version vLB2>, <Version vUB2>)"` := versionRange) ? <"[<vLB2>","(<vUB2>"> :
				(/(VersionRange)`"(<Version vLB3>, <Version vUB3>]"` := versionRange) ? <"(<vLB3>","[<vUB3>"> :
				(/(VersionRange)`"(<Version vLB4>, <Version vUB4>)"` := versionRange) ? <"(<vLB4>","(<vUB4>"> :
				<"[0.0.0","(-1">;
		vSpec = "range";
	}
	else {
		vRange = <"[0.0.0","(-1">;
		vSpec = "none";
	}
	
	params += (paramLVersion : vRange[0]);
	params += (paramUVersion : vRange[1]);
	params += (paramSpec : vSpec);
	return params;
}