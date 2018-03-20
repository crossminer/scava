module org::eclipse::scava::dependency::model::OSGi::language::Load

import org::eclipse::scava::dependency::model::OSGi::language::internal::IO;
import org::eclipse::scava::dependency::model::OSGi::language::Syntax;

import IO;
import ParseTree;


//--------------------------------------------------------------------------------
// Functions
//--------------------------------------------------------------------------------

/*
 * Parse manifest file.
 */
public start[Manifest] parseManifest(str manifestFile) {
	manifestMap = loadManifest(manifestFile);
	manifest = "<for (k <- manifestMap) {><k>: <manifestMap[k]><}>";
	return parse(#start[Manifest], manifest, allowAmbiguity=false);
}

/*
 * Parse manifest file.
 */
public start[Manifest] parseManifest(loc manifestFile) {
	manifestMap = loadManifest(manifestFile);
	manifest = "<for (k <- manifestMap) {><k>: <manifestMap[k]><}>";
	return parse(#start[Manifest], manifest, allowAmbiguity=false);
}