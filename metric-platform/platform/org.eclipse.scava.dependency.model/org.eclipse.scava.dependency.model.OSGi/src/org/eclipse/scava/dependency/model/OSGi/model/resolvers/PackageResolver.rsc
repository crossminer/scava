module org::eclipse::scava::dependency::model::OSGi::model::resolvers::PackageResolver

import IO;
import List;
import Relation;
import Set;
import Type;

import org::eclipse::scava::dependency::model::OSGi::language::Syntax;
import org::eclipse::scava::dependency::model::OSGi::model::OSGiModelBuilder;
import org::eclipse::scava::dependency::model::OSGi::model::resolvers::BundleResolver;
import org::eclipse::scava::dependency::model::OSGi::util::OSGiUtil;
import org::eclipse::scava::dependency::model::OSGi::util::VersionsHelper;


//--------------------------------------------------------------------------------
// Functions
//--------------------------------------------------------------------------------

/*
 * Returns a relation mapping the logical location of the given bundle to the logical 
 * location of each one of its exported packages. Params are included.
 */
public rel[loc,loc,map[str,str]] getExportPackages(loc logical, OSGiModel model) {
	rel[loc,loc,map[str,str]] exportPackages = {};
	for(/ExportPackage expPackage := model.manifests[logical]) {
		params = setExportPackagesParams(expPackage);
		exportPackages += {<logical, createPackageLogicalLoc("<name>"), params> | name <- expPackage.packageNames};
	}
	return exportPackages;
}

/*
 * Returns a map with an Export-Package header related parameters. The considered parameters
 * correspond to version, split, x-internal, x-installation, and version-spec. 
 */
private map[str,str] setExportPackagesParams(ExportPackage expPackage) {
	map[str,str] params = (
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

/*
 * Returns a relation mapping the logical location of the given bundle to the logical 
 * location of each one of its imported packages, plus some additional parameters. It 
 * returns just one tuple per imported package name. The version or range version is 
 * included in the package location.
 */
public rel[loc,loc,map[str,str]] getImportPackages(loc logical, OSGiModel model) {
	rel[loc,loc,map[str,str]] importPackages = {};
	
	for(/ImportPackage impPackage := model.manifests[logical]) {
		params = setImportPackagesParams(impPackage);
		importPackages += {<logical, createPackageLogicalLoc("<name>"), modifyImpPackageResolvedParam("<name>",params,model)> | 
			name <- impPackage.packageNames};
	}
	
	return importPackages;
}

/*
 * Returns a map with an Import-Package header related parameters. The considered parameters
 * correspond to resolved, lower-version, upper-version, resolution, split, x-installation,
 * and version-spec.
 */
  // TODO: split - multiple params?
private map[str,str] setImportPackagesParams(ImportPackage impPackage) {
	map[str,str] params = (
			"resolved" : "false", 
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

/*
 * Modifies the value of an imported package "resolved" parameter.
 */
private map[str,str] modifyImpPackageResolvedParam(str symbolicName, map[str,str] params, OSGiModel model) {
	if(size(model.exportedPackages) > 0) {
		vers = [p["version"] | p <- model.exportedPackages[_,createPackageLogicalLoc("<symbolicName>")]];
		params += ("resolved" : "<versionExists(vers, params["lower-version"], params["upper-version"])>");
	}
	return params;	
}

/*
 * Returns an updated version of the params map received as parameter. Lower and 
 * upper version values, as well as version specification are updated. 
 */ 
private map[str,str] getImpPackageVersionRange(UnrestrictedHybridVersion ver, map[str,str] params) {
	tuple[str,str] vRange;
	str vSpec;
	
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

/*
 * Returns a relation mapping the logical location of the given bundle to the logical 
 * location of each one of its dynamically imported packages, plus some additional parameters. 
 * It returns just one tuple per imported package name. The version or range version is 
 * included in the package location.
 */
public rel[loc,loc,map[str,str]] getDynamicImportPackages(loc logical, OSGiModel model) {
	rel[loc,loc,map[str,str]] dynamicImportPackages = {};
	
	for(/DynamicImportDescription dynImpDescription := model.manifests[logical]) {
		params = setDynamicImportPackagesParams(dynImpDescription);
		set[str] names = {"<packages>" | /QualifiedName packages := dynImpDescription};
		if(size(names) > 0) {
			dynamicImportPackages += {<logical, createPackageLogicalLoc(name), modifyImpPackageResolvedParam(name,params,model)> | 
				name <- names};
		}
	}
	
	return dynamicImportPackages;
}

/*
 * Returns a map with a DynamicImport-Package header related parameters. The considered parameters
 * correspond to resolved, lower-version, upper-version, bundle-lower-version, bundle-upper-version,
 * bundle-symbolic-name, version-spec, and bundle-version-spec.
 */
private map[str,str] setDynamicImportPackagesParams(DynamicImportDescription dynImpDescription) {
	map[str,str] params = (
			"resolved" : "false", 
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

/*
 * Returns an updated version of the params map received as parameter. Lower and 
 * upper version values, as well as version specification are updated. 
 */ 
private map[str,str] getDynImpPackageVersionRange(QuotedHybridVersion ver, map[str,str] params, 
	str paramLVersion, str paramUVersion, str paramSpec) {
	tuple[str,str] vRange;
	str vSpec;
	
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