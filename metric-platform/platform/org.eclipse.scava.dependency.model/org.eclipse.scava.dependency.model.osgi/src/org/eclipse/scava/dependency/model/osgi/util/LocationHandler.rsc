@license{
	Copyright (c) 2018 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
module org::eclipse::scava::dependency::model::osgi::util::LocationHandler

import Boolean;
import IO;
import List;
import Relation;
import Set;
import String;

import org::eclipse::scava::dependency::model::osgi::language::Syntax;


//--------------------------------------------------------------------------------
// Constants
//--------------------------------------------------------------------------------

public str SYSTEM_BUNDLE_ALIAS = "system.bundle";

public str SYSTEM_BUNDLE_NAME = "org.eclipse.osgi";

public str MANIFEST_RELATIVE_PATH = "META-INF/MANIFEST.MF";


//--------------------------------------------------------------------------------
// Functions
//--------------------------------------------------------------------------------

// The URI follows the form: |bundle://eclipse/<symbolicName>/<version>|.
public loc createBundleLogicalLoc(str symbolicName, str version="0.0.0")
	= |bundle://eclipse| + "/<symbolicName>/<version>";

public str getBundleSymbolicName(loc logical) 
	= split("/", logical.path)[1];

public str getBundleVersion(loc logical)
	= split("/", logical.path)[2];

public str getPackageQualifiedName(loc logical)
	= replaceAll(substring(logical.path,1),"/",".");

// It conforms to the M3 specification: |package://<package-path>|.
public loc createPackageLogicalLoc(str name)
	= |java+package:///| + "<replaceAll(name, ".", "/")>";

/*
 * Transforms a binary relation into a set. The last slot 
 * of the original relation has a map.
 */
public set[loc] binaryRelationToSet(rel[loc,map[str,str]] relation) 
	= {x | <x,y> <- relation};