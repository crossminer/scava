module org::maracas::\test::match::TreeTest

import lang::java::m3::AST;
import lang::java::m3::Core;
import org::maracas::match::matcher::Tree;
import org::maracas::\test::input::ColoredTreeTestInput;
import ValueIO;


// canonicalStr
test bool canonicalStr1() = canonicalStr(ct1)
	== "black$#blackred$#leafred$leafleaf$#leafred$#leafleaf$#";
	
test bool canonicalStr2() = canonicalStr(ct2)
	== "leaf$#";
	
test bool canonicalStr3() = canonicalStr(ct3)
	== "black$#leafleaf$#";
	
test bool canonicalStr4() = canonicalStr(ct4)
	== "red$#blackblack$#redleaf$redleaf$#leafleaf$leafleaf$#";
	

// descendants
test bool descendants1() = descendants(ct1)
	== [
		black(leaf(1), red(leaf(2), red(leaf(3), leaf(4)))),
		leaf(1),
		1,
		red(leaf(2), red(leaf(3), leaf(4))),
		leaf(2),
		2,
		red(leaf(3), leaf(4)),
		leaf(3),
		3,
		leaf(4),
		4,
		red(leaf(5), leaf(6)),
		leaf(5),
		5,
		leaf(6),
		6
	];
	
test bool descendants2() = descendants(ct2) == [1];

test bool descendants3() = descendants(ct3) 
	== [leaf(2), 2, leaf(1), 1];

test bool descendants4() = descendants(ct4) 
	== [
		black(red(leaf(1), leaf(2)), leaf(3)), 
		red(leaf(1), leaf(2)), 
		leaf(1),
		1, 
		leaf(2),
		2,
		leaf(3),
		3,
		black(red(leaf(4), leaf(5)), leaf(6)),
		red(leaf(4), leaf(5)), 
		leaf(4), 
		4,
		leaf(5),
		5,
		leaf(6),
		6
	];

test bool descendants5() = descendants(ct5) 
	== [leaf(2), 2, leaf(1), 1];
	
	
// height
test bool height1() = height(ct1) == 5;
test bool height2() = height(ct2) == 1;
test bool height3() = height(ct3) == 2;
test bool height4() = height(ct4) == 4;


// isomorphic
test bool isomorphic1() = isomorphic(ct3, ct5);
test bool isomorphic2() = isomorphic(ct5, ct3);
test bool isomorphic3() = isomorphic(ct3, ct3);
test bool isomorphic4() = !isomorphic(ct2, ct1);
test bool isomorphic5() = !isomorphic(ct1, ct4);



// AST Tests
Declaration ast1 = createAstFromFile(|project://maracas/src/org/maracas/test/data/AST1.java|, true);
Declaration ast2 = createAstFromFile(|project://maracas/src/org/maracas/test/data/AST2.java|, true);
Declaration ast3 = createAstFromFile(|project://maracas/src/org/maracas/test/data/AST3.java|, true);
Declaration ast4 = createAstFromFile(|project://maracas/src/org/maracas/test/data/AST4.java|, true);

private str canonStrBinFile(int ast) 
	= readBinaryValueFile(#str, |project://maracas/src/org/maracas/test/data/| + "ast<ast>CanonStr.bin");
	
test bool canonicalStrAST1() = canonicalStr(ast1) == canonStrBinFile(1);
test bool canonicalStrAST2() = canonicalStr(ast2) == canonStrBinFile(2);
test bool canonicalStrAST3() = canonicalStr(ast3) == canonStrBinFile(3);
test bool canonicalStrAST4() = canonicalStr(ast4) == canonStrBinFile(4);

private list[value] descendantsBinFile(int ast) 
	= readBinaryValueFile(#list[value], |project://maracas/src/org/maracas/test/data/| + "ast<ast>Descendants.bin");

test bool descendantsAST1() = descendants(ast1) == descendantsBinFile(1);
test bool descendantsAST2() = descendants(ast2) == descendantsBinFile(2);
test bool descendantsAST3() = descendants(ast3) == descendantsBinFile(3);
test bool descendantsAST4() = descendants(ast4) == descendantsBinFile(4);

test bool heightAST1() = height(ast1) == 9;
test bool heightAST2() = height(ast2) == 9;
test bool heightAST3() = height(ast3) == 10;
test bool heightAST4() = height(ast4) == 9;

test bool isomorphicAST1() = isomorphic(ast1, ast4);
test bool isomorphicAST2() = isomorphic(ast4, ast1);
test bool isomorphicAST3() = isomorphic(ast2, ast2);
test bool isomorphicAST4() = !isomorphic(ast2, ast3);
test bool isomorphicAST5() = !isomorphic(ast3, ast2);
