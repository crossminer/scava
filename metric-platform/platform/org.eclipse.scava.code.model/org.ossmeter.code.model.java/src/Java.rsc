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
import util::Benchmark;
import ValueIO;

private str MAVEN = getSystemProperty("MAVEN_EXECUTABLE");

@javaClass{org.rascalmpl.library.lang.java.m3.internal.ClassPaths}
java map[loc, list[loc]] getClassPath(
	loc checkout, 
	map[str,loc] updateSites = (x : |http://download.eclipse.org/releases| + x | x <- ["indigo","juno","kepler","luna","mars","neon","oxygen","photon"]),
	loc mavenExecutable = |file:///usr/bin/mvn|);

@memo
set[loc] projectClassPath(loc repo, loc checkout, loc scratch, ProjectDelta delta) {
	loc cachedClassPath = scratch + "Classpath.cache";

	// Classpath is inferred from pom.xml and MANIFEST.MF files only.
	// If the delta does not affect these files, there is no need to recompute it.
	if (!deltaAffectsDependencies(delta, repo) && isFile(cachedClassPath)) {
		print("Reusing previously-computed classpath for <repo>.");
		return readBinaryValueFile(#set[loc], cachedClassPath);
	}

	try {
		print("Computing classpath for <repo>... Hang on!");
		int before = getMilliTime();
		map[loc, list[loc]] classPaths = getClassPath(checkout);
		set[loc] ret = {*classPaths[cp] | cp <- classPaths};
		int after = getMilliTime();
		print("Found <size(ret)> JARs to include in the classpath in <(after - before) / 1000>s. Caching it in scratch folders.");
		writeBinaryValueFile(cachedClassPath, ret);
		return ret;
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

bool deltaAffectsDependencies(ProjectDelta delta, loc repo) {
	for (/rd:vcsRepositoryDelta(vcsRepository(repo), _, _) := delta, /VcsCommitItem co := rd) {
		if (endsWith(co.path, "pom.xml") || endsWith(co.path, "MANIFEST.MF"))
			return true;
	}

	return false;
}

bool deltaAffectsJavaFiles(ProjectDelta delta, loc repo) {
	for (/rd:vcsRepositoryDelta(vcsRepository(repo), _, _) := delta, /VcsCommitItem co := rd) {
		if (endsWith(co.path, ".java"))
			return true;
	}

	return false;
}

@memo
tuple[set[M3], set[Declaration]] buildASTsAndM3s(loc repo, ProjectDelta delta, loc checkout, loc scratch) {
	print("delta=<delta>");
	loc cachedModels = scratch + "Models.cache";
	
	// M3 and AST models are built from .java files + dependencies only.
	// If the delta does not affect them, there is no need to recompute them.
	if (!deltaAffectsDependencies(delta, repo) && !deltaAffectsJavaFiles(delta, repo) && isFile(cachedModels)) {
		print("Reusing cached Java models for <repo>.");
		int before = getMilliTime();
		tuple[set[M3], set[Declaration]] cached = readBinaryValueFile(#tuple[set[M3], set[Declaration]], cachedModels);
		int after = getMilliTime();
		print("Reading existing Java models took <(after - before) / 1000>s.");

		return cached;
	} else {
		list[loc] jars = toList(findJars({checkout}));
		list[loc] sources = toList(getSourceRoots({checkout}));
		list[loc] classPaths = toList(projectClassPath(repo, checkout, scratch, delta)) + jars;

		set[loc] files = {f | f <- find(checkout, "java"), isFile(f)};
		print("Now building Java models for <repo>; <size(files)> files to process...");
		int before = getMilliTime();
		tuple[set[M3], set[Declaration]] allModels = createM3sAndAstsFromFiles(files, sourcePath=sources, classPath=classPaths);
		int after = getMilliTime();
		print("Done in <(after - before) / 1000>s. Caching Java models for future days.");
		
		before = getMilliTime();
		writeBinaryValueFile(cachedModels, allModels);
		after = getMilliTime();
		print("Serializing Java models took <(after - before) / 1000>s.");
		
		return allModels;
	}
}

@M3Extractor{java()}
@memo
rel[Language, loc, M3] javaM3(loc project, ProjectDelta delta, map[loc repos, loc folders] checkouts, map[loc, loc] scratchFolders) {
	rel[Language, loc, M3] result = {};
	loc parent = (project | checkouts[repo].parent | repo <- checkouts);
	assert all(repo <- checkouts, checkouts[repo].parent == parent);
	
	for (repo <- checkouts) {
		loc checkout = checkouts[repo];
		loc scratch = scratchFolders[repo];
		tuple[set[M3] m3s, set[Declaration] decls] allModels = buildASTsAndM3s(repo, delta, checkout, scratch);
		
		result += {<java(), m.id, m> | m <- allModels.m3s};
	}
	
	return result;
}

@ASTExtractor{java()}
@memo
rel[Language, loc, AST] javaAST(loc project, ProjectDelta delta, map[loc repos, loc folders] checkouts, map[loc, loc] scratchFolders) {
	rel[Language, loc, AST] result = {};
	loc parent = (project | checkouts[repo].parent | repo <- checkouts);
	assert all(repo <- checkouts, checkouts[repo].parent == parent);
	
	for (repo <- checkouts) {
		loc checkout = checkouts[repo];
		loc scratch = scratchFolders[repo];
		tuple[set[M3] m3s, set[Declaration] decls] allModels = buildASTsAndM3s(repo, delta, checkout, scratch);
		
		result += {<java(), |file:///<d.decl.path>|, declaration(d)> | d <- allModels.decls};
	}
	
	return result; 
}

// this may become more interesting if we try to recover dependency information from meta-data
// for now we do a simple file search
set[loc] findJars(set[loc] checkouts) {
	return { f | ch <- checkouts, f <- find(ch, "jar"), isFile(f) };
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
