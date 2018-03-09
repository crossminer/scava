@license{
Copyright (c) 2014 SCAVA Partners.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
}
module PHP

import lang::php::m3::Core;
import lang::php::m3::AST;
import lang::php::m3::FillM3;
import lang::php::m3::Calls;
import lang::php::ast::AbstractSyntax;
import lang::php::ast::System;
import lang::php::util::Utils;
import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;

import IO;
import Message;
import Relation;
import Map;
import DateTime;

@M3Extractor{php()}
@memo
public rel[Language, loc, M3] extractM3sPHP(loc project, ProjectDelta delta, map[loc repos, loc folders] checkouts, map[loc, loc] scratch)
{
	m3s = { <php(), file, createM3forScript(file, script)> | <php(), file, phpAST(script)> <- extractASTsPHP(project, delta, checkouts, scratch) };
	
	m3s += { <php(), root, getSystemStructureM3(root)> | root <- checkouts<folders> };
	
	return m3s;
}

@ASTExtractor{php()}
@memo
public rel[Language, loc, AST] extractASTsPHP(loc project, ProjectDelta delta, map[loc repos, loc folders] checkouts, map[loc, loc] scratch)
{
	rel[Language, loc, AST] result = {};
	
	for (root <- checkouts<folders>)
	{
		System sys = getSystem(root);
		result += { <php(), file, (errscript(m) := sys[file]) ? noAST(error(m, file)) : phpAST(sys[file])> | file <- sys };
	}
	
	return result;
}


@memo
private System getSystem(loc root) {
	return loadPHPFiles(root);
}


private M3 getSystemStructureM3(loc root) {
	M3 m3 = createEmptyM3(|php+system:///| + root.path);
	
	for (file <- getSystem(root)) {
		m3@containment[root] += file;
	}
	
	return m3;
}


// recover Systems from m3s and asts
@memo
public rel[loc, System] getSystems(rel[Language, loc, M3] m3s, rel[Language, loc, AST] asts) {
	map[loc, System] result = ();
	
	map[loc, loc] roots = ();
	
	for (<php(), root, m3> <- m3s, m3.id.scheme == "php+system") {
		for (file <- m3@containment[root]) {
			roots[file] = root;
		}
		
		result[root] = ();
	}
	
	for (<php(), file, phpAST(script)> <- asts) {
		if (file in roots) {
			result[roots[file]][file] = script;
		}
	}
	
	return toRel(result);
}


@memo
public M3 composeM3s(rel[Language, loc, M3] m3s, datetime d) {
	phpM3s = range(m3s[php()]);
	projectLoc = |php+project:///| + printDate(d, "YYYYMMdd");
	if (phpM3s == {}) {
		throw undefined("No PHP M3 models available", projectLoc);
	}
	else {
		return composeM3(projectLoc, phpM3s);
	}
}

@memo
public tuple[M3 m3, rel[loc, loc] callResolution, rel[loc, loc] fieldAccessResolution] resolveMethodCallsAndFieldAccesses(M3 m3) {
	calls = resolveUnknownMethodCalls(m3);	
	accesses = resolveUnknownFieldAccesses(m3);
	
	m3 = replaceUnknownMethodCalls(m3, calls);
	m3 = replaceUnknownFieldAccesses(m3, accesses);	
	
	return <m3, calls, accesses>;
}

@memo
public M3 systemM3(rel[Language, loc, M3] m3s, ProjectDelta delta = ProjectDelta::\empty()) {
	M3 m3 = composeM3s(m3s, delta.date);
	m3 = resolveMethodCallsAndFieldAccesses(m3)[0];
	return m3;
}
