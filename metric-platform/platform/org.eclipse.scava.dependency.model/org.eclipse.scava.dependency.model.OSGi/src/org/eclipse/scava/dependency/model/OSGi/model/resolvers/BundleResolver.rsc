module org::eclipse::scava::dependency::model::OSGi::model::resolvers::BundleResolver

import IO;
import List;
import Relation;
import Set;
import String;
import ValueIO;

import org::eclipse::scava::dependency::model::OSGi::language::Load;
import org::eclipse::scava::dependency::model::OSGi::language::Syntax;
import org::eclipse::scava::dependency::model::OSGi::model::OSGiModelBuilder;
import org::eclipse::scava::dependency::model::OSGi::util::OSGiUtil;
import org::eclipse::scava::dependency::model::OSGi::util::VersionsHelper;

// If no version is specified it is set to 0.0.0 as defined in the OSGi specification R.6.
public tuple[loc,map[str,str]] getBundleLocation(start[Manifest] manifest) {
	symbolicName = (/HeaderBundleSymbolicName h := manifest) ? "<h.name>" : "";
	vers = (/HeaderBundleVersion h := manifest) ? "<h.version>" : "0.0.0";	
	return <createBundleLogicalLoc(symbolicName, version=vers), ("version" : vers, "symbolic-name" : symbolicName)>;
}

public rel[loc,map[str,str]] getRequiredBundles(OSGiModel model) {
	requiredBundles = {};
	for(/RequireBundle reqBundle := model.headers) {
		params = setRequiredBundlesParams(reqBundle, model);
		bundleLoc = createBundleLogicalLoc("<reqBundle.symbolicName>", 
			version="<params["lower-version"]>-<params["upper-version"]>");
		requiredBundles += <bundleLoc, params>;
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