@doc{ 
	Taken from:
	Jean-Rémy Falleri, Floréal Morandat, Xavier Blanc, Matias Martinez, and Martin Monperrus.
	2014. Fine-grained and accurate source code differencing. 
	In 29th International Conference on Automated Software Engineering. ACM, New York, 313-324. 
	
	Some modifications have been applied.
} 
module org::maracas::match::matcher::GumTreeMatcher

import IO;
import lang::java::m3::Core;
import List;
import ListRelation;
import Node; 
import org::maracas::match::matcher::Tree;
import org::maracas::util::Math;
import Set;
import util::Math;


list[value] topDownMatch(&T <: node t1, &R <: node t2, int minHeight) {
	priority1 = [];
	priority2 = [];
	candidates = [];
	mappings = [];
	
	priority1 += <height(t1), t1>;
	priority2 += <height(t2), t2>;
	
	terminate = false;
	while (!terminate) {
		maxHeight1 = max(domain(priority1));
		maxHeight2 = max(domain(priority2));
				
		if (min(maxHeight1, maxHeight2) <= minHeight) {
			terminate = true;
		}
		
		if (maxHeight1 > maxHeight2) {
			// Add children from nodes with height = maxHeight1.
			priority1 += [ <height(c), c> |
				<h, n> <- domainR(priority1, {maxHeight1}), 
				&T <: node c <- getNodeChildren(n) ];
				
			// Remove nodes from list with height = maxHeight1.
			priority1 = domainX(priority1, {maxHeight1});
		}
			
		else if (maxHeight1 < maxHeight2) {
			// Add children from nodes with height = maxHeight2.
			priority2 += [ <height(c), c> |
				<h, n> <- domainR(priority2, {maxHeight2}),
				&T <: node c <- getNodeChildren(n) ];
				
			// Remove nodes from list with height = maxHeight2.
			priority2 = domainX(priority2, {maxHeight2});
		}
		
		else {
			nodesMaxHeight1 = range(domainR(priority1, {maxHeight1}));
			nodesMaxHeight2 = range(domainR(priority2, {maxHeight2}));
			priority1 = domainX(priority1, {maxHeight1});
			priority2 = domainX(priority2, {maxHeight2});
			
			cartesian = nodesMaxHeight1 * nodesMaxHeight2;
			for (<t1, t2> <- cartesian) {
				if (isomorphic(t1, t2)) {
					candidates += <t1, t2>;
					terminate = true;
				}
			}
			
			candidatesDom = domain(candidates);
			for (n <- nodesMaxHeight1) {
				if (n notin candidatesDom) {
					priority1 += [ <height(c), c> | &S <: node c <- getNodeChildren(n) ];
				}
			}
			
			candidatesRang = range(candidates);
			for (n <- nodesMaxHeight2) {
				if (n notin candidatesRang) {
					priority2 += [ <height(c), c> | &S <: node c <- getNodeChildren(n) ];
				}
			}
		}
	}
	
	// Identify one-to-one mappings, and remove them from candidates.
	candidatesInv = invert(candidates);
	for (<n1, n2> <- candidates) {
		if (size(candidates[n1]) == 1 && size(candidatesInv[n2]) == 1) {
			mappings += <n1, n2>;
			candidates -= <n1, n2>;
		}
	}
	
	// Less than function for list sorting.
	bool diceLessThan (
		tuple[real c1, tuple[&T <: node, &T <: node] m1] n1,
		tuple[real c2, tuple[&R <: node, &R <: node] m2] n2) 
		= c1 > c2;
		
	// Sort candidates using dice function.
	candidatesDice = [ <diceCoefficient(n1, n2, mappings), <n1, n2>>
		| <n1, n2> <- candidates ];
	candidatesDiceSort = sort(candidatesDice, diceLessThan);
	mappings += range(candidatesDiceSort);
	
	return mappings;
}
	
@doc {
	Metric used for comparing the similarity of two trees.
}
real diceCoefficient(&T <: node t1, &T <: node t2, lrel[node, node] mappings) {
	// TODO: there can be repeated children? Consider using other structure.
	descendantsT1 = descendants(t1);
	descendantsT2 = descendants(t2);
	commonDescendants = ( 0 | it + 1 
		|d <- descendantsT1, m <- mappings[d], m in descendantsT2 );
		
	return 0.0 + ((2 * commonDescendants) / (size(descendantsT1) + size(descendantsT2)));
}

