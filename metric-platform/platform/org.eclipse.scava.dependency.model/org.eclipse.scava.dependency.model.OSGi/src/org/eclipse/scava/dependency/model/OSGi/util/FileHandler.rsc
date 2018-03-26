module org::eclipse::scava::dependency::model::OSGi::util::FileHandler

import IO;
import String;

import org::eclipse::scava::dependency::model::OSGi::util::ExtensionOptions;


public list[loc] getJarsLoc(loc directory) {
	content = listEntries(directory);
	return [directory + "/<l>" | str l <- content, isFile(directory + l), l[-3..] == JAR_EXTENSION];
}

public list[loc] getJarsLocRecursively(loc directory) {
	content = listEntries(directory);
	jars = [];
	
	for(c <- content) {
		if(isFile(directory + c), c[-3..] == JAR_EXTENSION) {
			println("entra");
			jars += directory + "/<c>";
		}
		else if(isDirectory(directory + c)) {
			jars += getJarsLocRecursively(directory + c);
		}
		else {
			continue;
		}
	}
	return jars;
}

public loc getFileFromJar(loc jar, str fileName) {
	if(!startsWith(jar.scheme,"jar")) {
		jar.scheme = "jar+<jar.scheme>";
		jar.path = "<jar.path>!";
	}
	
	try {
		return find(fileName, [jar]);
	}	
	catch: 
		files = jar.ls;
		target = |file:///|;
		for(f <- files, target == |file:///|, isDirectory(f)) {
			target = getFileFromJar(f,fileName);
			return target;
		}
		return target;
	
}