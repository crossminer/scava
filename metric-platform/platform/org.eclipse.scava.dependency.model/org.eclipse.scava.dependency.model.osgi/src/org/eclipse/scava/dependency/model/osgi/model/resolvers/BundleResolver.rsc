@license{
	Copyright (c) 2018 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
module org::eclipse::scava::dependency::model::osgi::model::resolvers::BundleResolver

import IO;
import List;
import Relation;
import Set;
import String;

import org::eclipse::scava::dependency::model::osgi::language::Load;
import org::eclipse::scava::dependency::model::osgi::language::Syntax;
import org::eclipse::scava::dependency::model::osgi::model::OSGiModel;
import org::eclipse::scava::dependency::model::osgi::util::LocationHandler;
import org::eclipse::scava::dependency::model::osgi::util::VersionsHelper;

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