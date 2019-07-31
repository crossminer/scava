module org::maracas::match::fun::StringSimilarity

import lang::java::m3::Core;
import org::maracas::config::Options;
import org::maracas::delta::Delta;
import org::maracas::m3::Core;
import org::maracas::m3::M3Diff;
import org::maracas::match::matcher::Matcher;
import Relation;


@javaClass{org.maracas.match.fun.internal.StringSimilarity}
@reflect{for debugging}
java real levenshteinSimilarity(str snippet1, str snippet2);