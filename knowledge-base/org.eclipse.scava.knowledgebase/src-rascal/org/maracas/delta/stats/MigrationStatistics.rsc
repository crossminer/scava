module org::maracas::delta::stats::MigrationStatistics

import org::maracas::delta::Detector;
import org::maracas::delta::Migration;
import org::maracas::delta::stats::Core;

import IO;
import Map;
import Node;
import Set;


rel[str, int] getCasesPerChangeType(set[Migration] migs) {
	map[str, int] types = getDeltaTypesMap();
	
	for (migration(_, _, detection(_, _, _, _, typ)) <- migs) {
		str name = getName(typ);
		types[name] = types[name] + 1;
	}
	
	return toRel(types);
}

rel[str, int] getTruePositivesPerChangeType(set[Migration] migs)
	= getCasesPerChangeType(getTruePositives(migs));
	
rel[str, int] getFalsePositivesPerChangeType(set[Migration] migs)
	= getCasesPerChangeType(getFalsePositives(migs));
	
rel[str, int] getFalseNegativesPerChangeType(set[Migration] migs)
	= getCasesPerChangeType(getFalseNegatives(migs));


set[Migration] getTruePositives(set[Migration] migs) 
	= { m | m <- migs, m.newUsed in m.newUses };

set[Migration] getFalsePositives(set[Migration] migs) 
	= { m | m <- migs, m.newDecl != |unknown:///|, m.newUsed == |unknown:///| };

set[Migration] getFalseNegatives(set[Migration] migs)
	= { m | m <- migs, m.newDecl == |unknown:///|, m.newUsed == |unknown:///| };
		

int truePositives(set[Migration] migs) 
	= size(getTruePositives(migs));
	
int falsePositives(set[Migration] migs) 
	= size(getFalsePositives(migs));
	
int falseNegatives(set[Migration] migs)
	= size(getFalseNegatives(migs));
	

real precision(set[Migration] migs) 
	= (0.0 + truePositives(migs)) / (truePositives(migs) + falsePositives(migs));

real recall(set[Migration] migs)
	= (0.0 + truePositives(migs)) / (truePositives(migs) + falseNegatives(migs));
	
set[Migration] getUnmodifiedDeprecatedMembers(set[Migration] migs) {
	depr = getCasesPerType(deprecated(), migs);
	return { d | d <- depr, d.newDecl != |unknown:///|, d.oldUsed in d.newUses };
}

set[Migration] getModifiedDeprecatedMembers(set[Migration] migs) {
	depr = getCasesPerType(deprecated(), migs);
	return { d | d <- depr, d.newDecl != |unknown:///|, d.oldUsed notin d.newUses };
}