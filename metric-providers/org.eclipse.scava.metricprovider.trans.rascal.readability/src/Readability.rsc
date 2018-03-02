@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module Readability

import org::eclipse::scava::metricprovider::MetricProvider;

import String;
import Set;
import Map;
import List;
import util::Math;
import analysis::statistics::Descriptive;

import analysis::m3::Core;
import analysis::m3::AST;


@doc{
	Checks the appearance of whitespace in lines of code around given symbols.
	Returns the ratio of found spaces v.s. total spaces possible.
}
public real checkSpaces(list[str] lines, set[str] symbolsTwoSides, set[str] symbolsOneSide) {

	// TODO ignore comments
	
	map[str, int] whitespaceRequired = toMapUnique(symbolsTwoSides * {2} + symbolsOneSide * {1});
	
	spacesFound = 0;
	spacesMissed = 0;
	
	whitespace = {" ", "\n", "\r", "\f", "\t"};
	
	for (l <- lines) {
		for (s <- whitespaceRequired) {
			for (p <- findAll(l, s)) {
				spaces = 0;
				if (p == 0 || l[p-1] in whitespace) {
					spaces += 1;
				}
				p += size(s); // p is after s
				if (p == size(l) || l[p] in whitespace) {
					spaces += 1;
				}
				
				missed = max(0, whitespaceRequired[s] - spaces);
				
				spacesFound += whitespaceRequired[s] - missed;
				spacesMissed += missed;
			}
		}
	}
	
	maxSpacesPossible = spacesFound + spacesMissed;
	
	if (maxSpacesPossible > 0) {
		return round(spacesFound / toReal(maxSpacesPossible), 0.01);
	}
	
	return 1.0;
}


//real checkSpacesGeneric(list[str] lines, Language lang) {
//  real result;
//
//  switch(lang) {
//    case java(): result = checkSpaces(lines, {"{", "}"}, {";", ","});
//    case php(): result = checkSpaces(lines, {"{", "}"}, {";", ","});
//    default: result = -1.0;
//  }
//  
//  return result;
//}


@metric{fileReadability}
@doc{Code readability per file, measured by use of whitespace measures deviations from common usage of whitespace in source code, such 
as spaces after commas. This is a basic collection metric which is used further downstream.}
@friendlyName{File readability}
@appliesTo{generic()}
map[loc, real] fileReadability(rel[Language, loc, AST] asts = {}) {
  return (f : checkSpaces(l, {"{", "}"}, {";", ","}) 
         | <generic(), f, lines(l)> <- asts
         , f.extension == "java" || f.extension == "php");
}

@metric{fileReadabilityQuartiles}
@doc{We measure file readability by counting exceptions to common usage of whitespace in source code, such as spaces after commas. The quartiles
represent how many of the files have how many of these deviations. A few deviations per file is ok, but many files with many deviations indicates a
lack of attention to readability.}
@friendlyName{File readability quartiles}
@appliesTo{generic()}
@historic
@uses{("fileReadability": "val")}
map[str, real] fileReadabilityQ(map[loc, real] val = ()) {
  return quartiles(val);
}

@metric{ReadabilityFactoid}
@doc{We measure file readability by counting exceptions to common usage of whitespace in source code, such as spaces after commas. We find out  
how many of the files have how many of these deviations. A few deviations per file is ok, but many files with many deviations indicates a
lack of attention to readability.}
@friendlyName{Use of whitespace}
@appliesTo{generic()}
@uses=("fileReadability":"fileReadability")
Factoid readabilityFactoid(map[loc, real] fileReadability = ()) {
  if (isEmpty(fileReadability)) {
    throw undefined("No readability data available.", |tmp:///|);
  }

  re = { <l, fileReadability[l]> | l <- fileReadability };
  
  // percentages per risk group
  lowPerc  = [ r | <_,r> <- re, r >= 0.90];
  medPerc  = [ r | <_,r> <- re, r < 0.90 && r >= 0.75];
  highPerc  = [ r | <_,r> <- re, r < 0.75 && r >= 0.50];
  veryHighPerc = [ r | <_,r> <- re, r < 0.50];

  total = size(fileReadability);

  med = round(100.0 * ((size(lowPerc) == total) ? median(lowPerc) : median(medPerc + highPerc + veryHighPerc)), 0.01);
  
  star = \one();
  
  txt = "For readability of source code it is import that spaces around delimiters such as [,;{}] are used.\n";

  if (size(lowPerc) == total) {
     star = four();
     txt += "In this project all files have only minor readability issues.
            'An average file in this category has <med>% of the expected whitespace.";
  }
  else if (size(veryHighPerc) == 0 && size(highPerc) == 0 && size(medPerc) <= (total / 10)) {
     star = four();
     txt += "In this project less than 10% of the files have moderate readability issues.
            'An average file in this category has <med>% of the expected whitespace.";
  }
  else if (size(veryHighPerc) == 0 && size(highPerc) <= (total / 5) && size(medPerc) <= (total / 15)) {
     star = three();
     txt += "In this project less than 20% of the files have major readability issues.
            'An average file in this category has <med>% of the expected whitespace.";
  }
  else if (size(veryHighPerc) <= (total / 5) && size(highPerc) <= (total / 25)) {
     star = two();
     txt += "In this project less than 30% of the files have severe readability issues.
            'An average file in this category has <med>% of the expected whitespace.";
  }
  else {
     star = \one();
     txt += "In this project, <percent(size(veryHighPerc) + size(highPerc) + size(medPerc), total)>% of the files have serious readability issues.
            'An average file in this category has <med>% of the expected whitespace.";
  }
  
  return factoid(txt, star);
}

