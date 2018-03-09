@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module Comments

import analysis::m3::Core;
import analysis::m3::AST;
import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;

import Prelude;
import util::Math;
import analysis::graphs::Graph;
import Split;
import Set;
import Map;
import Generic;

private map[str name, str header] licenses =
  ("GPLv2"
  : "Copyright (C) YEAR AUTHOR
    'This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
    '
    'This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
    '
    'You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
    ",
   "LGPL" 
    : "Copyright (C) YEAR OWNER 
      'This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
      '
      'This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
      '
      'You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
      ",
    "BSD-2" 
    : "Copyright (c) YEAR, OWNER
      'All rights reserved.
      '
      'Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
      '
      '1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
      '
      '2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
      '
      'THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
      ",
    "BSD-3" 
    : "Copyright (c) YEAR, OWNER
      'All rights reserved.
      '
      'Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
      '
      '1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
      '
      '2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
      '
      '3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
      '
      'THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
      ",
    "GPL-3" 
    : "one line to give the program\'s name and a brief idea of what it does.
      'Copyright (C) YEAR AUTHOR
      '
      'This program is free software: you can redistribute it and/or modify
      'it under the terms of the GNU General Public License as published by
      'the Free Software Foundation, either version 3 of the License, or
      '(at your option) any later version.
      '
      'This program is distributed in the hope that it will be useful,
      'but WITHOUT ANY WARRANTY; without even the implied warranty of
      'MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
      'GNU General Public License for more details.
      '
      'You should have received a copy of the GNU General Public License
     ' along with this program.  If not, see \<http://www.gnu.org/licenses/\>.",
    "Apache" 
    : "Copyright YEAR OWNER
      '
      'Licensed under the Apache License, Version 2.0 (the \"License\");
      'you may not use this file except in compliance with the License.
      'You may obtain a copy of the License at
      '
      'http://www.apache.org/licenses/LICENSE-2.0
      '
      'Unless required by applicable law or agreed to in writing, software
      'distributed under the License is distributed on an \"AS IS\" BASIS,
      'WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      'See the License for the specific language governing permissions and
      'limitations under the License.
      ",
    "MIT" 
    : "The MIT License (MIT)
      '
      'Copyright (c) YEAR COPYRIGHTHOLDERS
      '
      'Permission is hereby granted, free of charge, to any person obtaining a copy
      'of this software and associated documentation files (the \"Software\"), to deal
      'in the Software without restriction, including without limitation the rights
      'to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
      'copies of the Software, and to permit persons to whom the Software is
      'furnished to do so, subject to the following conditions:
      '
      'The above copyright notice and this permission notice shall be included in
      'all copies or substantial portions of the Software.
      '
      'THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
      'IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
      'FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
      'AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
      'LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
      'OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
      'THE SOFTWARE.
      ",
    "EPL" 
    : "Copyright (c) YEAR OWNER
      'All rights reserved. This program and the accompanying materials
      'are made available under the terms of the Eclipse Public License 
      'which accompanies this distribution, and is available at
      'http://www.eclipse.org/legal/epl-v10.html
      ",
    "Mozilla" : "This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/."
  );
  
  
data CommentStats
  = stats(int linesWithComment, int headerStart, int headerSize, int commentedOutCodeLines)
  | unknown()
  ;


@memo
CommentStats commentStats(list[str] lines, Language lang) {
  CommentStats result;

  switch(lang) {
    case java(): result = genericCommentStats(lines, {<"/*", "*/">}, {"//"}, {";", "{", "}"}, {"package", "import"});
    case php(): result = genericCommentStats(lines, {<"/*", "*/">}, {"//", "#"}, {";", "{", "}", "$"}, {"\<?php", "\<?"});
    default: result = unknown();
  }
  
  return result;
}

CommentStats genericCommentStats(list[str] lines, rel[str, str] blockDelimiters, set[str] lineDelimiters, set[str] codePatterns, set[str] ignorePrefixesForHeader) {

  allDelimiters = lineDelimiters + domain(blockDelimiters);

  int cloc = 0, hskip = 0, hloc = 0, coloc = 0;
  
  containsCode = bool(str s) { // s is not a comment
    return /.*[a-zA-Z].*/ := s;
  };
  
  commentContainsCode = bool(str comment) {
    return size(codePatterns) > 0 && any(p <- codePatterns, findFirst(comment, p) > -1);
  };
    
  inBlock = false;
  currentDelimiter = "";  

  bool inHeader = true;
  for (l <- lines) {
    originalLine = l;
    hasCode = false;
    hasCodeInComment = false;
    hasComment = false;
    
    if (inBlock) {
      hasComment = true;
    
      if (<true, pre, rest> := firstSplit(l, currentDelimiter)) {      
        hasCodeInComment = commentContainsCode(pre);
        inBlock = false;
        l = rest;
      } else {
        hasCodeInComment = commentContainsCode(l);
      }
    }

    if (!inBlock) {
      while (l != "") {
        if (<true, pre, post, d> := firstSplit(l, allDelimiters)) {
          hasComment = true;
          hasCode = hasCode || containsCode(pre);
          if (d in lineDelimiters) { // line comment
            hasCodeInComment = hasCodeInComment || commentContainsCode(post);
            l = "";
          } else { // start of block comment
            currentDelimiter = getOneFrom(blockDelimiters[d]);
            if (<true, c, rest> := firstSplit(post, currentDelimiter)) { // block closed on same line
              l = rest;
              hasCodeInComment = hasCodeInComment || commentContainsCode(c);
            } else { // block open at end of line
              hasCodeInComment = hasCodeInComment || commentContainsCode(post);
              inBlock = true;
              l = "";
            }
          }
        } else {
          l = "";
        }
      }
    }

    if (hasComment) {
      cloc += 1;
    }

    if (hasCodeInComment) {
      coloc += 1;
    }
    
    if (inHeader) {
      trimmed = trim(originalLine);
      if ((hloc == 0) && (size(trimmed) == 0 || 
          (size(ignorePrefixesForHeader) > 0 && any(p <- ignorePrefixesForHeader, startsWith(trimmed, p))))) {
        // skip trailing empty lines or lines with prefixes to ignore
        hskip += 1;
      } else if (hasComment && !hasCode) {
        hloc += 1;
      } else {
        inHeader = false;
      }
    }
  }

  return stats(cloc, hskip, hloc, coloc);
}

@metric{commentLOC}
@doc{Number of lines containing comments per file is a basic metric used for downstream processing. This metric does not consider the difference
between natural language comments and commented out code.}
@friendlyName{Number of lines containing comments per file}
@appliesTo{generic()}
map[loc, int] commentLOC(rel[Language, loc, AST] asts = {}) {
  map[loc, int] result = ();

  for (<lang, f, _> <- asts, lang != generic()) {
    if ({lines(l)} := asts[generic(), f]) {
      s = commentStats(l, lang);
      if (s != unknown()) {
        result[f] = s.linesWithComment;
      }
    }
  }

  return result;
}

@metric{headerLOC}
@doc{Header size per file is a basic metric counting the size of the comment at the start of each file. It is used for further processing downstream.}
@friendlyName{Header size per file}
@appliesTo{generic()}
map[loc, int] headerLOC(rel[Language, loc, AST] asts = {}) {
  map[loc, int] result = ();

  for (<lang, f, _> <- asts, lang != generic()) {
    if ({lines(l)} := asts[generic(), f]) {
      s = commentStats(l, lang);
      if (s != unknown()) {
        result[f] = s.headerSize;
      }
    }
  }

  return result;
}

@metric{commentedOutCode}
@doc{Lines of commented out code per file uses heuristics (frequency of certain substrings typically used in code and not in natural language) to find out how
much source code comments are actually commented out code. Commented out code is, in large quantities is a quality contra-indicator.}
@friendlyName{Lines of commented out code per file}
@appliesTo{generic()}
map[loc, int] commentedOutCode(rel[Language, loc, AST] asts = {}) {
  map[loc, int] result = ();

  for (<lang, f, _> <- asts, lang != generic()) {
    if ({lines(l)} := asts[generic(), f]) {
      s = commentStats(l, lang);
      if (s != unknown()) {
        result[f] = s.commentedOutCodeLines;
      }
    }
  }

  return result;
}


@metric{commentedOutCodePerLanguage}
@doc{Lines of commented out code per file uses heuristics (frequency of certain substrings typically used in code and not in natural language) to find out how
much source code comments are actually commented out code. Commented out code is, in large quantities is a quality contra-indicator.}
@friendlyName{Lines of commented out code per language}
@appliesTo{generic()}
@uses{("commentedOutCode": "commentedOutCode")}
@historic
map[str, int] commentedOutCodePerLanguage(rel[Language, loc, AST] asts = {}, map[loc, int] commentedOutCode = ()) {
  map[str, int] result = ();
  for (<l, f, a> <- asts, l != generic(), f in commentedOutCode) {
    result["<getLanguageName(l)>"]?0 += commentedOutCode[f];
  }
  return result;
}


@metric{percentageCommentedOutCode}
@doc{Commented-out code, in large quantities, is a contra-indicator for quality being a sign of experimental code or avoiding the use of a version control system.}
@friendlyName{Percentage of commented out code}
@appliesTo{generic()}
@uses{("org.eclipse.scava.metricprovider.trans.rascal.LOC.locPerLanguage": "locPerLanguage",
       "commentedOutCodePerLanguage": "commentedOutCodePerLanguage")}
Factoid percentageCommentedOutCode(map[str, int] locPerLanguage = (), map[str, int] commentedOutCodePerLanguage = ()) {
  // only report figures for measured languages
  languages = domain(locPerLanguage) & domain(commentedOutCodePerLanguage);

  if (isEmpty(languages)) {
    throw undefined("No LOC data available", |unknown:///|);
  }

  totalLines = toReal(sum([ locPerLanguage[l] | l <- languages ]));
  totalCommentedLines = toReal(sum([ commentedOutCodePerLanguage[l] | l <- languages ]));

  totalPercentage = round((totalCommentedLines / totalLines) * 100.0, 0.01);
  
  stars = four();
  
  if (totalPercentage >= 5) {
    stars = \one();
  } else if (totalPercentage >= 2) {
    stars = two();
  } else if (totalPercentage >= 1) {
    stars = three();
  }
  
  txt = "The percentage of commented out code over all code files is <totalPercentage>%.";

  languagePercentage = ( l : round(100 * commentedOutCodePerLanguage[l] / toReal(locPerLanguage[l]), 0.01) | l <- languages ); 

  otherTxt = intercalate(", ", ["<l[0]> (<languagePercentage[l]>%)" | l <- languages]);  
  txt += " The percentages per language are <otherTxt>.";

  return factoid(txt, stars);	
}


@metric{commentLinesPerLanguage}
@doc{Number of lines containing comments per language (excluding headers). The balance between comments and code indicates understandability. Too many comments are often not maintained and may lead to confusion, not enough means the code lacks documentation explaining its intent. This is a basic fact collection metric which is used further downstream.}
@friendlyName{Number of lines containing comments per language (excluding headers)}
@appliesTo{generic()}
@uses{("commentLOC": "commentLOC",
       "headerLOC": "headerLOC",
       "commentedOutCode": "commentedOutCode")}
@historic
map[str, int] commentLinesPerLanguage(rel[Language, loc, AST] asts = {},
                                      map[loc, int] commentLOC = (),
                                      map[loc, int] headerLOC = (),
                                      map[loc, int] commentedOutCode = ()) {
  map[str, int] result = ();
  for (<l, f, a> <- asts, l != generic(), f in commentLOC) {
    result["<getLanguageName(l)>"]?0 += commentLOC[f] - (headerLOC[f]?0) - (commentedOutCode[f]?0);
  }
  return result;
}


@metric{commentPercentage}
@doc{The balance between comments and code indicates understandability. Too many comments are often not maintained and may lead to confusion, not enough means the code lacks documentation explaining its intent.}
@friendlyName{Percentage of lines with comments (excluding headers)}
@appliesTo{generic()}
@uses{("org.eclipse.scava.metricprovider.trans.rascal.LOC.locPerLanguage": "locPerLanguage",
       "commentLinesPerLanguage": "commentLinesPerLanguage")}
Factoid commentPercentage(map[str, int] locPerLanguage = (), map[str, int] commentLinesPerLanguage = ()) {
  // only report figures for measured languages
  languages = domain(locPerLanguage) & domain(commentLinesPerLanguage);

  if (isEmpty(languages)) {
    throw undefined("No LOC data available", |unknown:///|);
  }

  totalLines = toReal(sum([ locPerLanguage[l] | l <- languages ]));
  totalCommentedLines = toReal(sum([ commentLinesPerLanguage[l] | l <- languages ]));

  totalPercentage = (totalCommentedLines / totalLines) * 100.0;
  
  stars = \one();
  
  // according to Software Assessments, Benchmarks, and Best Practices. Capers Jones 2000,
  // the ideal comment density is 1 comment per 10 statements. (less AND more is worse).
  // (which is around 9% if all lines would be either statements or comments)
  
  // according to The comment density of open source software code. Arafat and Riehle, 2009,
  // the average comment density of >5000 OSS projects is 18.67%
    
  if (totalPercentage < 25) {
  	if (totalPercentage > 8) {
  		stars = four();
  	}
  	else if (totalPercentage > 6) {
  		stars = three();
  	}
  	else if (totalPercentage > 4) {
  		stars = two();
  	}
  }
  else {
  	stars = three();
  }
  
  txt = "The percentage of lines containing comments over all measured languages is <round(totalPercentage,0.01)>%. Headers and commented out code are not included in this measure.";

  languagePercentage = ( l : round(100 * commentLinesPerLanguage[l] / toReal(locPerLanguage[l]), 0.01) | l <- languages ); 

  otherTxt = intercalate(", ", ["<l> (<languagePercentage[l]>%)" | l <- languages]);  
  txt += " The percentages per language are <otherTxt>.";

  return factoid(txt, stars);	
}


@metric{headerPercentage}
@doc{Percentage of files with headers is an indicator for the amount of files which have been tagged with a copyright statement (or not). If the number is low this indicates a problem with the copyright of the program. Source files without a copyright statement are not open-source, they are owned, in principle, by the author and may not be copied without permission. Note that the existence of a header does not guarantee the presence of an open-source license, but its absence certainly is telling.}
@friendlyName{Percentage of files with headers.}
@appliesTo{generic()}
@uses{("headerLOC": "headerLOC")}
@historic
real headerPercentage(map[loc, int] headerLOC = ()) {
	int measuredFiles = size(headerLOC);
	if (measuredFiles == 0) {
		throw undefined("No headers found", |unknown:///|); 
	}	
	return round((100.0 * ( 0 | it + 1 | f <- headerLOC, headerLOC[f] > 0 ) ) / measuredFiles, 0.01);
}

private alias Header = set[str];

private Header extractHeader(list[str] lines, int headerSize, int headerStart) {
	return { trim(l) | l <- lines[headerStart..(headerStart + headerSize)], /.*[a-zA-Z].*/ := l, !contains(l, "@")};
}

private Header extractHeaderWords(list[str] lines, int headerSize, int headerStart) {
	return { word | l <- lines[headerStart..(headerStart + headerSize)], /.*[a-zA-Z].*/ := l, !contains(l, "@"), /<word:\w+>/ := l};
}


@memo
private map[loc, Header] extractHeaders(rel[Language, loc, AST] asts) {
	map[loc, Header] headers = ();
	
	for (<lang, f, _> <- asts, lang != generic()) {
		if ({lines(l)} := asts[generic(), f])
		{
			s = commentStats(l, lang);
			if (s != unknown() && s.headerSize > 0) {
				headers[f] = extractHeader(l, s.headerSize, s.headerStart);
			}
		}
	}
	
	return headers;
}


@metric{headerCounts}
@doc{In principle it is expected for the files in a project to share the same license. The license text in the header of each file may differ slightly due to different copyright years and or lists of contributors. The heuristic allows for slight differences. The metric produces the number of different types of header files found. A high number is a contra-indicator, meaning either a confusing licensing scheme or the source code of many different projects is included in the code base of the analyzed system.}
@friendlyName{Number of appearances of estimated unique headers}
@appliesTo{generic()}
list[int] headerCounts(rel[Language, loc, AST] asts = {}) {

	headersPerFile = extractHeaders(asts);
	
	headers = range(headersPerFile);
	
	Graph[Header] bestMatches = {};
	
	int unmatched = 0;
	
	for (h1 <- headers) {
		int score = 0;
		Header bestMatch = {};
		
		for (h2 <- headers) {
			int s = size(h1 & h2);
			
			if (s > score) {
				score = s;
				bestMatch = h2;
			}
		}
		
		if (score > 0) {
			bestMatches += {<h1, bestMatch>};
		} else {
			unmatched += 1;
		}
	}
		
	components = connectedComponents(bestMatches);
	
	map[set[Header], int] headerCount = ();
	
	for (f <- headersPerFile) {
		h = headersPerFile[f];
		for (c <- components) {
			if (h in c) {
				headerCount[c]?0 += 1;
			}
		}
	}
	
	list[int] headerHistogram = [ headerCount[c] | c <- headerCount ] + [ 1 | i <- [0..unmatched] ]; 
	
	return headerHistogram;
}

@metric{matchingLicenses}
@doc{We match against a list of known licenses to find out which are used in the current project}
@friendlyName{Used licenses (from selected list of known licenses)}
@appliesTo{generic()}
set[str] matchingLicenses(rel[Language, loc, AST] asts = {}) {
    licenseHeaders = (name : extractHeaderWords(inputList, size(inputList), 0) | name <- licenses, l := licenses[name], inputList := split("\n",l));
	map[loc, Header] headersPerFile = extractHeaders(asts);
	
	// split lines into words
	headersPerFile = ( f : {word | l <- headersPerFile[f], /<word:\w+>/ := l} | f <- headersPerFile);
	map[loc file, str license] matches = ();
	
	for (f <- headersPerFile) {
	  bestScore = 0;
	  bestLicense = "";
	   
	  for (l <- licenseHeaders) {
	    score = size(licenseHeaders[l] & headersPerFile[f]) * 1.0 / size(licenseHeaders[l]);
	    // at least 80% of the header should match
	    if (score > 0.8, bestScore < score) {
	      bestScore = score;
	      bestLicense = l;
	    }
	  }
	  
	  matches[f] = bestLicense;
	}
	
	return (matches<license> - {""});
}


@metric{headerUse}
@doc{In principle it is expected for the files in a project to share the same license. The license text in the header of each file may differ slightly due to different copyright years and or lists of contributors. We find out how many different types of header files are used and if the distribution is flat or focused on a single distribution. The difference is between  a clear and simple license for the entire project or a confusing licensing scheme with possible juridical consequences.}
@friendlyName{Consistency of header use}
@appliesTo{generic()}
@uses{("headerCounts": "headerCounts", "headerPercentage": "headerPercentage", "matchingLicenses":"matchingLicenses")}
Factoid headerUse(list[int] headerCounts = [], real headerPercentage = -1.0, set[str] matchingLicenses = {}) {

	if (headerCounts == [] || headerPercentage == -1.0) {
		throw undefined("Not enough header data available", |tmp:///|);
	}

	stars = 1;
	message = "";

	if (headerPercentage > 50.0) {
		stars += 1;
		
		if (headerPercentage > 95.0) {
			stars += 1;
		}
		
		highestSimilarity = (max(headerCounts) * 1.00) / sum(headerCounts);
		
		if (highestSimilarity > 0.8) {
			stars += 1;
		}
		
		message = "The percentage of files with a header is <round(headerPercentage,0.01)>%." +
			"The most common header appears in <round(highestSimilarity*100.0,0.01)>% of the files.";
	}
	else if (headerPercentage > 0.0) {
		message = "Only <round(headerPercentage,0.01)>% of the files contain a header.";
	}
	else {
		message = "No headers found.";
	}
	
	message = "<message>
	          '<if (size(matchingLicenses) == 1, l <- matchingLicenses) {>Only found files with the <l> license header were found.<}>
	          '<if (size(matchingLicenses) > 1) {>Files with the following license headers types were found:
	          '<for (l <- matchingLicenses) {>   * <l>
	          '<}>
	          '<}> (These matching licenses were tested against a known collection of <size(licenses)> header texts.)
	          ";  
	
	return factoid(message, starLookup[stars]);	
}
