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
 * Transformas a ternary relation into a binary relation.
 * The last slot of the original relation has a map.
 */
public rel[loc,loc] toBinaryRelation(rel[loc,loc,map[str,str]] relation) 
	= {<x,y> | <x,y,z> <- relation};

/*
 * Transforms a binary relation into a set. The last slot 
 * of the original relation has a map.
 */
public set[loc] binaryRelationToSet(rel[loc,map[str,str]] relation) 
	= {x | <x,y> <- relation};