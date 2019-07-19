module org::maracas::\test::match::GumTreeMatcherTest

import lang::java::m3::AST;
import lang::java::m3::Core;
import List;
import org::maracas::match::matcher::GumTreeMatcher;

import Node;

Declaration ast1 = createAstFromFile(|project://maracas/src/org/maracas/test/data/AST1.java|, true);
Declaration ast2 = createAstFromFile(|project://maracas/src/org/maracas/test/data/AST2.java|, true);
Declaration ast3 = createAstFromFile(|project://maracas/src/org/maracas/test/data/AST3.java|, true);
Declaration ast4 = createAstFromFile(|project://maracas/src/org/maracas/test/data/AST4.java|, true);
Declaration ast5 = createAstFromFile(|project://maracas/src/org/maracas/test/data/AST5.java|, true);


test bool sizeTopDownMatchAST1() = size(topDownMatch(ast1, ast4, 2)) == 1;
test bool sizeTopDownMatchAST2() = size(topDownMatch(ast4, ast1, 2)) == 1;
test bool sizeTopDownMatchAST3() = size(topDownMatch(ast2, ast2, 2)) == 1;
test bool sizeTopDownMatchAST4() = size(topDownMatch(ast2, ast3, 2)) == 0;
test bool sizeTopDownMatchAST5() = size(topDownMatch(ast3, ast2, 2)) == 0;

test bool topDownMatchAST1() = topDownMatch(ast1, ast4, 2) == [<ast1, ast4>];
test bool topDownMatchAST2() = topDownMatch(ast4, ast1, 2) == [<ast4, ast1>];
test bool topDownMatchAST3() = topDownMatch(ast2, ast2, 2) == [<ast2, ast2>];
test bool topDownMatchAST4() = topDownMatch(ast2, ast3, 2) == [];
test bool topDownMatchAST5() = topDownMatch(ast3, ast2, 2) == [];

list[value] s() = topDownMatch(ast2, ast5, 2);
list[value] c() = getChildren(ast1);
