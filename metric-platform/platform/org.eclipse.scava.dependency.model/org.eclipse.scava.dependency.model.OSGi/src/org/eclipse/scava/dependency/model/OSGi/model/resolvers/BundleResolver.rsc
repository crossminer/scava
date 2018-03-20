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


//--------------------------------------------------------------------------------
// Functions
//--------------------------------------------------------------------------------

/*
 * Returns a relation where each element is a duple whose first value refers to the symbolic 
 * name of the bundle and the second value refers to the physical location of the analyzed bundle.
 * If no version is specified it is set to 0.0.0 as defined in the OSGi specification R.6.
 */
public tuple[loc,loc,map[str,str]] getBundleLocation(loc jar, start[Manifest] manifest) {
	symbolicName = (/HeaderBundleSymbolicName h := manifest) ? "<h.name>" : "";
	vers = (/HeaderBundleVersion h := manifest) ? "<h.version>" : "0.0.0";	
	return <createBundleLogicalLoc(symbolicName, version=vers), jar, ("version" : vers, "symbolic-name" : symbolicName)>;
}

/*
 * Returns a relacion mapping the logical location of the given bundle to the logical 
 * location of each one of its required bundles, plus some additional parameters.
 */
public rel[loc,loc,map[str,str]] getRequiredBundles(loc logical, OSGiModel model) {
	rel[loc,loc,map[str,str]] requiredBundles = {};
	
	for(/RequireBundle reqBundle := model.manifests[logical]) {
		params = setRequiredBundlesParams(reqBundle, model);
		bundleLoc = suggestReqBundle("<reqBundle.symbolicName>", params["lower-version"], params["upper-version"], model);
		
		params = (bundleLoc != |bundle://eclipse|) 
			? modifyReqBundleResolvedParam(true, params) : params;
		bundleLoc = (bundleLoc == |bundle://eclipse|) 
			? createBundleLogicalLoc("<reqBundle.symbolicName>") : bundleLoc;
		
		requiredBundles += <logical, bundleLoc, params>;
	}
	
	return requiredBundles;
}

/*
 * Returns a map with a Require-Bundle header related parameters. The considered parameters
 * correspond to resolved, lower-version, upper-version, visibility and resolution. The resolved 
 * boolean specifies if the required bundle is part of the Eclipse projects corpus.
 * A version == -1 represents infinity. All versions start either with '[' (inclusive) or '('
 * (exclusive).
 */
private map[str,str] setRequiredBundlesParams(RequireBundle reqBundle, OSGiModel model) {
	map[str,str] params = (
		"resolved" : "false", 
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

/*
 * Modifies the value of an imported package "resolved" parameter.
 */
private map[str,str] modifyReqBundleResolvedParam(bool resolved, map[str,str] params) {
	params += ("resolved" : "<resolved>");
	return params;	
}

/*
 * Returns an updated version of the params map received as parameter. Lower and 
 * upper version values, as well as version specification are updated. 
 */ 
private map[str,str] getReqBundleVersionRange(QuotedHybridVersion ver, map[str,str] params) {
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
	
	params += ("lower-version" : vRange[0]);
	params += ("upper-version" : vRange[1]);
	params += ("version-spec" : vSpec);
	return params;
}

/*
 * Returns the location of the suggested required bundle (a bundle that respects the 
 * version range and that has the highest version). This method only considers major 
 * and minor version values as suggested by the OSGi specification R.6. Each version 
 * follows the grammar: ("("|"[") major(.minor(.micro(.qualifier)?)?)?
 */
public loc suggestReqBundle(str symbolicName, str lowerVersion, str upperVersion, OSGiModel model) {
	symbolicName = (symbolicName == SYSTEM_BUNDLE_ALIAS) ? SYSTEM_BUNDLE_NAME : symbolicName;
	vers = {params["version"] | <logical, physicial, params> <- model.locations, getBundleSymbolicName(logical) == symbolicName};
	highestVers = highestVersionWithBounds(vers,lowerVersion,upperVersion);
	
	return (highestVers in vers) ? createBundleLogicalLoc(symbolicName,version=highestVers) : |bundle://eclipse|;
}