module org::maracas::lang::mj::Syntax

import Prelude;


//-------------------------------------------------
// MJ (Middleweight Java) Syntax
//-------------------------------------------------

start syntax Program 
	= program: ClassDefinition* classDefs
	;

syntax ClassDefinition 
	= classDefinition: AccessModifier modifier "class" Id class "extends" Id superClass "{"  
		FieldDefinition* fieldDefs
		ConstructorDefinition? constDefs
		MethodDefinition* methDefs
		"}"
	;
	
syntax FieldDefinition 
	= fieldDefinition: AccessModifier modifier VariableDeclaration def ";"
	;

syntax ConstructorDefinition 
	= constDef: Id class "(" {VariableDeclaration ","}* params ")" "{"
		"super" "(" {Id ","}* args ")" ";"
		Statement* statements
		"}"
	;

syntax MethodDefinition 
	= methDef: AccessModifier accMod Modifier mod ReturnType returnType Id meth"(" {VariableDeclaration ","}* params ")" "{"
		Statement* statements
		"}"
	;

syntax Modifier
	= "static"
	| "final" 
	;
	
// Extending to support minimal example
syntax AccessModifier
	= "public"
	| "private"
	| "protected"
	;
	
syntax ReturnType 
	=  \void: "void"
	| \type: Id;

syntax Expression 
	= null: "null"
	| variable: Id
	| fieldAccess: FieldMethodAccess
	| cast: "(" Id type ")" Id var
	| arithmExpression: ArithmExpression
	| promotableExpression: PromotableExpression
	;

// Extending to support minimal example
syntax ArithmExpression 
	= \num: IntegerLiteral
	| var: Id
	> left mult: ArithmExpression "*" ArithmExpression
	> left div: ArithmExpression "/" ArithmExpression
	> left sum:  ArithmExpression "+" ArithmExpression
	> left subs: ArithmExpression "-" ArithmExpression
	;
	
syntax PromotableExpression 
	= methInv: FieldMethodAccess "(" {Id ","}* args ")"
	| objCreation: "new" Id type "(" {Id ","}* args ")"
	;

syntax Statement 
	= noop: ";"
	| promExp: PromotableExpression exp ";"
	| cond: "if" "(" Id cond ")" "{" Statement* ifBody "}" "else" "{" Statement* elseBody "}"
	| loop: "while" "(" Id cond ")" "{" Statement* body "}"
	| fieldAssign: FieldMethodAccess field "=" Expression val ";"
	| varDecl: VariableDeclaration ";"
	| varAssign: Id var "=" Expression val ";"
	| \return: "return" Expression val ";"
	| block: "{" Statement* statements "}"
	;

syntax VariableDeclaration 
	= varDecl: Id class ("[" "]")? Id var
	;

syntax FieldMethodAccess 
	= Id invoker NoSpace "." NoSpace Id fieldMeth
	;

syntax Id = IdLiteral \ Keyword;
 
lexical IdLiteral = [A-Za-z0-9$]+ !>> [A-Za-z0-9$];
lexical IntegerLiteral = [\-]?[0-9]+ !>> [0-9];

keyword Keyword
	= "public"
	| "private"
	| "protected"
	| "static"
	| "final" 
	| "void"
	| "if"
	| "else"
	| "while"
	| "return"
	| "new"
	;

layout Whitespace = [\t-\n\r\ ]* !>> [\t-\n\r\ ];
layout NoSpace = @manual;