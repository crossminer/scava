module org::maracas::lang::patl::Match

import org::maracas::lang::mj::Syntax;
import org::maracas::lang::patl::Syntax;

void matchingInstance() {}

void match(TransformationRule rule, MethodDefinition meth) {
	for(pattern <- rule.srcPatterns) {
		
		// TODO: check order (notion of block)
		// TODO: check that nested statements are also matched
		// Statement cond, Statement loop, Statement block
		for(stat <- meth.statements) {
			match(pattern, stat);
		}
	}
}

// TODO: StatementPattern assignment -> Statement fieldAssign [check types?]

// StatementPattern assignment -> Statement varAssign	
void match(
	(StatementPattern)`<Id metavariable> = <ExpressionPattern expPattern>;`, 
	(Statement)`<Id var> = <Expression val> ;`) {}

// StatementPattern expression -> Statement prmExp: 1) methInv; 2) objCreation 
void match(
	(StatementPattern)`<ExpressionPattern expPattern>;`, 
	(Statement)`<PromotableExpression exp> ;`) {}

// StatementPattern expression -> Statement varDecl	
void match(
	(StatementPattern)`<ExpressionPattern expPattern>;`, 
	(Statement)`<Id class> <Id var> ;`) {}
	
// StatementPattern expression -> Statement return	
void match(
	(StatementPattern)`<ExpressionPattern expPattern>;`, 
	(Statement)`return <Expression val> ;`) {}
	

// ExpressionPattern methodInvocation -> Expression methInv
void match((ExpressionPattern)`<Id var>.<Id meth> (<{Id ","}* args>)`) {}

// ExpressionPattern constructor -> Expression objCreation
void match((ExpressionPattern)`new <Id typ> (<{Id ","}* args>)`) {}

// ExpressionPattern fieldAccess -> Expression fieldAccess
void match((ExpressionPattern)`<Id var>.<Id field>`) {}