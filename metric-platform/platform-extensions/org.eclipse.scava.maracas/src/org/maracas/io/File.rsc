module org::maracas::io::File

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