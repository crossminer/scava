@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module Clones

import analysis::m3::Core;
import analysis::m3::AST;
import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;

import Prelude;
import util::Math;

import Generic;
import Split;

/*

In Type I clones, a copied code fragment is the same as the original. However, there might
be some variations in whitespace (blanks, new line(s), tabs etc.), comments and/or layouts.
Type I is widely know as Exact clones.

*/


@memo
list[str] normalizeCode(list[str] lines, Language lang) {
  switch(lang) {
    case java(): return genericNormalizeCode(lines, {<"/*", "*/">}, {"//"}, {"{", "}"}, {"package", "import"});
    case php(): return genericNormalizeCode(lines, {<"/*", "*/">}, {"//", "#"}, {"{", "}", "\<?php", "\<?", "?\>", "\<?=", "\<script language=\"php\"\>", "\</script\>", "\<%", "\<%=", "%\>"}, {"namespace", "use", "include", "require"});
    default: return [];
  }
}


list[str] genericNormalizeCode(list[str] lines, rel[str, str] blockCommentDelimiters, set[str] lineCommentDelimiters, set[str] toRemove, set[str] ignorePrefixes) {

  allDelimiters = lineCommentDelimiters + domain(blockCommentDelimiters);

  list[str] result = [];

  inBlock = false;
  currentDelimiter = "";
  
  toStrip = toRemove + {" ", "\n", "\r", "\f", "\t"};
  
  strip = str (str s) {
    for (rm <- toStrip) {
    	s = replaceAll(s, rm, "");
    }
    return s;
  };  

  for (l <- lines) {
    l = toLowerCase(trim(l));
  	stripped = "";
  
    if (inBlock) {
      if (<true, pre, rest> := firstSplit(l, currentDelimiter)) {      
        l = rest;
        inBlock = false;
      }
    }

    if (!inBlock) {
      if (size(ignorePrefixes) > 0 && any(p <- ignorePrefixes, startsWith(l, p))) {
        l = ""; // ignore
      }

      while (size(l) > 0) {
        if (<true, pre, post, d> := firstSplit(l, allDelimiters)) {
          stripped += strip(pre);
          if (d in lineCommentDelimiters) { // line comment
            l = "";
          } else { // start of block comment
            currentDelimiter = getOneFrom(blockCommentDelimiters[d]);
            if (<true, c, rest> := firstSplit(post, currentDelimiter)) { // block closed on same line
              l = rest;
            } else { // block open at end of line
              inBlock = true;
              l = "";
            }
          }
        } else {
          stripped += strip(l);
          l = "";
        }
      }
    }

    if (stripped != "") {
    	result += stripped;
    }
  }

  return result;
}

private alias Block = list[str];


@metric{cloneLOCPerLanguage}
@doc{Lines of code in Type I clones larger than 6 lines, per language. A Type I clone is a literal clone. A large number of literal clones is considered to be bad. This metric is not easily compared between systems because it is not size normalized yet. We use it for further processing downstream. You can analyze the trend over time using this metric.}
@friendlyName{Lines of code in Type I clones larger than 6 lines, per language}
@appliesTo{generic()}
@historic
map[str, int] cloneLOCPerLanguage(rel[Language, loc, AST] asts = {}) {

  map[str, int] result = ();
  int BLOCKSIZE = 7; // larger than 6
  
  langs = asts<0> - { generic() };
  
  for (lang <- langs) {
    set[Block] uniqueBlocks = {};
  
    for (<f, _> <- asts[lang]) {    
      if ({lines(content)} := asts[generic(), f]) {
        norm = normalizeCode(content, lang);
        
        if (size(norm) >= BLOCKSIZE) {        
          Block block = [];
          set[int] linesInClones = {};
        
          for (i <- [0..size(norm)]) {
            block += [norm[i]];
          
            if (size(block) == BLOCKSIZE) {
              if (block in uniqueBlocks) {
                linesInClones += { i - j | j <- [0..BLOCKSIZE] };
              } else {
                uniqueBlocks += {block};
              }          
            
              block = block[1..]; // remove first element
            }
          }

          result["<getLanguageName(lang)>"]?0 += size(linesInClones);
        }
      }
    }
  }

  return result;
}   


@metric{cloneCode}
@doc{The amount of code in the project in Type I clones larger than 6 lines. A Type I clone is a literal clone. If a considerable part of a large project 
consists of cloned code there may be maintainability risks.}
@friendlyName{Code Cloning}
@appliesTo{generic()}
@uses{("org.eclipse.scava.metricprovider.trans.rascal.LOC.locPerLanguage": "locPerLanguage", "cloneLOCPerLanguage": "cloneLOCPerLanguage")}
Factoid cloneCode(map[str, int] locPerLanguage = (), map[str, int] cloneLOCPerLanguage = ()) {
  measuredLanguages = domain(locPerLanguage) & domain(cloneLOCPerLanguage);

  if (isEmpty(measuredLanguages)) {
    throw undefined("No LOC data available", |unknown:///|);
  }

  rel[str, real] clonePercentagePerLanguage = { <l, round((100.0 * cloneLOCPerLanguage[l]) / locPerLanguage[l], 0.01)> | l <- measuredLanguages };  

  // generic() is already removed in locPerLanguage
  lrel[str, real] sorted = sort(clonePercentagePerLanguage,
    bool (tuple[str, real] a, tuple[str, real] b) {
    	return a[1] > b[1]; // sort from high to low
    });

  totalLOC = sum([ locPerLanguage[l] | l <- measuredLanguages ]);
  totalCloneLOC = sum([ cloneLOCPerLanguage[l] | l <- measuredLanguages ]); 

  totalClonePercentage = round((100.0 * totalCloneLOC) / totalLOC, 0.01);

  // Kapser, C.; Godfrey, M.W., ""Cloning Considered Harmful" Considered Harmful," 13th Working Conference on Reverse Engineering (WCRE), pp. 19-28, Oct. 2006:
  // clone code is not necessarily bad

  // Chen, X., Wang, A. Y., & Tempero, E. D. (2014). A Replication and Reproduction of Code Clone Detection Studies. In ACSC (pp. 105-114).
  // 43 java projects, 50% had more than 14% code in clones.

  stars = \one();

  if (totalClonePercentage < 10.0) {
  	stars = four();
  } else if (totalClonePercentage < 15.0) {
    stars = three();
  } else if (totalClonePercentage < 20.0) {
    stars = two();
  }

  txt = "The measured percentage of source code in Type I (verbatim) clones larger than 6 lines is <totalClonePercentage>% (less is better).";
  if (size(sorted) > 1) {
    otherTxt = intercalate(", ", ["<l[0]>: <l[1]>%" | l <- sorted]);
  
    txt += " The percentages of clone code per language are <otherTxt>.";
  } 

  return factoid(txt, stars);	
}
