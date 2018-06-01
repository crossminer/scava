module org::eclipse::scava::dependency::model::osgi::language::Load

import org::eclipse::scava::dependency::model::osgi::language::internal::IO;
import org::eclipse::scava::dependency::model::osgi::language::Syntax;

import IO;
import ParseTree;


public start[Manifest] parseManifest(loc file) {
	manifestMap = loadManifest(file);
	manifest = "<for (k <- manifestMap) {><k>: <manifestMap[k]><}>";
	return parse(#start[Manifest], manifest);
}