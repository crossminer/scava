module org::maracas::io::properties::Parse

import org::maracas::io::properties::Syntax;
import ParseTree;

public Properties parseProperties(loc l) = parse(#Properties, l);