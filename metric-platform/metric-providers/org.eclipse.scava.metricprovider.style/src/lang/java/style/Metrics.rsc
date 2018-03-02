@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module lang::java::style::Metrics

import analysis::m3::Core;
import lang::java::m3::Core;
import lang::java::m3::AST;
import Message;
import String;
import Set;
import Type;
import Node;

import lang::java::jdt::m3::Core;		// Java specific modules
import lang::java::jdt::m3::AST;

import lang::java::style::Utils;
import lang::java::style::CheckStates;

import IO;

/*
BooleanExpressionComplexity		DONE
ClassDataAbstractionCoupling	DONE
ClassFanOutComplexity			DONE
CyclomaticComplexity			DONE and removed
NPathComplexity					DONE and removed
JavaNCSS						TBD
*/

data Message = metric(str category, loc pos);

/* --- booleanExpressionComplexity ------------------------------------------*/

int countBooleanOperators(list[Expression] parents){
	nOperators = 0;
	for(parent <- parents){
		if(isBooleanExpression(parent)){
			nOperators += 1;
		}
	}
	return nOperators;
}

list[Message] booleanExpressionComplexity(Expression exp,  list[Expression] parents, M3 model) =
	(exp has src && isBooleanExpression(exp) && countBooleanOperators(parents) > 2) ? [metric("BooleanExpressionComplexity", exp@src)] : [];

/* --- classDataAbstractionCoupling -----------------------------------------*/

set[str] excludedClasses = {
	"boolean", "byte", "char", "double", "float", "int", "long", "short", "void", 
	"Boolean", "Byte", "Character", "Double", "Float", "Integer", "Long", "Short", 
	"Void", "Object", "Class", "String", "StringBuffer", "StringBuilder", 
	"ArrayIndexOutOfBoundsException", "Exception", "RuntimeException", 
	"IllegalArgumentException", "IllegalStateException", "IndexOutOfBoundsException", 
	"NullPointerException", "Throwable", "SecurityException", "UnsupportedOperationException", 
	"List", "ArrayList", "Deque", "Queue", "LinkedList", "Set", "HashSet", "SortedSet", "TreeSet", 
	"Map", "HashMap", "SortedMap", "TreeMap"};

list[Message] classDataAbstractionCoupling(Expression exp: \newObject(_, _, _, _),  list[Expression] parents, M3 model) {
	updateCheckState("classDataAbstractionCoupling", getTypeName(exp@typ));
	return [];
}
	
list[Message] classDataAbstractionCoupling(Expression exp: \newObject(Expression expr, _, _),  list[Expression] parents, M3 model){
	updateCheckState("classDataAbstractionCoupling", getTypeName(exp@typ));
	return [];
}

list[Message] classDataAbstractionCoupling(Expression exp: \newObject(Type \type, _, _),  list[Expression] parents, M3 model){
	updateCheckState("classDataAbstractionCoupling", getTypeName(exp@typ));
	return [];
}

list[Message] classDataAbstractionCoupling(Expression exp: \newObject(_, _),  list[Expression] parents, M3 model){
	updateCheckState("classDataAbstractionCoupling", getTypeName(exp@typ));
	return [];
}
	
// update/finalize

value updateClassDataAbstractionCoupling(value current, value delta) { 
	if(set[str] cs:= current && str d := delta) return cs + d; 
}

list[Message] finalizeClassDataAbstractionCoupling(Declaration d, value current) =
	(set[str] s := current && size(s- excludedClasses) > 7) ? [metric("ClassDataAbstractionCoupling", d@src)] : [];	


/* --- classFanOutComplexity ------------------------------------------------*/

set[str] getTypeNames(set[loc] locs) = { getTypeName(l) | l <- locs };

list[Message] classFanOutComplexity(Declaration cls, list[Declaration] parents, M3 model) =
	size(getTypeNames(model@typeDependency[model@containment[cls@decl]]) - excludedClasses) > 7 ? [ metric("ClassFanOutComplexity", cls@src) ] : [];

