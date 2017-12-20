@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module WMC

import lang::java::m3::Core;
import lang::java::m3::AST;
import IO;
import Node;
import List;
import String;
import Map;
import Manager;

map[str class, num wmcCount] getWMC(M3 fileM3) {
  return (replaceAll(replaceFirst(cl.path, "/", ""), "/", ".") : sum([getCC(m, fileM3.ast) | m <- fileM3.model@containment[cl], isMethod(m)]) | <cl,_> <- fileM3.model@declarations, isClass(cl));
}

map[str class, num wmcCount] getWMC(unknownFileType(int lines)) = ("": -1);
map[str class, int cc] getCC(unknownFileType(int lines)) = ("" : -1);

map[str class, int cc] getCC(M3 fileM3) {
  return (replaceAll(replaceFirst(m.path, "/", ""), "/", ".") : getCC(m, fileM3.ast) | <cl,_> <- fileM3.model@declarations, isClass(cl), m <- fileM3.model@containment[cl], isMethod(m));
}

Declaration getASTOfMethod(loc methodLoc, Declaration fileAST) {
  visit(fileAST) {
    case Declaration d: {
      if ("decl" in getAnnotations(d) && d@decl == methodLoc) {
        return d;
      }
    }
  }
  throw "ast not found for method: <methodLoc>";
}

int getCC(loc m, Declaration ast) {
  int count = 1;
  Declaration methodAST = getASTOfMethod(m, ast);
  
  visit(methodAST) {
    case Statement s: {
      count += getPaths(s);
    }
  }
  return count;
}

int getPaths(\foreach(Declaration parameter, Expression collection, Statement body)) = 1;
int getPaths(\for(list[Expression] initializers, Expression condition, list[Expression] updaters, Statement body))  = 1 + getPaths(condition);
int getPaths(\for(list[Expression] initializers, list[Expression] updaters, Statement body)) = 1;
int getPaths(\if(Expression condition, Statement thenBranch, Statement elseBranch)) = 1 + getPaths(condition);
int getPaths(\if(Expression condition, Statement thenBranch)) = 1 + getPaths(condition);
int getPaths(\case(Expression expression)) = 1 + getPaths(expression);
int getPaths(\while(Expression condition, Statement body)) = 1 + getPaths(condition);
int getPaths(\do(Statement body, Expression condition)) = getPaths(condition);
int getPaths(\catch(Declaration exception, Statement body)) = 1;
default int getPaths(Statement s) = 0;

int getPaths(\infix(Expression lhs, "||", Expression rhs, list[Expression] extendedOperands)) = 1 + size(extendedOperands);
int getPaths(\infix(Expression lhs, "&&", Expression rhs, list[Expression] extendedOperands)) = 1 + size(extendedOperands);
default int getPaths(Expression e) = 0;
