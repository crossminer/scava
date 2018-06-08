@license{
	Copyright (c) 2018 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
module org::eclipse::scava::dependency::model::osgi::language::Util

import org::eclipse::scava::dependency::model::osgi::language::Syntax;


public str getExportedPackageQualifiedName(ExportPackage package) {
	if(/QualifiedName name := package) {
		return "<name>";
	}
	return "";
}

public str getImportedPackageQualifiedName(ImportPackage package) {
	if(/QualifiedName name := package) {
		return "<name>";
	}
	return "";
}

public str getRequiredBundleQualifiedName(RequireBundle bundle) {
	if(/QualifiedName name := bundle) {
		return "<name>";
	}
	return "";
}

public bool reqBundleHasVersion(RequireBundle bundle) 
	= (/QuotedHybridVersion v := bundle) ? true : false;
