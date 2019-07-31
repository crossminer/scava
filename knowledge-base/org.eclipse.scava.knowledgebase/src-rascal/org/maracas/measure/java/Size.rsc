module org::maracas::measure::java::Size

import IO;
import List;
import String;

@doc{
	Returns the size of the file or directory in bytes.
}
@javaClass{org.maracas.measure.java.Size}
@reflect{for debugging}
java int countBytes(loc source);

@doc{
	Counts the number of lines of code per file. Comment lines 
	are included. Blank lines are excluded.
	
	@param source: loc pointing to the source file or directory
	@return number of LOCs
}
int countLOCs(loc source) 
	= countLines(source, getCodeLines);

private list[str] getCodeLines(list[str] lines) 
	= [l | l <- lines, (/^\s*$/ !:= l)];

@doc{
	Counts the number of comment lines of code per file. Blank 
	and code lines are excluded.
	
	@param source: loc pointing to the source file or directory
	@return number of CLOCs
}
int countCLOCs(loc source) 
	= countLines(source, getCommentLines);

private list[str] getCommentLines(list[str] lines) {
	list[str] cLines = [];
	bool readingComment = false;
	
	for (l <- lines) {
		// Checking "// blabla" comments
		if (/^\s*\/\/[.]*/ := l) {
			cLines += l;
		}
		
		// Checking "/* blabla */" comments
		if (/^\s*\/\*[.]*/ := l) {
			readingComment = true;
		}
		if (readingComment) {
			cLines += l;
			readingComment = !(/[.]*\*\/(\n)?$/ := l);
		}
	}
	return cLines;
}

private int countLines(loc source, list[str] (list[str]) predicate) {
	int cont = 0;
	
	if (isDirectory(source)) {
		for (e <- listEntries(source)) {
			loc entry = source + e;
			cont += countLines(entry, predicate);
		}
	}
	
	if (isJavaFile(source)) {
		list[str] lines = predicate(readFileLines(source));
		cont = size(lines);
	}
	
	return cont;
}

private bool isJavaFile(loc file)
	= isFile(file) && endsWith(file.path, ".java");
