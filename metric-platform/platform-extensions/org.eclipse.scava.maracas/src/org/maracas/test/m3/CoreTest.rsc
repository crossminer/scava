module org::maracas::\test::m3::CoreTest

import lang::java::m3::Core;
import org::maracas::m3::Core;


//----------------------------------------------
// methodQualName
//----------------------------------------------
test bool methodQualNameWrongScheme() {
	try {
		methodQualName(|java+field:///|);
		return false;
	}
	catch : {
		return true;
	}
}

test bool methodQualNameRightScheme() {
	try {
		methodQualName(|java+method:///|);
		return true;
	}
	catch : {
		return false;
	}
}

test bool methodQualName1() 
	= methodQualName(|java+method:///path/to/a()|) == "/path/to/a";
	
test bool methodQualName2() 
	= methodQualName(|java+method:///path/to/a(java.lang.String%5B%5D)|) == "/path/to/a";

test bool methodQualName3() 
	= methodQualName(|java+method:///path/to/a(boolean,int)|) == "/path/to/a";
	

//----------------------------------------------
// sameMethodQualName
//----------------------------------------------
test bool sameMethodQualNameWrongScheme() {
	try {
		sameMethodQualName(|java+field:///|);
		return false;
	}
	catch : {
		return true;
	}
}

test bool sameMethodQualRightScheme() {
	try {
		methodQualName(|java+method:///|);
		return true;
	}
	catch : {
		return false;
	}
}

test bool sameMethodQualName1()
	= sameMethodQualName(
		|java+method:///path/to/a()|,
		|java+method:///path/to/a()|
	) == true;
	
test bool sameMethodQualName2()
	= sameMethodQualName(
		|java+method:///path/to/a(java.lang.String%5B%5D)|,
		|java+method:///path/to/a()|
	) == true;
	
test bool sameMethodQualName3()
	= sameMethodQualName(
		|java+method:///path/to/a(java.lang.String%5B%5D)|,
		|java+method:///path/to/a(boolean,int)|
	) == true;
	
test bool sameMethodQualName4()
	= sameMethodQualName(
		|java+method:///path/to/to/a(boolean,int)|,
		|java+method:///path/to/a(boolean,int)|
	) == false;
	

//----------------------------------------------
// memberName
//----------------------------------------------
test bool memberNameWrongScheme() {
	try {
		memberName(|java+compilationUnit:///|);
		return false;
	}
	catch : {
		return true;
	}
}

test bool memberNameMethodScheme() {
	try {
		memberName(|java+method:///|);
		return true;
	}
	catch : {
		return false;
	}
}

test bool memberNameFieldScheme() {
	try {
		memberName(|java+field:///|);
		return true;
	}
	catch : {
		return false;
	}
}

test bool memberNameClassScheme() {
	try {
		memberName(|java+class:///|);
		return true;
	}
	catch : {
		return false;
	}
}

test bool className() 
	= memberName(|java+class:///path/to/A|) == "A";
	
test bool innerClassName() 
	= memberName(|java+class:///path/to/a/A$A|) == "A$A";

test bool interfaceName() 
	= memberName(|java+interface:///path/to/IA|) == "IA";

test bool methodName1() 
	= memberName(|java+method:///path/to/a()|) == "a";
	
test bool methodName2() 
	= memberName(|java+method:///path/to/a(java.lang.String%5B%5D)|) == "a";

test bool methodName3() 
	= memberName(|java+method:///path/to/a(boolean,int)|) == "a";
	
test bool fieldName() 
	= memberName(|java+field:///path/to/A/a|) == "a";
	
	
//----------------------------------------------
// methodSignature
//----------------------------------------------
test bool methodSignatureWrongScheme() {
	try {
		methodSignature(|java+field:///|);
		return false;
	}
	catch : {
		return true;
	}
}

test bool methodSignatureRightScheme() {
	try {
		methodSignature(|java+method:///|);
		return true;
	}
	catch : {
		return false;
	}
}

test bool methodSignature1() 
	= methodSignature(|java+method:///path/to/a()|) == "a()";
	
test bool methodSignature2() 
	= methodSignature(|java+method:///path/to/a(java.lang.String%5B%5D)|) == "a(java.lang.String[])";

test bool methodSignature3() 
	= methodSignature(|java+method:///path/to/a(boolean,int)|) == "a(boolean,int)";
	