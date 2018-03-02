@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module Advanced

import lang::java::m3::Core;
import lang::java::m3::AST;

import org::eclipse::scava::metricprovider::MetricProvider;

private int countAdvancedFeatures(Declaration d) {
	result = 0; 

	visit (d) {
		// wildcards and union types
		case wildcard(): result += 1;
		case upperbound(_): result += 1;
		case lowerbound(_): result += 1;
		case unionType(_): result += 1;
		
		// anonymous classes
		case newObject(_, _, _, Declaration d): result += 1;
		case newObject(_, _, Declaration d): result += 1;
	}

	return result;
}


@metric{AdvancedLanguageFeaturesJava}
@doc{Usage of advanced Java features (wildcards, union types and anonymous classes), reported per file and line number of the occurrence. This metric is for downstream processing by other metrics.}
@friendlyName{Usage of advanced Java features}
@appliesTo{java()}
public map[loc file, int count] countUsesOfAdvancedLanguageFeatures(rel[Language, loc, AST] asts = {}) {
	map[loc, int] result = ();

	for (<java(), f, declaration(d)> <- asts) {
		result[f] = countAdvancedFeatures(d);
	}

	return result;
}

@metric{AdvancedLanguageFeaturesJavaQuartiles}
@doc{Quartiles of counts of advanced Java features (wildcards, union types and anonymous classes). The numbers indicate the thresholds that delimit the first 25%, 50% and 75% of the data as well as the maximum and minumum values.}
@friendlyName{Usage of advanced Java features quartiles}
@appliesTo{java()}
@historic
@uses{("AdvancedLanguageFeaturesJava": "v")}
public map[str, real] countUsesOfAdvancedLanguageFeaturesQ(map[loc, int] v = ()) {
	return quartiles(v);
}

