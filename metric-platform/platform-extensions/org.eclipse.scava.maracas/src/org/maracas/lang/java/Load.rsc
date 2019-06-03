module org::maracas::lang::java::Load

import lang::java::\syntax::Java15;
import ParseTree;

CompilationUnit parseJava15(loc file) = parseJava15(readFile(file));
CompilationUnit parseJava15(str content) = parse(#CompilationUnit, content);