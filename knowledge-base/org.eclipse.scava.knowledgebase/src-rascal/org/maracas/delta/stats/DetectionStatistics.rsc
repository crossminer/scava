module org::maracas::delta::stats::DetectionStatistics

import org::maracas::delta::Detector;
import org::maracas::delta::stats::Core;

import Map;
import Node;


rel[str, int] casesPerChangeType(set[Detection] detects) {
	map[str, int] types = getDeltaTypesMap();
	
	for (detection(_, _, _, _, typ) <- detects) {
		str name = getName(typ);
		types[name] = types[name] + 1;
	}
	
	return toRel(types);
}