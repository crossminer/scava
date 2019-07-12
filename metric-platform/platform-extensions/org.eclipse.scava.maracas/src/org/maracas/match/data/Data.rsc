module org::maracas::match::\data::Data

data Data
	= \set (
		real threshold = 0.0,
		map[loc elem, set[loc] repr] from = {},
		map[loc elem, set[loc] repr] to = {} ) 
	| string (
		real threshold = 0.0,
		map[loc elem, str repr] from = {},
		map[loc elem, str repr] to = {} )
	;