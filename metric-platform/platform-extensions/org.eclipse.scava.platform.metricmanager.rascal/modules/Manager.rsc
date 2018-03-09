@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module Manager

import util::ShellExec;
import List;
import Set;
import IO;
import lang::java::m3::Core;
import lang::java::m3::AST;
import util::Math;
import String;

data M3 = unknownFileType(int lines)
        | m3WithAdditionalInfo(M3 model, Declaration ast, int total, int comments, int empty, int source)
        ;

str checkOutRepository(str repositoryURL, int revision, loc localStorage) {
    str result = readEntireStream(createProcess("svn", 
        ["co", "-r", toString(revision), "--non-interactive", "--trust-server-cert", repositoryURL], localStorage
    ));
    return result;
}

M3 createFileM3(loc file) {
  if (file.extension == "java") {
    M3 fileM3 = createM3FromFile(file);
    loc compilationUnitSrc = getCompilationUnit(fileM3);
  
    str fileContents = readFile(compilationUnitSrc);
  
    int total = compilationUnitSrc.end.line;
    int comment = countCommentedLoc(fileM3, fileContents);
    int empty = countEmptyLoc(fileM3, fileContents);
    int source = total - comment - empty;

    return m3WithAdditionalInfo(fileM3, createAstFromFile(file, true), total, comment, empty, source);
  }
  if (isFile(file))
    return unknownFileType(size(readFileLines(file)));
  throw "Location <file> is not a file";
}

bool isValid(M3 fileM3) {
  return !isEmpty(fileM3);
}

loc getCompilationUnit(M3 fileM3) {
  set[loc] compilationUnit = {src | <name, src> <- fileM3@declarations, isCompilationUnit(name)};
  assert size(compilationUnit) == 1 : "More than one compilation unit in a file???";
  loc compilationUnitSrc = getOneFrom(compilationUnit);
  
  return compilationUnitSrc;
}

int countTotalLoc(M3 fileM3) {
  loc compilationUnit = getCompilationUnit(fileM3);
  return compilationUnit.end.line;
}

int countCommentedLoc(M3 fileM3, str fileContents) {
  int result = 0;
  set[loc] comments = { src | <name, src> <- fileM3@documentation, isCompilationUnit(name) };
  for (source <- comments) {
    result += source.end.line - source.begin.line + 1 - checkForSourceLines(source, fileContents);
  }
  return result;
}

int countEmptyLoc(M3 fileM3, str fileContents)
  =  (0 | it + 1 | /^\s*$/ <- split("\n", removeComments(fileContents, fileM3)));
  
tuple[str language, int count] locPerLanguage(M3 fileM3) {
  return <"java", countTotalLoc(fileM3)>;
}

tuple[str language, int count] locPerLanguage(loc file) {
  return <file.extension, size(readFileLines(file))>;
}

int countCommentedLoc(M3 projectModel, loc cu) 
  = (0 | it + (doc.end.line - doc.begin.line + 1 - checkForSourceLines(doc)) | doc <- projectModel@documentation[cu]); 

private int checkForSourceLines(loc commentLoc, str fileContents) {
  str comment = substring(fileContents, commentLoc.offset, commentLoc.offset + commentLoc.length);
  // We will check to see if there are any source code in the commented lines
  loc commentedLines = commentLoc;
  // start from start of the line
  commentedLines.begin.column = 0;
  // increase to the next line to cover the full line
  commentedLines.end.line += 1;
  // we won't take any character from the next line
  commentedLines.end.column = 0;
  list[str] contents = split("\n", fileContents)[commentedLines.begin.line-1..commentedLines.end.line-1];
  str commentedLinesSrc = intercalate("\n", contents);
  // since we look till the start of the next line, we need to make sure we remove the extra \n from the end  
  if (isEmpty(last(contents)))
    commentedLinesSrc = replaceLast(commentedLinesSrc, "\n" , "");
  return size(split(trim(comment), trim(commentedLinesSrc)));
}

str removeComments(str contents, M3 fileM3) {
  set[loc] compilationUnit = {src | <name, src> <- fileM3@declarations, isCompilationUnit(name)};
  assert size(compilationUnit) == 1 : "More than one compilation unit in a file???";
  loc cu = getOneFrom(compilationUnit);
  list[str] listContents = split("\n", contents);
  list[str] result = listContents;
  for (loc commentLoc <- fileM3@documentation[cu]) {
    // remove comments
    result = result - slice(listContents, commentLoc.begin.line - 1, commentLoc.end.line - commentLoc.begin.line + 1);
  }
  return intercalate("\n", result);
}
