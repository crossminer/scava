module org::maracas::lang::patl::Abstract


//-------------------------------------------------
// PATL ADT
//-------------------------------------------------

data PATL = patl(list[Rule] rules);

data Rule 
	= transRule(list[Metavariable] vars, 
		list[Pattern] srcPatterns,
		list[Pattern] targPatterns);

data Metavariable = metavarDecl(str var, str oldType, str newType);

data Pattern
	= assignment(str var, Expression pattern)
	| expression(Expression pattern);
	
data Expression 
	= methodInvocation(tuple[str var, str inv] acc, list[str] arguments)
	| constructor(str typ, list[str] arguments)
	| fieldAccess(tuple[str var, str field] acc);