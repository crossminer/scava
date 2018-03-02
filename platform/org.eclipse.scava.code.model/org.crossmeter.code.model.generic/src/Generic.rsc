@license{
Copyright (c) 2014 SCAVA Partners.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
}
module Generic

import util::FileSystem;
extend analysis::m3::Core;
extend analysis::m3::AST;
import org::eclipse::scava::metricprovider::ProjectDelta;

import IO;
import List;
import String;


@M3Extractor{generic()}
@memo
rel[Language, loc, M3] genericM3(loc project, ProjectDelta delta, map[loc repos,loc folders] checkouts, map[loc,loc] scratch) {
  //if (file.extension in blackListedExtensions) {
  //}
  rel[Language, loc, M3] result = {};
  folders = checkouts<folders>;
  for (folder <- folders, file <- visibleFiles(folder), estimateLanguageByFileExtension(file) != "") { 
    m = emptyM3(file);
    
    try {
      content = readFile(file);
      chs = size(content);
      numLines = chs == 0 ? 1 : (1 | it + 1 | /\n/ := content);
      lastline = chs == 0 ? 1 : size(readFileLines(file)[-1]);
      m@declarations = { <file[scheme="m3+unit"], file(0,chs,<1,0>,<numLines, lastline>)> }; // TODO remove
    }
    catch IO(str msg) : {
      m@messages += [error(msg, file)];
    }
    
    result += { <generic(), file, m> };
  }
  
  return result;
}

@ASTExtractor{generic()}
@memo
rel[Language, loc, AST] genericAST(loc project, ProjectDelta delta, map[loc repos,loc folders] checkouts, map[loc,loc] scratch) 
  = {<generic(), file, lines(readFileLines(file))> | folder <- checkouts<folders>, file <- visibleFiles(folder), estimateLanguageByFileExtension(file) != ""};

@memo
map[str, str] getLanguageExtensions() {
languageExtensions = {
<"ActionScript", ["as"]>,
<"Ada", ["adb"]>,
<"ASP", ["asp"]>,
<"ASP.NET", ["aspx", "axd", "asx", "asmx", "ashx"]>,
<"Assembler", ["asm"]>,
<"C", ["c", "h"]>,
<"C#", ["cs"]>,
<"C++", ["cpp", "hpp", "cxx", "hxx", "cc", "hh"]>,
<"Clojure", ["clj"]>,
<"Cobol", ["cob"]>,
<"CoffeeScript", ["coffee"]>,
<"Coldfusion", ["cfm"]>,
<"CSS", ["css"]>,
<"CUDA", ["cu"]>,
<"Erlang", ["erl", "hrl"]>,
<"F#", ["fs"]>,
<"Flash", ["swf"]>,
<"Fortran", ["f"]>,
<"GLSL", ["glsl", "vert", "frag"]>,
<"Go", ["go"]>,
<"Haskell", ["hs", "lhs"]>,
<"HLSL", ["hlsl"]>,
<"HTML", ["html", "htm", "xhtml", "jhtml", "dhtml"]>,
<"J#", ["jsl"]>,
<"Java", ["java", "jav"]>,
<"JavaScript", ["js", "jse", "ejs"]>,
<"JSP", ["jsp", "jspx", "wss", "do", "action"]>,
<"LISP", ["lisp", "cl"]>,
<"Lua", ["lua"]>,
<"Matlab", ["matlab"]>,
<"ML", ["ml", "mli"]>,
<"Objective C", ["m", "mm"]>,
//<"OpenCL", [""]>, // also uses cl
<"Pascal/Delphi", ["pas"]>,
<"Perl", ["pl", "prl", "perl"]>,
<"PHP", ["php", "php4", "php3", "phtml"]>,
<"PL/I", ["pli"]>,
<"Python", ["py"]>,
<"Rascal", ["rsc"]>,
<"Ruby", ["rb", "rhtml"]>,
<"Scala", ["scala"]>,
<"Shell script", ["sh", "bsh", "bash", "ksh", "csh"]>,
<"Smalltalk", ["st"]>,
<"SQL", ["sql"]>,
<"TCL", ["tcl"]>,
<"(Visual) Basic", ["bas", "frm", "cls", "ctl"]>,
<"Visual Basic Script", ["vbs", "vbscript"]>,
<"XML", ["xml", "xst"]>,
<"XSLT", ["xslt"]>
};

return (ext:lang | <lang, exts> <- languageExtensions, ext <- exts);
}

str estimateLanguageByFileExtension(loc filename)
{
  return getLanguageExtensions()[toLowerCase(filename.extension)]?"";
}

str getLanguageName(Language l)
{
	n = getName(l);
	
	if (size(n) > 0)
	{
		if (size(n) == 1)
		{
			return toUpperCase(n);
		}
		return toUpperCase(n[0]) + n[1..];
	}
	return n;
}
