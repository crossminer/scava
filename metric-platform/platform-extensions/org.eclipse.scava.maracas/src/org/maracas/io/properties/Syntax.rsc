module org::maracas::io::properties::Syntax

start syntax Properties
	= properties: {Property EOL}*
	;
	
syntax Property
	= property: String key '=' String value
	;

lexical String
	= ![\n\r\",] ![\n\r,]* !>> ![\n\r,]
	;
  
lexical EOL
	= [\n]
	| [\r][\n]
	| [\r] !>> [\n]
	;