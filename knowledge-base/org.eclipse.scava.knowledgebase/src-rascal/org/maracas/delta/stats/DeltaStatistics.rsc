module org::maracas::delta::stats::DeltaStatistics

import org::maracas::delta::Delta;

import Node;
import Set;


rel[str, int] casesPerChangeType(Delta delt) {
	kws = getKeywordParameters(delt);
	rel[str, int] stats = {};
	
	for (str name <- kws) {
		value v = kws[name];

		if (rel[loc, Mapping[&T]] relation := v) {
			stats += <name, size(relation)>;
		}
	}
		
	return stats;
}