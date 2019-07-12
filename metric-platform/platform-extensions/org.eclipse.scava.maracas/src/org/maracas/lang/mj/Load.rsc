module org::maracas::lang::mj::Load

import IO;
import Prelude;
import org::maracas::lang::mj::Syntax;

// Return the Parse Tree of the program (cf. mj::Syntax)
Program parseMJ(str content) = parse(#Program, content, allowAmbiguity=false);
Program parseMJ(loc file) = parseMJ(readFile(file));

Id parseId(str id) = parse(#Id, id);