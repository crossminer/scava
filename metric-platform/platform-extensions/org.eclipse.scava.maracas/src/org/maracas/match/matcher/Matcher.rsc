module org::maracas::match::matcher::Matcher

import lang::csv::IO;
import lang::java::m3::Core;
import org::maracas::delta::Delta;
import org::maracas::config::Options;
import org::maracas::m3::Core;
import org::maracas::m3::M3Diff;
import org::maracas::match::\data::Data;


data Matcher = matcher(
	set[Mapping[loc]] (M3Diff diff, real threshold) match);


/*
 * Loads a set of matches from a CSV file. Default deparator: ",".
 */
set[Mapping[loc]] loadMatches(loc csv) {
	matches = readCSV(#rel[loc from, loc to], csv);
	// If matches are given, then confidence must be equal to 1.
	return matches join {<1.0, MATCH_LOAD>}; 
}

set[Mapping[loc]] match(M3Diff diff, Data dat, real (&T, &T) fun) {
	threshold = dat.threshold;
	result = {};
	
	for (loc r <- diff.removedDecls, isTargetMember(r)) {
		for (loc a <- diff.addedDecls, r.scheme == a.scheme) {	
			real similarity = fun(dat.from[r], dat.to[a]);
								
			if (similarity > threshold) { 
				result += buildMapping(r, a, similarity, MATCH_LEVENSHTEIN);
			}
		}
	}
	return result;
}