@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module Includes

import lang::php::ast::AbstractSyntax;
import lang::php::ast::System;
import lang::php::ast::Scopes;

import lang::php::analysis::includes::IncludesInfo;
import lang::php::analysis::includes::QuickResolve;
import lang::php::analysis::evaluators::DefinedConstants;
import lang::php::util::LocUtils;

import Prelude;
import util::Math;

import IO;

private IncludesInfo buildIncludesInfo(System sys, loc baseloc) {
	map[loc,set[ConstItemExp]] loc2consts = ( l : { cdef[e=normalizeExpr(cdef.e, baseloc)]  | cdef <- getScriptConstDefs(sys[l]) } | l <- sys);
	rel[ConstItem,loc,Expr] constrel = { < (classConst(cln,cn,ce) := ci) ? classConst(cln,cn) : normalConst(ci.constName), l, ci.e > | l <- loc2consts, ci <- loc2consts[l] };

	map[str, Expr] constMap = ( cn : ce | ci:normalConst(cn) <- constrel<0>, csub := constrel[ci,_], size(csub) == 1, ce:scalar(sv) := getOneFrom(csub), encapsed(_) !:= sv );  
	if ("DIRECTORY_SEPARATOR" notin constMap)
		constMap["DIRECTORY_SEPARATOR"] = scalar(string("/"));
	if ("PATH_SEPARATOR" notin constMap)
		constMap["PATH_SEPARATOR"] = scalar(string(":"));

	map[str, map[str, Expr]] classConstMap = ( );
	for (ci:classConst(cln,cn) <- constrel<0>, csub := constrel[ci,_], size(csub) == 1, ce:scalar(sv) := getOneFrom(csub), encapsed(_) !:= sv) {
		if (cln in classConstMap) {
			classConstMap[cln][cn] = ce;
		} else {
			classConstMap[cln] = ( cn : ce );
		}
	}

	return includesInfo(loc2consts, constrel, constMap, classConstMap);
}

private str cleanPath(str p) {
	parts = split("/", replaceAll(p, "\\", "/"));
	
	while([a*,b,"..",c*] := parts, b != "..") parts = [*a,*c];
	while([a*,".",c*] := parts) parts = [*a,*c];
		
	return intercalate("/", parts);
}

private set[loc] matchIncludes(System sys, Expr includeExpr, loc baseLoc, set[loc] libs = { }) {

	scalars = [ cleanPath(s) | /scalar(string(s)) := includeExpr ];
	
	if (scalars == []) {
		return {};
	}
	
	// Create the regular expression representing the include expression
	str re = "^.*" + intercalate(".*", scalars) + "$";

	// Filter the includes to just return those that match the regular expression
	set[loc] filteredIncludes = { l | l <- (sys<0> + libs), rexpMatch(l.path,re) }; 

	// Just return the result of applying the regexp match, we may want to do
	// some caching, etc here in the future	
	return filteredIncludes;	
}

private tuple[rel[loc,loc] resolved, set[loc] unresolved, set[str] unresolvedRelativePaths] resolveIncludes(System sys, IncludesInfo iinfo, loc toResolve, loc baseLoc, set[loc] libs = { }, bool checkFS=false) {
	rel[loc, loc] resolved = {};

	Script scr = sys[toResolve];
	includes = { < i@at, i > | /i:include(_,_) := scr };
	if (size(includes) == 0) return <{}, {}, {}>;
	
	// Step 1: simplify the include expression using a variety of techniques,
	// such as simulating function calls, replacing magic constants, and
	// performing string concatenations
	includes = { < l, normalizeExpr(replaceConstants(i,iinfo), baseLoc) > | < l, i > <- includes };
	
	rel[loc, str] relativePaths = {};
	
	// Step 2: if we have a scalar expression that is an absolute path, meaning
	// it starts with \ or /, then see if we can match it to a file, it should
	// be something in the set of files that make up the system; in this case we
	// should be able to match it to a unique file
	for (iitem:< _, i > <- includes, scalar(string(s)) := i.expr) {
		if (size(s) > 0, s[0] in { "\\", "/"}) {
			try {
				iloc = calculateLoc(sys<0>,toResolve,baseLoc,s,checkFS=checkFS,pathMayBeChanged=false);				
				resolved += {<i@at, iloc >};
			} catch UnavailableLoc(_) : {
				;
			}
		}
		else {
			relativePaths += {<i@at, s>};
		}
	}

	// Step 3: if we have a non-scalar expression, try matching to see if we can
	// match the include to one or more potential files; if this matches multiple
	// possible files, that's fine, this is a conservative estimation so we may
	// find files that will never actually be included in practice
	for (iitem:< _, i > <- domainX(includes, domain(resolved))) {
		possibleMatches = matchIncludes(sys, i, baseLoc, libs=libs);		
		resolved = resolved + { < i@at, l > | l <- possibleMatches };
	}

	unresolved = domain(includes) - domain(resolved);
 	unresolvedRelativePaths = relativePaths[unresolved]; 

	return <resolved, unresolved, unresolvedRelativePaths>;
}

@memo
private tuple[rel[loc,loc] resolved, set[loc] unresolved, set[str] unresolvedRelativePaths] resolveIncludes(rel[loc, System] systems) {
	rel[loc, loc] resolved = {};
	set[loc] unresolved = {};
	set[str] unresolvedRelativePaths = {};

	for (<baseLoc, sys> <- systems) {
		ii = buildIncludesInfo(sys, baseLoc);
		
		otherSystemFiles = { *domain(s) | <r, s> <- systems, r != baseLoc };
		
		for (f <- sys) {
			<r, u, ur> = resolveIncludes(sys, ii, f, baseLoc, libs=otherSystemFiles );
			resolved += r;
			unresolved += u;
			unresolvedRelativePaths += ur;
		}
	}
	
	return <resolved, unresolved, unresolvedRelativePaths>;
}

public map[int, int] includeResolutionHistogram(rel[loc, System] systems) {

	map[int, int] result = ();

	<r, u, ur> = resolveIncludes(systems);

	if (u != {}) {
		result[0] = size(u);
	}
	
	for (i <- domain(r)) {
		result[size(r[i])]?0 += 1;
	}
	
	return result;
}

public set[str] estimateMissingLibraries(rel[loc, System] systems) {
	set[str] result = {};

	<r, u, ur> = resolveIncludes(systems);
		
	for (s <- ur) {
		parts = split("/", cleanPath(s));
		
		while (["..", _, _*] := parts) parts = parts[1..]; 
		
		if (size(parts) > 1) {
			result += { intercalate("/", parts[..-1]) };
		}
	}
	
	return result;
}
