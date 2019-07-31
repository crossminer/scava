module org::maracas::m3::Core

import IO;
import lang::java::m3::ClassPaths;
import lang::java::m3::AST;
import lang::java::m3::Core;
import lang::java::m3::TypeSymbol;
import org::maracas::io::File;
import List;
import Node;
import Relation;
import Set;
import String;
import Type;
import ValueIO;

// extends lang::java::m3::AST::Modifier
// Could be moved to M3 creation itself
// but this is the quickest way :)
data Modifier =
	\defaultAccess();


@memo map[loc, set[loc]] memoizedDeclarations(M3 m) = toMap(m.declarations);
@memo map[loc, set[TypeSymbol]] memoizedTypes(M3 m) = toMap(m.types);
@memo map[loc, set[loc]] memoizedContainment(M3 m) = toMap(m.containment);
@memo map[loc, set[Modifier]] memoizedModifiers(M3 m) = toMap(m.modifiers);
@memo map[loc, set[loc]] memoizedExtends(M3 m) = toMap(m.extends);
@memo map[loc, set[loc]] memoizedImplements(M3 m) = toMap(m.implements);
@memo map[loc, set[loc]] memoizedMethodInvocation(M3 m) = toMap(m.methodInvocation);
@memo map[loc, set[loc]] memoizedFieldAccess(M3 m) = toMap(m.fieldAccess);

set[value] getM3Set(loc elem, map[loc, set[value]] m) 
	= (elem in m) ? m[elem] : {};
	
list[value] getM3SortValue(loc elem, map[loc, set[value]] m) 
	= (elem in m) ? sort(m[elem]) : [];

str getM3SortString(loc elem, map[loc, set[value]] m) 
	= toString(getM3SortValue(elem, m));

loc getNonCUContainer(loc elem, M3 m) {
	invCont = invert(m.containment);
	cont = getOneFrom(invCont[elem]);
	
	if (isCompilationUnit(cont)) {
		pkg = invCont[cont];
		cont = (pkg != {}) ? getOneFrom(pkg) : cont;
	}
	else {
		cont = getOneFrom(invCont[elem]);
	}
	
	return cont;
}

loc getNonCUChild(loc elem, M3 m) {
	if (isCompilationUnit(elem)) {
		child = m.containment[elem];
		elem = (child != {}) ? getOneFrom(child) : elem;
	}
	return elem;
}

// TODO: consider moving this function to Rascal module lang::java::m3::Core
bool isType(loc entity) = isClass(entity) || isInterface(entity);

bool isTargetMemberExclInterface(loc elem)
	= isClass(elem)
	|| isMethod(elem)
	|| isField(elem);

bool isTargetMember(loc elem)
	= isTargetMemberExclInterface(elem)
	|| isInterface(elem);


bool storeM3(loc projectJar, loc projectM3) {
	try {
		M3 m = createM3(projectJar);
		writeBinaryValueFile(projectM3, m);
		return true;
	}
	catch :
		return false;
}

@memo
M3 createM3(loc project, loc mvnExec=|file:///Users/ochoa/installations/apache-maven-3.5.4/bin/mvn|) {
	M3 m = (project.scheme == "jar" || project.extension == "jar") ? createM3FromJar(project) : m3FromFolder(project);

	return fillDefaultVisibility(filterAnonymousClasses(m));
}

M3 m3FromFolder(loc project, loc mvnExec=|file:///Users/ochoa/installations/apache-maven-3.5.4/bin/mvn|) {
	if (existFileWithName(project, "pom.xml") 
		|| existFileWithName(project, "MANIFEST.MF")) {
		
		// This step seems to take too long
		classPath = projectClassPath(project, mvnExec);
		
		return createM3FromFiles(project,
			fetchFilesByExtension(project, "java"),
			sourcePath=[project],
			classPath=classPath);
	}
	else {
		return createM3FromDirectory(project);
	}
}

private list[loc] projectClassPath(loc project, loc mvnExec) {
	classPaths = getClassPath(project, mavenExecutable=mvnExec);
	return [ *classPaths[cp] | cp <- classPaths ];
}

private set[loc] fetchFilesByExtension(loc directory, str extension) {
	set[loc] files = {};
	if (isDirectory(directory)) { 
		for (f <- directory.ls) {
			if (isDirectory(f)) {
				files += fetchFilesByExtension(f, extension);
			}
			if (isFile(f) && f.extension == "<extension>") {
				files += f;
			}
		}
	}
	return files;
}

// In JAR M3s, inner classes are denoted with '$'.
// In source code M3s, inner classes are denoted either with
// '/' in uri.path, or with '.' in uri.file (e.g. param types)
// Also, type erasure; e.g. JAR=Map$Entry, source=Map.Entry%3CK,V%3E,
// and JAR=java.lang.Object, source=E
// I did not solve that yet.
loc jarLocToSourceLoc(loc l) {
	// If typedecl, replace '$' with '/' in file
	if (isType(l) || isEnum(l))
		l.file = replaceAll(l.file, "$", "/");
	// If method/field/constructor/initializer,
	// replace '$' with '/' in path and '$' with
	// '.' in file
	else if (isMethod(l) || isField(l)) {
		l.file = replaceAll(l.file, "$", ".");
		l.path = replaceAll(l.path, "$", "/");
	}

	return l;
}

@memo
M3 createM3FromDirectoryCached(loc directory) {
	return createM3FromDirectory(directory);
}

str sourceCode(loc jarLocation, loc logical) {
	if (logical == |unknown:///|)
		return "";

	loc sourcesLocation = jarLocation;
	sourcesLocation.extension = "";
	sourcesLocation.file = sourcesLocation.file + "-sources";

	if (isDirectory(sourcesLocation)) {
		M3 sourcesM3 = createM3FromDirectoryCached(sourcesLocation);
		loc sourceLoc = jarLocToSourceLoc(logical);
		set[loc] found = sourcesM3.declarations[sourceLoc];

		if (size(found) > 0)
			return readFile(getOneFrom(found));
		else
			return "Couldn\'t find source for <logical>";
	}

	return "No sources available";
}

str javadoc(loc jarLocation, loc logical) {
	loc sourcesLocation = jarLocation;
	sourcesLocation.extension = "";
	sourcesLocation.file = sourcesLocation.file + "-sources";

	if (isDirectory(sourcesLocation)) {
		M3 sourcesM3 = createM3FromDirectoryCached(sourcesLocation);
		set[str] javadocs = { readFile(l) | l <- sourcesM3.documentation[logical] };
		return ("" | it + "<doc>" | doc <- javadocs);
	}

	return "";
}

list[str] javadocLinks(loc jarLocation, loc logical) {
	str doc = replaceAll(javadoc(jarLocation, logical), "\n", "");

	if (!isEmpty(doc))
		return [ trim(replaceAll(link, "*", "")) | /\{@link <link: [^}]*>\}/ := doc ];
	else
		return [];
}

M3 fillDefaultVisibility(M3 m3) {
	accMods = { \defaultAccess(), \public(), \private(), \protected() };

	// Concise version, *extremely* slow (?)
	//m3.modifiers += { <elem, \defaultAccess()> | elem <- domain(m3.declarations),
	//					(isType(elem) || isMethod(elem) || isField(elem))
	//					&& isEmpty(m3.modifiers[elem] & accMods) }; 

	m3.modifiers += { <elem, \defaultAccess()> | elem <- domain(m3.declarations),
						(isType(elem) || isMethod(elem) || isField(elem))
						&& <elem, \public()> notin m3.modifiers
						&& <elem, \protected()> notin m3.modifiers
						&& <elem, \private()> notin m3.modifiers };

	return m3;
}

M3 filterAnonymousClasses(M3 m)
	= filterM3(m, 
		bool (value v1, value v2) {
			return isAnonymousClass(v1) && isAnonymousClass(v2);
		});

private bool isAnonymousClass (value v) {
	if (loc l := v)
		return /\$[0-9]+/ !:= l.uri;
	return true;
}

M3 filterM3(M3 m, set[loc] elems) 
	= filterM3(m, 
		bool (value v1, value v2) {
			return v1 in elems || v2 in elems;
		});

/**
 * Filters out all tuples <a, b> from every relation
 * of the M3 model 'm' where neither 'a' nor 'b' are in
 * the 'elems' set. 
 */
M3 filterXM3(M3 m, set[loc] elems) 
	= filterM3(m, 
		bool (value v1, value v2) {
			return v1 notin elems && v2 notin elems;
		});

/**
 * Filters out all tuples <a, b> from every relation
 * of the M3 model 'm' where neither 'a' nor 'b' are in
 * the 'elems' set. 
 */
M3 filterXM3WithExcpetions(M3 m, set[loc] elems, set[loc] excep) 
	= filterM3(m,
		bool(value v1, value v2) {
			if (v1 notin elems && v2 in elems) {
				return v1 in excep;
			}
			else if (v2 notin elems && v1 in elems) {
				return v2 in excep;
			}
			else {
				return v1 notin elems && v2 notin elems;
			}
		});

/**
 * Filter out all tuples <a, b> from every relation
 * of the M3 model 'm' for which predicate(a, b)
 * does not hold
 */
M3 filterM3(M3 m, bool (value v1, value v2) predicate) {
	m3Filtered = lang::java::m3::Core::m3(m.id);

	map[str, value] kws = getKeywordParameters(m);
	for (str relName <- kws) {
		value v = kws[relName];

		if (rel[value, value] relation := v)
			kws[relName] = { <a, b> | <a, b> <- relation, predicate(a, b) };
	};

	return setKeywordParameters(m3Filtered, kws);
} 

str methodQualName(loc m) {
	if (isMethod(m)) {
		return (/<mPath: [a-zA-Z0-9.$\/]+>(<mParams: [a-zA-Z0-9.$\/]*>)/ := m.path) ? mPath : "";
	}
	else {
		throw "Cannot get a method qualified name from <m>.";
	}
}

bool sameMethodQualName(loc m1, loc m2) {
	if (isMethod(m1) && isMethod(m2)) {
		m1Name = methodQualName(m1);
		m2Name = methodQualName(m2);
		return m1Name == m2Name;
	}
	else {
		throw "Cannot compare <m1> and <m2>. Wrong scheme(s).";
	}
}

str methodName(loc m) {
	if (isMethod(m)) {
		return substring(methodQualName(m), (findLast(methodQualName(m), "/") + 1));
	}
	else {
		throw "Cannot get a method name from <m>.";
	}
}

str methodSignature(loc m) {
	if (isMethod(m)) {
		return substring(m.path, (findLast(m.path, "/") + 1));
	}
	else {
		throw "Cannot get a method signature from <m>.";
	}
}

str memberName(loc m) {
	if(isMethod(m)) {
		return memberName(methodQualName(m));
	}
	else if (isType(m) || isField(m)) {
		return memberName(m.path);
	}
	else {
		throw "Cannot get a name from unsupported memeber <m>.";
	}
}

private str memberName(str path) 
	= substring(path, (findLast(path, "/") + 1));

 
list[TypeSymbol] methodParams(TypeSymbol typ) 
	= (\method(_,_,_,params) := typ) ? params : [];
	
TypeSymbol methodReturnType(TypeSymbol typ) 
	= (\method(_,_,ret,_) := typ) ? ret : TypeSymbol::\void();

str memberDeclaration(loc elem, M3 m) {
	if (isType(elem)) {
		return typeDeclaration(elem, m);
	}
	if (isMethod(elem)) {
		return methodDeclaration(elem, m);
	}
	if (isField(elem)) {
		return fieldDeclaration(elem, m);
	}	
	throw "<elem> is not part of the scoped members.";
}

str typeDeclaration(loc typ, M3 m) {
	if (isType(typ)) {
		list[Modifier] modifiers = sort(memoizedModifiers(m)[typ]);
		str name = memberName(typ);
		set[loc] super = (typ in memoizedExtends(m)) ? memoizedExtends(m)[typ] : {};
		list[loc] interfaces = (typ in memoizedImplements(m)) ? sort(memoizedImplements(m)[typ]) : [];
		
		return "<modifiers> <name> <super> <interfaces>";
	}
	else {
		throw "Cannot compute a type declaration from <typ>";
	}
}

str methodDeclaration(loc meth, M3 m) {
	if (isMethod(meth)) {
		list[Modifier] modifiers = sort(memoizedModifiers(m)[meth]);
		TypeSymbol methType = getOneFrom(memoizedTypes(m)[meth]);
		TypeSymbol returnType = methodReturnType(methType);
		str signature = methodSignature(meth);
		
		return "<modifiers> <returnType> <signature>";
	}
	else {
		throw "Cannot compute a method declaration from <meth>";
	}
}

str fieldDeclaration(loc field, M3 m) {
	if (isField(field)) {
		list[Modifier] modifiers = sort(memoizedModifiers(m)[field]);
		set[TypeSymbol] fieldType = memoizedTypes(m)[field];
		str name = memberName(field);
		
		return "<modifiers> <fieldType> <name>";
	}
	else {
		throw "Cannot compute a field declaration from <field>";
	}
}