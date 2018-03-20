module org::analyzer::osgi::util::OSGiUtil

import Boolean;
import IO;
import List;
import Relation;
import Set;
import String;

import org::eclipse::scava::dependency::model::OSGi::language::Syntax;


//--------------------------------------------------------------------------------
// Constants
//--------------------------------------------------------------------------------

public str SYSTEM_BUNDLE_ALIAS = "system.bundle";

public str SYSTEM_BUNDLE_NAME = "org.eclipse.osgi";

/*
 * Path of the generated analysis files.
 */
public loc ANALYSIS_FILES_PATH = |project://DependenciesAnalyzer/data/analysis|;

/*
 * Path of the program binary files.
 */
public loc BINARY_FILES_PATH = |project://DependenciesAnalyzer/data|;

/*
 * Path of the bundle-bundle logical locations relation based on the import package header.
 */
public loc OSGI_MODEL_PATH = BINARY_FILES_PATH + "osgi-model";

public loc M3_PATH = BINARY_FILES_PATH + "m3";

public loc M3_TEST_PATH = |project://DependenciesAnalyzer/tests/data/m3|;

/*
 * Path of the folder in the file system containing all the studied Eclipse bundles.
 */
public loc ECLIPSE_BUNDLES_PATH = |file:///Users/ochoa/Documents/cwi/crossminer/data/p2_repos/4.6/plugins|; 

/*
 * Extension of a JAR file.
 */
public str JAR_EXTENSION = "jar";

/*
 * Relative path in all bundles to get the correpsonding MANIFEST.MF file.
 */
public str MANIFEST_RELATIVE_PATH = "META-INF/MANIFEST.MF";

public loc RESULTS_PATH = ANALYSIS_FILES_PATH + "results.csv";


//--------------------------------------------------------------------------------
// Functions
//--------------------------------------------------------------------------------

/*
 * Creates the bundle logical location based on its symbolic name and version if
 * it exists. The URI follows the form: 
 * |bundle://eclipse/<symbolicName>/<version>|.
 */
public loc createBundleLogicalLoc(str symbolicName, str version="0.0.0")
	= |bundle://eclipse| + "/<symbolicName>/<version>";

/*
 * Returns the value of the "symbolic-name" parameter of a given bundle logical location.
 */
public str getBundleSymbolicName(loc logical) 
	= split("/", logical.path)[1];

/*
 * Returns the value of the version from a bundle logical location.
 */
public str getBundleVersion(loc logical)
	= split("/", logical.path)[2];

/*
 * Returns the qualified name from a bundle logical location.
 */
public str getPackageQualifiedName(loc logical)
	= replaceAll(substring(logical.path,1),"/",".");

/*
 * Creates the package logical location based on its name. It conforms to the M3 
 * specification: |package://<package-path>|.
 */
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