@license{
	Copyright (c) 2018 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
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