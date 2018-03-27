module org::eclipse::scava::dependency::model::maven::util::MavenVersionHandler

import List;
import String;

import org::eclipse::scava::dependency::model::maven::util::internal::MavenVersionHelper;
import org::eclipse::scava::dependency::model::maven::util::MavenVersionOptions;


public str getVersionType(str version) {
	version = replaceAll(version," ", "");
	
	if(!contains(version, ",")) {
		return SOFT_VERSION;
	}
	else if(/\[[a-zA-Z0-9_-.]\]/ := version) {
		return HARD_VERSION;
	}
	else if(/((\[|\()[a-zA-Z0-9_-.]?,[a-zA-Z0-9_-.]?(\]|\),)+(\[|\()[a-zA-Z0-9_-.]?,[a-zA-Z0-9_-.]?(\]|\))/ := version) {
		return MULTIPLE_RANGE_VERSIONS;
	}
	else if(/(\[|\()[a-zA-Z0-9_-.]?,[a-zA-Z0-9_-.]?(\]|\))/ := version) {
		return RANGE_VERSION;
	}
	else {
		return ERROR_VERSION;
	}
}