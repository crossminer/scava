@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module DynamicFeatures

import String;

import lang::php::ast::AbstractSyntax;
import lang::php::stats::Stats;


public data DynamicFeature
  = varref()
  | overload()
  | varargs()
  | eval()
  ;


public int sumCounts(map[DynamicFeature, int] c) = (c[varref()]?0) + (c[varargs()]?0) + (c[overload()]?0) + (c[eval()]?0); 


public map[DynamicFeature, int] getDynamicFeatureCounts(node n) {

	map[DynamicFeature, int] result = (
		varref(): 0,
		overload(): 0,
		varargs(): 0,
		eval(): 0
	);

	top-down-break visit (n) {
		case fetchClassConst(expr(_),_): result[varref()] += 1;
		case assign(Expr t,_): if (containsVV(t)) result[varref()] += 1; 
		case assignWOp(Expr t,_,_): if (containsVV(t)) result[varref()] += 1;
 		case listAssign(ll,_): if (true in { containsVV(t) | t <- ll }) result[varref()] += 1;
		case refAssign(Expr t,_): if (containsVV(t)) result[varref()] += 1;
 		case new(expr(_),_): result[varref()] += 1;
 		case methodCall(_,expr(_),_): result[varref()] += 1;
		case include(Expr e,_): if (scalar(string(_)) !:= e) result[varref()] += 1;
 		case propertyFetch(_,expr(_)): result[varref()] += 1;
		case var(expr(_)): result[varref()] += 1;
		case \break(someExpr(e)): if (scalar(_) !:= e) result[varref()] += 1;
		case \continue(someExpr(e)): if (scalar(_) !:= e) result[varref()] += 1;

 		case staticCall(cr, mr, _): {
 			if (expr(_) := cr) result[varref()] += 1;
 			if (expr(_) := mr) result[varref()] += 1;
 		}
 			
 		case staticPropertyFetch(cr, pr): {
 			if (expr(_) := cr) result[varref()] += 1;
 			if (expr(_) := pr) result[varref()] += 1;
 		}

 		case method(name,_,_,_,_):
 			if (toLowerCase(name) in {"__set","__get","__isset","__unset","__call","__callStatic"}) {
 				result[overload()] += 1;
 			}

		case call(f,_):
			if (expr(_) := f) {
				result[varref()] += 1;
			}
			else if (name(name(fn)) := f) {
				fn = toLowerCase(fn);
				if (fn == "eval") {
					result[eval()] += 1;
				}
				else if (fn in {"func_get_args","func_get_arg","func_num_args"}) {
					result[varargs()] += 1;
				}
			}

		case eval(_):
			result[eval()] += 1;
	}

	return result;
}
