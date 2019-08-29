@license{
Copyright (c) 2014 SCAVA Partners.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
}
module Java

extend lang::java::m3::Core;
import lang::java::m3::AST;
import util::FileSystem;
import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;
import IO;
import Set;
import util::SystemAPI;
import DateTime;
import lang::java::m3::ClassPaths;

private str MAVEN = getSystemProperty("MAVEN_EXECUTABLE");

@javaClass{m3.ClassPaths}
java map[loc, list[loc]] getClassPath(
	loc workspace, 
	map[str,loc] updateSites = (x : |http://download.eclipse.org/releases| + x | x <- ["indigo","juno","kepler","luna","mars","neon","oxygen","photon"]),
	loc mavenExecutable = |file:///usr/bin/mvn|);

@memo
set[loc] projectClassPath(loc workspace, ProjectDelta delta) {
	print("[Hang on] Computing classpath for project <workspace>...");
	try {
		map[loc, list[loc]] classPaths = getClassPath(workspace);
		print("Done.");
		return {*classPaths[cp] | cp <- classPaths};
	} catch e: {
		print("Error while computing the classpath: <e>");
		return {};
	}
}

set[loc] getSourceRoots(set[loc] folders) {
	set[loc] result = {};
	for (folder <- folders) {
		// only consult one java file per package tree
		top-down-break visit (crawl(folder)) {
			case directory(d, contents): {
				set[loc] roots = {};
				for (file(f) <- contents, toLowerCase(f.extension) == "java") {
					try {
						for (/package<p:[^;]*>;/ := readFile(f)) {
							packagedepth = size(split(".", trim(p)));
							roots += { d[path = intercalate("/", split("/", d.path)[..-packagedepth])] };
						}
						
						if (roots == {}) { // no package declaration means d is a root 
							roots += { d };	
						}
						
						break;						
					} catch _(_) : ;					
				}
				
				if (roots != {}) {
					result += roots;
				}
				else {
					fail; // continue searching subdirectories
				}
			}
		}
	}
	
	return result;
}

@M3Extractor{java()}
@memo
rel[Language, loc, M3] javaM3(loc project, ProjectDelta delta, map[loc repos,loc folders] checkouts, map[loc,loc] scratch) {	
	rel[Language, loc, M3] result = {};
	loc parent = (project | checkouts[repo].parent | repo <- checkouts);
	assert all(repo <- checkouts, checkouts[repo].parent == parent);
	
	for (repo <- checkouts) {
		loc checkout = checkouts[repo];
		list[loc] jars = toList(findJars({checkout}));
		list[loc] sources = toList(getSourceRoots({checkout}));
		list[loc] classPaths = toList(projectClassPath(checkout, delta)) + jars;

		for (f <- find(checkout, "java"), isFile(f)) {
			try {
				result += {<java(), f, createM3FromFile(f, sourcePath=sources, classPath=classPaths)>};
			} catch e: {
				println("Error building M3 model for <f>");
				result += {<java(), f, m3(|unknown:///|)>};
			}
		}
	}
	
	return result;
}

@ASTExtractor{java()}
@memo
rel[Language, loc, AST] javaAST(loc project, ProjectDelta delta, map[loc repos,loc folders] checkouts, map[loc,loc] scratch) {
	rel[Language, loc, AST] result = {};
	loc parent = (project | checkouts[repo].parent | repo <- checkouts);
	assert all(repo <- checkouts, checkouts[repo].parent == parent);
	
	for (repo <- checkouts) {
		loc checkout = checkouts[repo];
		list[loc] jars = toList(findJars({checkout}));
		list[loc] sources = toList(getSourceRoots({checkout}));
		list[loc] classPaths = toList(projectClassPath(checkout, delta)) + jars;
		
		for (f <- find(checkout, "java"), isFile(f)) {
			try {
				result += {<java(), f, declaration(createAstFromFile(f, true, sourcePath=sources, classPath=classPaths))>};
			} catch e: {
				println("Error building AST for <f>");
			}
		}
	}
	
	return result; 
}

public loc findFileInFolder(loc folder, str fileName) {
	try {
		return find(fileName, [folder]);
	}	
	catch "not-in-folder": {
		files = folder.ls;
		for(f <- files, isDirectory(f)) {
			return findFileInFolder(f,fileName);
		}
		return |file:///|;
	}
}

//TODO: check
public set[loc] findFileInFolderRecursively(loc folder, str fileName) {
	set[loc] findFiles(loc target, set[loc] prev) {
		try {
			mod_prev = find(fileName, [folder]) + prev;
			return {*findFiles(f,mod_prev) | f <- target.ls, isDirectory(f)}; 
		}	
		catch "not-in-folder": {
			return {*findFiles(f,prev) | f <- target.ls, isDirectory(f)};
		}
	}
	return findFiles(folder,{});
}

// this will become more interesting if we try to recover build information from meta-data
// for now we do a simple file search
// we have to find out what are "external" dependencies and also measure these!
set[loc] findSourceRoots(set[loc] checkouts) {
	bool containsFile(loc d) = isDirectory(d) ? (x <- d.ls && x.extension == "java" && isFile(x)) : false;
	return {*find(dir, containsFile) | dir <- checkouts};			 
}

// this may become more interesting if we try to recover dependency information from meta-data
// for now we do a simple file search
set[loc] findJars(set[loc] checkouts) {
	return { f | ch <- checkouts, f <- find(ch, "jar"), isFile(f) };
}

// this may become more interesting if we try to recover dependency information from meta-data
// for now we do a simple file search
set[loc] findClassFiles(set[loc] checkouts) {
	return { f | ch <- checkouts, f <- find(ch, "class"), isFile(f) };
}


@memo
public M3 systemM3(rel[Language, loc, M3] m3s, ProjectDelta delta = ProjectDelta::\empty()) {
	javaM3s = range(m3s[java()]);
	projectLoc = |java+tmp:///|+printDate(delta.date, "YYYYMMdd");
	if (javaM3s == {}) {
		throw undefined("No Java M3s available", projectLoc);
	}
	return composeJavaM3(projectLoc, javaM3s);
}
