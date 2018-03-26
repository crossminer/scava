module org::eclipse::scava::dependency::model::OSGi::language::Load

import org::eclipse::scava::dependency::model::OSGi::language::internal::IO;
import org::eclipse::scava::dependency::model::OSGi::language::Syntax;

import IO;
import ParseTree;


public start[Manifest] parseManifest(loc file) {
	manifestMap = loadManifest(file);
	manifest = "<for (k <- manifestMap) {><k>: <manifestMap[k]><}>";
	return parse(#start[Manifest], manifest);
}