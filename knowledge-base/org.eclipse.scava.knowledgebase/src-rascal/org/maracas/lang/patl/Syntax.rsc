module org::maracas::lang::patl::Syntax

import Prelude;


//-------------------------------------------------
// PATL (PAtch-like Transformation Language) Syntax
//-------------------------------------------------

start syntax RuleSequence = patl: TransformationRule* rules;

syntax TransformationRule 
	= 	transRule: "(" { MetavariableDeclaration "," }+ vars ")" 
		"{" SourcePattern+ srcPatterns TargetPattern+ targPatterns "}";

syntax MetavariableDeclaration 
	= metavarDecl: Id name ":" Id oldType "-\>" Id newType; 

syntax SourcePattern = "-" StatementPattern pattern;

syntax TargetPattern = "+" StatementPattern pattern;

syntax StatementPattern 
	= assignment: Id metavariable "=" ExpressionPattern expPattern ";"
	| expression: ExpressionPattern expPattern ";";

syntax ExpressionPattern 
	= methodInvocation: FieldMethodAccess "(" {Id ","}* args ")"
	| constructor: "new" Id type "(" {Id ","}* args ")"
	| fieldAccess: FieldMethodAccess;
	
syntax FieldMethodAccess = Id var NoSpace "." NoSpace Id fieldMeth;

lexical Id = [A-Za-z0-9$]+ !>> [A-Za-z0-9$];

layout Whitespace = [\t-\n\r\ ]* !>> [\t-\n\r\ ];
layout NoSpace = @manual;
