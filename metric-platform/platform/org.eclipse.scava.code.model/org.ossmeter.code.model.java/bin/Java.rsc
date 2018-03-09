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
import util::SystemAPI;
import DateTime;
  
private str MAVEN = getSystemProperty("MAVEN_EXECUTABLE");
   
@javaClass{org.rascalmpl.library.lang.java.m3.internal.ClassPaths}
java map[loc,list[loc]] getClassPath(
  loc workspace,
  map[str,loc] updateSites = (x : |http://download.eclipse.org/releases| + x | x <- ["juno","kepler","luna"]),
  loc mavenExecutable = MAVEN == "" ? |file:///usr/local/bin/mvn| : |file:///<MAVEN>|);
 
 
@memo
map[loc,list[loc]] inferClassPaths(loc workspace, ProjectDelta delta) {
  if (daysDiff(delta.date, now()) < 14) { 
    try {
      return getClassPath(workspace);
    }
    catch Java("BuildException", msg, cause): {
      println("Could not infer classpath using Maven: <msg>, due to <cause>.");
    }
    catch Java("BuildException", msg): {
      println("Could not infer classpath using Maven: <msg>.");
    }
  }
  
  return (d : [*findJars({d})] | d <- workspace.ls, isDirectory(d));
}

private set[loc] getSourceRoots(set[loc] folders) {
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
  
  // TODO: we will add caching on disk again and use the deltas to predict what to re-analyze and what not
  try {
    map[loc,list[loc]] classpaths = inferClassPaths(parent, delta);
    for (repo <- checkouts) {
      checkout = checkouts[repo];
      sources = getSourceRoots({checkout});
      setEnvironmentOptions({*(classpaths[checkout]?[])}, sources);
    
      result += {<java(), f, createM3FromFile(f)> | f <- find(checkout, "java"), isFile(f)};
    }
  }
  catch "not-maven": {
    jars = findJars(checkouts.folders);
    
    for (repo <- checkouts) {
      checkout = checkouts[repo];
      sources = getSourceRoots({checkout});
      setEnvironmentOptions(jars, sources);
    
      result += {<java(), f, createM3FromFile(f)> | f <- find(checkout, "java"), isFile(f)};
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
  
  // TODO: we will add caching on disk again and use the deltas to predict what to re-analyze and what not
  try {
    map[loc,list[loc]] classpaths = inferClassPaths(parent, delta);
    for (repo <- checkouts) {
      checkout = checkouts[repo];
      sources = getSourceRoots({checkout});

      // TODO: turn classpath into a list
      setEnvironmentOptions({*(classpaths[checkout]?[])}, sources);
      result += {<java(), f, declaration(createAstFromFile(f, true))> | f <- find(checkout, "java"), isFile(f) };
    }
  }
  catch "not-maven": {
    jars = findJars(checkouts.folders);
    
    for (repo <- checkouts) {
      sources = getSourceRoots({checkout});
      setEnvironmentOptions(jars, sources);
    
      result += {<java(), f, declaration(createAstFromFile(f, true))> | f <- find(checkout, "java"), isFile(f) };
    }
  }
  
  return result; 
 
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
  return composeM3(projectLoc, javaM3s);  
}
