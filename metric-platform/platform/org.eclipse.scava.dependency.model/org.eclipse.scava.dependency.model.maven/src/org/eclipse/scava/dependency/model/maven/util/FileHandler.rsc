module org::eclipse::scava::dependency::model::maven::util::FileHandler

import IO;
import String;


public loc getFileFromProject(loc project, str fileName) {
	try {
		return find(fileName, [jar]);
	}	
	catch: {
		files = project.ls;
		target = |file:///|;
		for(f <- files, target == |file:///|, isDirectory(f)) {
			target = getFileFromProject(f,fileName);
			return target;
		}
		return target;
	}
}