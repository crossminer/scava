module org::maracas::match::matcher::JaccardMatcher

import IO;
import List;
import lang::java::m3::Core;
import org::maracas::delta::Delta;
import org::maracas::config::Options;
import org::maracas::m3::Core;
import org::maracas::m3::M3Diff;
import org::maracas::match::fun::SetSimilarity;
import org::maracas::match::\data::Data;
import org::maracas::match::matcher::Matcher;
import Relation;
import Set;

	
set[Mapping[loc]] jaccardMatch(M3Diff diff, real threshold) 
	= match(diff, createData(diff, threshold), jaccardSimilarity);

private Data createData(M3Diff diff, real threshold)
	= \set (
		threshold = threshold,
		from = createSets(diff.removedDecls, diff.from),
		to = createSets(diff.addedDecls, diff.to)
	);
	
private map[loc, set[loc]] createSets(set[loc] declarations, M3 m) {
	result = ();

	for (d <- declarations) {
		if (isType(d)) {
			inv = { *m.methodInvocation[methodDecl] | methodDecl <- m.containment[d] };
			result += (d : inv);
		} 
		else if (isMethod(d)) {
			result += (d : m.methodInvocation[d]);
		} 
		else if (isField(d)) {
			result += (d : {});
		};
	}
	return result;
}