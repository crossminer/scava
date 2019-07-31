module org::maracas::match::fun::SetSimilarity

import Set;


real jaccardSimilarity(set[value] x, set[value] y) 
	= (size(x) > 0 && size(y) > 0) ? jaccard(x, y) : -1.0;