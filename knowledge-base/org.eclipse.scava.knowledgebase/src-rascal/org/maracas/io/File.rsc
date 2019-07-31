module org::maracas::io::File

import Exception;
import IO;
import String;

bool existFileWithName(loc directory, str name) {
	if (isDirectory(directory)) {
		found = false;
		for (f <- directory.ls) {
			if (isDirectory(f)) {
				found = found || existFileWithName(f, name);
				if(found) {
					return true;
				}
			}
			if (isFile(f) && endsWith(f.path, "/<name>")) {
				return true;
			}
		}
	}
	return false;
}

str getFileName(loc file) {
	int begin = findLast(file.path, "/"); 
	int end = size(file.path);
	return substring(file.path, begin, end);
}

public loc unzipFile(loc file, loc targetDir) {
    zip = file[scheme = "jar+<file.scheme>"][path = file.path + "!/"];
    
    if (copyDirectory(zip, targetDir)) {
        return targetDir;
    }
    
    throw IO("Could not copy content of <file> to <targetDir>");
}

set[loc] walkJARs(loc dataset) {
	set[loc] result = {};

	for (e <- listEntries(dataset)) {
		loc entry = dataset + e;

		if (isDirectory(entry))
			result += walkJARs(entry);
		else if (endsWith(e, ".jar"))
			result += entry;
	};

	return result;
}