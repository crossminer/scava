@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module PHPMetrics

extend lang::php::m3::Core;
import lang::php::m3::Uses;
import lang::php::m3::Declarations;
import lang::php::m3::Calls;

import util::Math;
import Map;
import Set;
import List;

import PHP;
import DynamicFeatures;
import Includes;

import org::eclipse::scava::metricprovider::MetricProvider;

@memo
private M3 systemM3WithStdLib(rel[Language, loc, M3] m3s, ProjectDelta delta = ProjectDelta::\empty()) {
	return addPredefinedDeclarations(systemM3(m3s, delta = delta));
}


@metric{StaticTypeNameResolutionHistogram}
@doc{This histogram shows to how many source code artefacts a name can point in PHP code. The more declarations a single name can point to the more "dynamic" the code is and the harder it is to predict what happens at run-time. A low precision for static name resolution indicates the project is hard to change and perhaps also hard to understand.}
@friendlyName{Static PHP type resolution histogram}
@appliesTo{php()}
map[int, int] getTypeNameResolutionHistogram(rel[Language, loc, M3] m3s = {}, ProjectDelta delta = ProjectDelta::\empty())
{
	M3 m3 = systemM3WithStdLib(m3s, delta = delta);

	m3@uses = { <l, n> | <l, n> <- m3@uses, n.scheme in ["php+class", "php+interface", "php+trait"] };

	useDecl = resolveUsesToPossibleDeclarations(m3);
	
	return calculateResolutionHistogram(countNumPossibleDeclarations(useDecl));
}

@metric{StaticMethodNameResolutionHistogram}
@doc{This histogram shows to how many method definitions a call can point in PHP code. The more declarations a single name can point to the more "dynamic" the code is and the harder it is to predict what happens at run-time. A low precision for static name resolution indicates the project is hard to change and perhaps also hard to understand.}
@friendlyName{Static PHP method name resolution histogram}
@appliesTo{php()}
map[int, int] getMethodNameResolutionHistogram(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {})
{
	M3 m3 = composeM3s(m3s, delta.date); // m3 before resolution

	calls = resolveMethodCallsAndFieldAccesses(m3)[1];

	return memberResolutionHistogram(calls, m3);
}


@metric{StaticFieldNameResolutionHistogram}
@doc{This histogram shows to how many field declarations the usage sites (references) can point in PHP code. The more declarations a single name can point to the more "dynamic" the code is and the harder it is to predict what happens at run-time. A low precision for static name resolution indicates the project is hard to change and perhaps also hard to understand.}
@friendlyName{Static PHP field name resolution histogram}
@appliesTo{php()}
map[int, int] getFieldNameResolutionHistogram(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {})
{
	M3 m3 = composeM3s(m3s, delta.date); // m3 before resolution

	accesses = resolveMethodCallsAndFieldAccesses(m3)[2];

	return memberResolutionHistogram(accesses, m3);
}


@memo
map[loc, map[DynamicFeature, int]] getDynamicFeatureCountsPerFunction(rel[Language, loc, AST] asts) {
	map[loc, map[DynamicFeature, int]] result = ();
	
	scripts = { s | <php(), _, phpAST(s)> <- asts };

	if (scripts == {}) {
		throw undefined("No PHP ASTs available.", |tmp:///|);
	}

	top-down-break visit (scripts) {
		case m:method(_, _, _, _, _): result[m@decl] = getDynamicFeatureCounts(m);
		case f:function(_, _, _, _): result[f@decl] = getDynamicFeatureCounts(f);
	}
	
	return result;
}


@metric{numDynamicFeatureUses-PHP}
@doc{The number of uses of dynamic PHP language features measures locations in the source code where code such as "eval" is used. This can be very handy to build
plugin architecture, but should not be used everywhere in the code because it makes it hard to predict what the code is doing, also leading to possible security leaks.}
@friendlyName{Number of uses of dynamic PHP language features}
@appliesTo{php()}
@historic
public int getNumberOfDynamicFeatureUses(rel[Language, loc, AST] asts = {})
{
	counts = getDynamicFeatureCountsPerFunction(asts);
	
	return ( 0 | it + sumCounts(counts[f]) | f <- counts); // includes eval() calls 
}


@metric{numEvals}
@doc{The number of uses of eval measures locations in the source code where this reflective function is called. This can be very handy to build
plugin architecture, but should not be used everywhere in the code because it makes it hard to predict what the code is doing, also leading to possible security leaks.}
@friendlyName{Number of PHP eval calls}
@appliesTo{php()}
@historic
public int getNumberOfEvalCalls(rel[Language, loc, AST] asts = {})
{
	counts = getDynamicFeatureCountsPerFunction(asts);
	
	return ( 0 | it + counts[f][eval()] | f <- counts); 
}


@metric{numFunctionsWithDynamicFeatures}
@doc{We measure the number of functions which are "infected" by the use of dynamic language features. It is to be expected that a limited number of functions use
the advanced features, but a widespread usage would be a quality contra-indicator.}
@friendlyName{Number of PHP functions using dynamic features}
@appliesTo{php()}
@historic
public int getNumberOfFunctionsWithDynamicFeatures(rel[Language, loc, AST] asts = {})
{
	counts = getDynamicFeatureCountsPerFunction(asts);
	
	return ( 0 | it + 1 | f <- counts, sumCounts(counts[f]) > 0); 
}


@metric{IncludesResolutionHistogram}
@doc{We use a simple static resolution algorithm to find out which files are included by which other files. If it is hard to decide this statically, then it 
is hard to know what code the system is actually running, also for the maintainer of the system. This is a quality contra-indicator because of security and understandability issues that arise.}
@friendlyName{Static PHP includes resolution histogram}
@appliesTo{php()}
public map[int, int] getIncludesResolutionHistogram(rel[Language, loc, M3] m3s = {}, rel[Language, loc, AST] asts = {})
{
	systems = getSystems(m3s, asts);

	return includeResolutionHistogram(systems);
}


@metric{MissingLibrariesPHP}
@doc{If we can not resolve an include we assume a library is not included in the source code of the project and no dependency was declared. The number of external dependencies is good to know for deciding adoption or use of an open-source project in combination with other observations.}
@friendlyName{Missing PHP libraries}
@appliesTo{php()}
public set[str] estimateMissingLibraries(rel[Language, loc, M3] m3s = {}, rel[Language, loc, AST] asts = {})
{
	systems = getSystems(m3s, asts);

	return estimateMissingLibraries(systems);
}


@metric{DynamicLanguageFeaturesPHP}
@doc{The number of uses of dynamic PHP language features measures locations in the source code where code such as "eval" is used. This can be very handy to build
plugin architecture, but should not be used everywhere in the code because it makes it hard to predict what the code is doing, also leading to possible security leaks.}
@friendlyName{PHP dynamic language feature use}
@appliesTo{php()}
@uses=("numFunctionsWithDynamicFeatures": "numFunctionsWithDynamicFeatures")
Factoid dynamicLanguageFeaturesFactoid(rel[Language, loc, AST] asts = {}, int numFunctionsWithDynamicFeatures = -1) {
  numFunctions = size(getDynamicFeatureCountsPerFunction(asts));

  if (numFunctionsWithDynamicFeatures == -1 || numFunctions == 0) {
    throw undefined("No data available.", |tmp:///|);
  }

  perc = percent(numFunctionsWithDynamicFeatures, numFunctions);
  
  stars = \one();
  txt = "The use of dynamic language features in PHP code negatively influences its static analysability.";
  
  if (perc <= 5) {
    stars = four();
    txt += "In this project, only <perc>% of the functions/methods use dynamic language features."; 
  }
  else if (perc <= 10) {
    stars = three();
    txt += "In this project, <perc>% of the functions/methods use dynamic language features."; 
  }
  else if (perc <= 15) {
    stars = two();
    txt += "In this project, <perc>% of the functions/methods use dynamic language features."; 
  }
  else {
    txt += "In this project, <perc>% of the functions/methods use dynamic language features."; 
  }

  return factoid(txt, stars);
}


@metric{StaticNameResolutionPHP}
@doc{How well could the names in the PHP code be statically resolved? The more declarations a single name can point to the more "dynamic" the code is and the harder it is to predict what happens at run-time. A low precision for static name resolution indicates the project is hard to change and perhaps also hard to understand because the code is ambiguous about what data will be referred to at run-time and what code will be executed at run-time.}
@friendlyName{PHP static name resolution}
@appliesTo{php()}
@uses=(
  "StaticTypeNameResolutionHistogram": "typeNames",
  "StaticMethodNameResolutionHistogram": "methodNames",
  "StaticFieldNameResolutionHistogram": "fieldNames",
  "IncludesResolutionHistogram": "includes",
  "MissingLibrariesPHP": "missingLibraries"
)
Factoid staticNameResolutionFactoid(
  map[int, int] typeNames = (),
  map[int, int] methodNames = (),
  map[int, int] fieldNames = (),
  map[int, int] includes = (),
  set[str] missingLibraries = {}
) {
  if (typeNames + methodNames + fieldNames + includes == ()) {
    throw undefined("No name resolution data available.", |tmp:///|);
  }

  totalNames = 0;
  totalUnresolved = 0;
  totalAmbiguous = 0;
  
  txt = "The percentages of unresolved and ambiguous names (lower is better) for the following categories are respectively:\n";
  
  for (<m, n> <- [<typeNames, "Type names">, <methodNames, "Method names">, <fieldNames, "Field names">, <includes, "Included files">]) {
    if (m == ()) {
      continue;
    }
  
    numNames = sum([m[i] | i <- m]);
    unresolved = m[0]?0;
    ambiguous = numNames - (m[0]?0) - (m[1]?0);
  
    totalNames += numNames;
    totalUnresolved += unresolved;
    totalAmbiguous += ambiguous;
    
    txt += "<n>: <percent(unresolved, numNames)>% <percent(ambiguous, numNames)>%\n";
  }

  if (missingLibraries != {}) {
    txt += "The following missing libraries were detected: <intercalate(", ", sort(toList(missingLibraries)))>. Adding these to the configuration might increase the name resolution scores.";
  }
  
  percProblematic = percent(totalUnresolved + totalAmbiguous, totalNames);
  
  stars = \one();
  
  if (totalUnresolved + totalAmbiguous == 0) {
    stars = four();
    txt = "All names in the code could be statically resolved to a unique declaration.\n";
  }
  else {
    txt = "<percProblematic>% of the names in the code could not be statically resolved to a unique declaration. This might influence the results of other metrics, for instance the OO related ones.\n" + txt;
  
    if (percProblematic < 10) {
      stars = four();
      txt = "Only <txt>"; 
    }
    else if (percProblematic < 20) {
      stars = three();
    }
    else if (percProblematic < 30) {
      stars = two();
    }
  }

  return factoid(txt, stars);
}
