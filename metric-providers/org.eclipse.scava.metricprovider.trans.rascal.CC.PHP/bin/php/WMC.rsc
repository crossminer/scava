@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module php::WMC

import lang::php::m3::AST;
import lang::php::m3::Core;
import lang::php::ast::AbstractSyntax;

import Prelude;
import List;
import util::Math;

import CC;
import org::eclipse::scava::metricprovider::Factoid;


@metric{WMCPHP}
@doc{Cyclomatic complexity is a measure of the number of unique control flow paths in the methods of a class and the functions in a file. This indicates how many different test cases
you would need to test the method. A high number indicates also a lot of work to understand the method. The weighted method count for a class is the sum
of the cyclomatic complexity measures of all methods in the class. This metric is a basic metric for further processing downstream. It is not easily compared between projects.}
@friendlyName{Weighted Method Count for PHP classes}
@appliesTo{php()}
@uses = ("CCPHP" : "methodCC")
map[loc class, num wmcCount] getWMC(rel[Language, loc, M3] m3s = {}, map[loc, int] methodCC = ())
{
	map[loc class, num wmcCount] result = ();
	
	for (<php(), _, m3> <- m3s) {
		result += (cl : sum([methodCC[m]?0 | m <- m3@containment[cl], isMethod(m)]) | <cl, _> <- m3@containment, isClass(cl));
	}
	 
	return result;
}

@metric{CCPHP}
@doc{Cyclomatic complexity is a measure of the number of unique control flow paths in the methods of a class. This indicates how many different test cases
you would need to test the method or function. A high number indicates also a lot of work to understand the method. This basic metric collects data per method and function and is used further downstream.}
@friendlyName{McCabe's Cyclomatic Complexity for PHP methods}
@appliesTo{php()}
map[loc, int] getCC(rel[Language, loc, AST] asts = {}) 
{
  map[loc method, int cc] result = ();
  
  for (<php(), _, phpAST(a)> <- asts) {
    top-down-break visit (a) {
      case m:method(_, _, _, _, body): result[m@decl] = countCC(body);
      case f:function(_, _, _, body): result[f@decl] = countCC(body);
    }
  }

  return result;
}

int countCC(list[Stmt] stats) {
  int count = 1;
  
  visit (stats) {
      case \do(_, body): count += 1;
      case \for(_, _, _, body): count += 1;
      case \foreach(_, _, _, _, body): count += 1;
      case \while(_, body): count += 1;
      
      case \if(_, _, elseIfs, _):
        count += 1 + size(elseIfs); 
      
      case \tryCatch(_, catches):
        count += size(catches);
        
      case \tryCatchFinally(_, catches, _):
        count += size(catches);
  }

  return count;
}

@metric{CCOverPHPMethods}
@doc{Calculates how cyclomatic complexity is spread over the methods of a system. If high CC is localized, then this may be easily fixed but if many methods have high complexity, then the project may be at risk. This metric is good to compare between projects.}
@friendlyName{Spread of CC over methods}
@appliesTo{php()}
@uses{("CCPHP": "methodCC")}
real giniCCOverMethodsPHP(map[loc, int] methodCC = ()) {
  return giniCCOverMethods(methodCC);
}


@metric{CCHistogramPHP}
@doc{Number of PHP methods per CC risk factor, counts the number of methods which are in a low, medium or high risk factor. The histogram can be compared between projects to indicate which is probably easier to maintain on a method-by-method basis.}
@friendlyName{Number of PHP methods per CC risk factor}
@appliesTo{php()}
@uses{("CCPHP" : "methodCC")}
@historic
map[str, int] CCHistogramPHP(map[loc, int] methodCC = ()) {
  return CCHistogram(methodCC);
}


@metric{CCPHPFactoid}
@doc{The cyclometic complexity of the project's PHP code indicates on the lowest code level (inside the body of methods) how hard the code to test is and also provides contra indications for underdstandability.}
@friendlyName{Cyclomatic Complexity for PHP}
@appliesTo{php()}
@uses{("CCHistogramPHP" : "hist")}
Factoid CC(map[str, int] hist = ()) {
  return CCFactoid(hist, "PHP");
}
