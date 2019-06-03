/*
 * Taking as starting point the grammar defined in
 * lang::java::\syntax::Java15 from Rascal.
 * Updating it to comply with Java 1.8 grammar.
 */
module org::maracas::lang::java::Syntax

syntax Identifier
	= IdentifierChars
	;
	
syntax IdentifierChars 
	= JavaLetter JavaLetterOrDigit*
	;

syntax Literal 
	= IntLiteral
	| FloatLiteral
	| BoolLiteral
	| CharLiteral
	| StringLiteral
	| NullLiteral
	;
	
syntax IntLiteral 
	= deci: DeciLiteral !>> [l L]
	| hexa: HexaLiteral !>> [l L]
	| octa: OctaLiteral !>> [l L] 
	| bina: BinaLiteral !>> [l L]
	;

lexical HexaLiteral = HexaNumeral !>> [0-9 A-F a-f] [l L]?;
lexical HexaNumeral = [0] [X x] [0-9 A-F a-f]+;

lexical OctaLiteral = OctaNumeral !>> [0-7] [L l]?;
lexical OctaNumeral = [0] [0-7]+;

lexical DeciLiteral = DeciNumeral !>> [. 0-9 D F d f] [l L]?;
lexical DeciNumeral 
	= [1-9] [0-9]*
	| "0"
	;

lexical BinaLiteral = BinaNumeral !>> [0 1] [l L];
lexical BinaNumeral = "0" [b B] BinaDigits;
lexical BinaDigits = [0 1] ([0 1 \-]+ [0 1])?;

syntax FloatLiteral 
	= deci: DeciFloatLiteral \ DeciFloatLiteralKeywords !>> [D F d f]
	| hexa: HexaFloatLiteral !>> [D F d f] 
	;
	
lexical DeciFloatLiteral = DeciFloatNumeral [D F d f]?;
lexical DeciFloatNumeral
	= [0-9] !<< [0-9]+ DeciFloatExponentPart
	| [0-9] !<< [0-9]+ >> [D F d f]
	| [0-9] !<< [0-9]+ "." [0-9]* !>> [0-9] DeciFloatExponentPart?
	| [0-9] !<< "." [0-9]+ !>> [0-9] DeciFloatExponentPart?
	;
lexical DeciFloatExponentPart = [E e] SignedInteger !>> [0-9];
keyword DeciFloatLiteralKeywords = [0-9]+;

lexical HexaFloatLiteral = HexaFloatNumeral [D F d f]?;
lexical HexaFloatNumeral = HexaSignificand \ HexaSignificandKeywords !>> [0-9 A-F a-f] BinaryExponent;
lexical HexaSignificand 
	= [0] [X x] [0-9 A-F a-f]* "." [0-9 A-F a-f]*
	| [0] [X x] [0-9 A-F a-f]+
	;
lexical BinaryExponent = [P p] SignedInteger !>> [0-9];
keyword HexaSignificandKeywords = [0] [X x] ".";

syntax BoolLiteral
	= \true: "true"
	| \false: "false"
	;

syntax CharLiteral 
	= "\'" SingleChar "\'"
	| "\'" EscapeSeq "\'";

lexical SingleChar = ![\n \a0D \' \\];
lexical EscapeSeq 
	= unicode: UnicodeEscape
	| octa: OctaEscape
	| named: NamedEscape
	;
lexical UnicodeEscape = "\\" [u]+ [0-9 A-F a-f] [0-9 A-F a-f] [0-9 A-F a-f] [0-9 A-F a-f];
lexical OctaEscape
	= "\\" [0-3] [0-7]+ !>> [0-7]
	| "\\" [0-7] !>> [0-7]
	| "\\" [4-7] [0-7]
	;
lexical NamedEscape = "\\" [\" \' \\ b f n r t];

syntax StringLiteral 
	= StringLex
	;

lexical StringLex = "\"" StringPart* "\"";
lexical StringPart 
	= escape: EscapeSeq
	| chars: ([\a00] | ![\n \a0D \" \\])+ !>> ![\n \a0D \" \\]  !>> [\a00]
	;
	
syntax NullLiteral 
	= null: "null"
	;

lexical JavaLetter = [a-z A-Z _ $]+;
lexical JavaLetterOrDigit = [a-z A-Z 0-9 _ $]+;
lexical SignedInteger = [+ \-]? [0-9]+;

keyword Keyword 
	= BoolLiteral
	| NullLiteral
	;