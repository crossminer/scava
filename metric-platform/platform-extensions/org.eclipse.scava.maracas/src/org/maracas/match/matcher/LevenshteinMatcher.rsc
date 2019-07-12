module org::maracas::match::matcher::LevenshteinMatcher

import IO;
import List;
import lang::java::m3::Core;
import org::maracas::delta::Delta;
import org::maracas::config::Options;
import org::maracas::m3::Core;
import org::maracas::m3::M3Diff;
import org::maracas::match::fun::StringSimilarity;
import org::maracas::match::\data::Data;
import org::maracas::match::matcher::Matcher;
import Relation;
import Set;


set[Mapping[loc]] levenshteinMatch(M3Diff diff, real threshold) 
	= match(diff, createData(diff, threshold), levenshteinSimilarity);

private Data createData(M3Diff diff, real threshold)
	= string (
		threshold = threshold,
		from = createSnippets(diff.removedDecls, diff.from),
		to = createSnippets(diff.addedDecls, diff.to)
	);

private map[loc, str] createSnippets(set[loc] declarations, M3 m)
	= (d : createSnippet(d, m) | d <- declarations, isTargetMember(d));
	
private str createSnippet(loc elem, M3 owner)
	= (owner.id.extension == "jar") 
	? createSnippetForJar(elem, owner)
	: createSnippetForSourceCode(elem, owner);

// We don't take into account declarations ordering 
private str createSnippetForJar(loc elem, M3 owner) 
	= memberDeclaration(elem, owner)
	+ getM3SortString(elem, memoizedContainment(owner))
	+ getM3SortString(elem, memoizedMethodInvocation(owner))
	+ getM3SortString(elem, memoizedFieldAccess(owner));
	
private str createSnippetForSourceCode(loc elem, M3 owner) 
	= readFile(getFirstFrom(memoizedDeclarations(owner)[elem]));