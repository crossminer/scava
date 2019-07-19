module org::maracas::match::matcher::DeclarationMatcher

import org::maracas::delta::Delta;
import org::maracas::m3::Core;
import org::maracas::m3::M3Diff;


set[Mapping[loc]] levenshteinMatch(M3Diff diff, real threshold) 
	= levenshteinMatch(diff, threshold, createSnippet);

private str createSnippet(loc elem, M3 owner)
	= memberDeclaration(elem, owner);