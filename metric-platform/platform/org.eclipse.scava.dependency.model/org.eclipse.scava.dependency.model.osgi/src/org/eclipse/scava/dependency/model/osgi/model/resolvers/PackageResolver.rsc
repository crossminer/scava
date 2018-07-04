@license{
	Copyright (c) 2018 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
module org::eclipse::scava::dependency::model::osgi::model::resolvers::PackageResolver

import IO;
import List;
import Relation;
import Set;
import Type;

import org::eclipse::scava::dependency::model::osgi::language::Syntax;
import org::eclipse::scava::dependency::model::osgi::model::OSGiModel;
import org::eclipse::scava::dependency::model::osgi::model::resolvers::BundleResolver;
import org::eclipse::scava::dependency::model::osgi::util::LocationHandler;
import org::eclipse::scava::dependency::model::osgi::util::VersionsHelper;


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