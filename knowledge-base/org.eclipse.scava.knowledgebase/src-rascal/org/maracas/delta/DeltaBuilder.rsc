module org::maracas::delta::DeltaBuilder

import IO;
import Boolean;
import lang::java::m3::AST;
import lang::java::m3::Core;
import lang::java::m3::TypeSymbol;
import org::maracas::config::Options;
import org::maracas::delta::Delta;
import org::maracas::io::properties::IO;
import org::maracas::m3::Core;
import org::maracas::m3::M3Diff;
import org::maracas::match::matcher::Matcher;
import org::maracas::match::matcher::JaccardMatcher;
import org::maracas::match::matcher::LevenshteinMatcher;
import Relation;
import Set;
import String;


Delta createDelta(M3 from, M3 to, loc optionsFile = |project://maracas/config/config.properties|) {
	M3Diff diff = createM3Diff(from, to);
	Delta d = delta(<from.id, to.id>);
	
	println("--------------------------");
	println("Computing options");
	//d.options           = readProperties(optionsFile);
	println("--------------------------");
	println("Computing accessModifiers");
	d.accessModifiers   = accessModifiers(diff); 
	println("--------------------------");
	println("Computing finalModifiers");
	d.finalModifiers    = finalModifiers(diff);
	println("--------------------------");
	println("Computing staticModifiers");
	d.staticModifiers   = staticModifiers(diff);
	println("--------------------------");
	println("Computing abstractModifiers");
	d.abstractModifiers = abstractModifiers(diff);
	println("--------------------------");
	println("Computing paramLists");
	d.paramLists        = paramLists(diff);
	println("--------------------------");
	println("Computing types");
	d.types             = returnTypes(diff) + types(diff);
	println("--------------------------");
	println("Computing extends");
	d.extends           = extends(diff);
	println("--------------------------");
	println("Computing implements");
	d.implements        = implements(diff);
	println("--------------------------");
	println("Computing deprecated");
	d.deprecated        = deprecated(diff, d);
	println("--------------------------");
	println("Computing renamed");
	d.renamed           = renamed(diff, d);
	println("--------------------------");
	println("Computing moved");
	d.moved             = moved(diff, d);
	println("--------------------------");
	println("Computing removed");
	d.removed           = removed(diff, d);
	println("--------------------------");
	println("Computing added");
	d.added             = added(diff, d);

	return d;
}


/*
 * Identifying changes in access modifiers
 */
private rel[loc, Mapping[Modifier]] accessModifiers(M3Diff diff) {
	result = {};
	// The confidence of the mapping is 1 if the signature is the same
	for (<e, addMod> <- diff.additions.modifiers, isTargetMember(e), isAccessModifier(addMod)) {
		for (remMod <- diff.removals.modifiers[e], isAccessModifier(remMod)) {
			result += buildDeltaMapping(e, remMod, addMod, 1.0, MATCH_SIGNATURE);
		}
	}

	return result;
}

private bool isAccessModifier(Modifier m) {
	accMods = { 
		\defaultAccess(), 
		\public(), 
		\private(), 
		\protected() 
	};
	return m in accMods;
}

/*
 * Identifying changes in final, static, and abstract modifiers
 */
private rel[loc, Mapping[Modifier]] finalModifiers(M3Diff diff) 
	= changedModifier(diff, \final());

private rel[loc, Mapping[Modifier]] staticModifiers(M3Diff diff) 
	= changedModifier(diff, \static());

private rel[loc, Mapping[Modifier]] abstractModifiers(M3Diff diff) 
	= changedModifier(diff, \abstract());

// The confidence of the mapping is 1 if the signature is the same
private rel[loc, Mapping[Modifier]] changedModifier(M3Diff diff, Modifier modifier) 
	= { buildDeltaMapping(elem, \default(), modif, 1.0, MATCH_SIGNATURE)
	| <elem, modif> <- diff.additions.modifiers, elem notin diff.addedDecls, isTargetMemberExclInterface(elem), modif := modifier }
	+ { buildDeltaMapping(elem, modif, \default(), 1.0, MATCH_SIGNATURE)
	| <elem, modif> <- diff.removals.modifiers, elem notin diff.removedDecls, isTargetMemberExclInterface(elem), modif := modifier };


private rel[loc, Mapping[loc]] deprecated(M3Diff diff, Delta delta) {
	load = DEP_MATCHES_LOAD in delta.options && fromString(delta.options[DEP_MATCHES_LOAD]);
	
	// TODO: check that we only load matches from a certain kind (i.e. class, method, field)	
	if (load) {
		url = |file://| + delta.options[DEP_MATCHES_LOC];
		matches = loadMatches(url);
		return { buildDeltaMapping(m.from, m) | m <- matches };
	}
	
	// For now, only mark @Deprecated elements
	return { buildDeltaMapping(e, e, e, 1.0, MATCH_SIGNATURE) 
			| 	<e, a> <- diff.additions.annotations, isTargetMember(e),
				a == |java+interface:///java/lang/Deprecated|};
}


/*
 * Identifying renamed elements
 */ 
private rel[loc, Mapping[loc]] renamed(M3Diff diff,  Delta delta) {
	diff = filterDiffRenamed(diff, delta);
	result = {};
	
	for (elem <- diff.removedDecls, isTargetMember(elem)) {
		invCont = invert(diff.from.containment);
		
		if (invCont[elem] != {}) {
			// In type cases we need the owner package instead of its compilation unit.
			cont = getNonCUContainer(elem, diff.from); 
			elemsSameCont = {};
			
			for (a <- diff.to.containment[cont], a in diff.addedDecls) {
				a = getNonCUChild(a, diff.to);
				if (a.scheme == elem.scheme) {
					elemsSameCont += a;
				}
			}
			
			if (elemsSameCont != {}) {
				diffTemp = diff;
				diffTemp.removedDecls = { elem };
				diffTemp.addedDecls = elemsSameCont;
				result += applyMatchers(diffTemp, delta.options, MATCHERS);
			}
		}
		
		else {
			c = diff.from.containment;
			p = c[elem];
			println(elem);
		}
		
	}
	
	
	//for (<cont, elem> <- removals.containment, elem in diff.removedDecls, isTargetMember(elem)) {
	//	// In type cases we need the owner package instead of its compilation unit.
	//	if (isCompilationUnit(cont)) {
	//		cont = getOneFrom(invert(removals.containment)[cont]);
	//	}
	//	
	//	elemsSameCont = {};
	//	for (a <- additions.containment[cont], a in diff.addedDecls) {
	//		if (isCompilationUnit(a)) {
	//			a = getOneFrom(additions.containment[a]);
	//		}
	//		if (a.scheme == elem.scheme) {
	//			elemsSameCont += a;
	//		}
	//	}
	//	
	//	if (elemsSameCont != {}) {
	//		diffTemp = diff;
	//		diffTemp.removedDecls = { elem };
	//		diffTemp.addedDecls = elemsSameCont;
	//		result += applyMatchers(diffTemp, delta.options, MATCHERS);
	//	}
	//}
	return result;
}

private M3Diff filterDiffRenamed(M3Diff diff, Delta delta) {
	diff.removedDecls 
		= diff.removedDecls 
		- ((!isEmpty(delta.paramLists)) ? delta.paramLists.elem : {});
	return diff;
}


private rel[loc, Mapping[loc]] moved(M3Diff diff, Delta delta) {
	diff = filterDiffMoved(diff, delta);
	return applyMatchers(diff, delta.options, MATCHERS);
}

private M3Diff filterDiffMoved(M3Diff diff, Delta delta) {
	diff.removedDecls 
		= diff.removedDecls 
		- ((!isEmpty(delta.paramLists)) ? delta.paramLists.elem : {})
		- ((!isEmpty(delta.renamed)) ? delta.renamed.mapping<0> : {});
	diff.addedDecls 
		= diff.addedDecls
		- ((!isEmpty(delta.renamed)) ? delta.renamed.mapping<1> : {});
	
	return diff;
}

/*
 * Identifying removed elements
 */
// FIXME: match signature?
private rel[loc, Mapping[loc]] removed(M3Diff diff, Delta delta)
	= { buildDeltaMapping(e, e, |unknown:///|, 1.0, MATCH_SIGNATURE) 
	  | e <- diff.removedDecls, isTargetMember(e) };


private rel[loc, Mapping[loc]] added(M3Diff diff, Delta delta)
	= { buildDeltaMapping(e, |unknown:///|, e, 1.0, MATCH_SIGNATURE) 
	  | e <- diff.addedDecls, isTargetMember(e) };
	  

// Default matcher: Jaccard
rel[loc, Mapping[loc]] applyMatchers(M3Diff diff, map[str,str] options, str option) {
	Matcher currentMatcher = matcher(jaccardMatch);
	matchers = (option in options) ? split(",", options[option]) : []; 
	threshold = ("<option>Threshold" in options) ? options["<option>Threshold"] : 0.7;
	matches = {};
	
	if (matchers != []) {
		for (m <- matchers) {  
			switch (trim(m)) {
				case MATCH_LEVENSHTEIN : currentMatcher = matcher(levenshteinMatch);
				case MATCH_JACCARD : currentMatcher = matcher(jaccardMatch); 
				default : currentMatcher = matcher(jaccardMatch);
			}
			matches += currentMatcher.match(diff, threshold);
		}
	}
	return { buildDeltaMapping(m.from, m) | m <- matches };
}

/*
 * Identifying changes in method parameter lists
 */
rel[loc, Mapping[list[TypeSymbol]]] paramLists(M3Diff diff) 
	= changedMethodSignature(diff, methodParams);


/*
 * Identifying changes in method return types
 */
rel[loc, Mapping[TypeSymbol]] returnTypes(M3Diff diff)
	= changedMethodSignature(diff, methodReturnType);
	

private rel[loc, Mapping[&T]] changedMethodSignature(M3Diff diff, &T (&U) fun) {
	methsAdded = { <m, typ> | <m, typ> <- diff.additions.types, isMethod(m) };
	methsRemoved = { <m, typ> | <m, typ> <- diff.removals.types, isMethod(m) };
		
	result = {};
	for (<methAdded, typeAdded> <- methsAdded) {
		for (<methRemoved, typeRemoved> <- methsRemoved, sameMethodQualName(methAdded, methRemoved)) {
			elemsRemoved = fun(typeRemoved);
			elemsAdded = fun(typeAdded);
	
			if (elemsRemoved != elemsAdded) {
				result += buildDeltaMapping(methRemoved, elemsRemoved, elemsAdded, 1.0, MATCH_SIGNATURE);
			}
		}
	}
	return result;
}
	
/*
 * Identifying changes in field return types
 */
rel[loc, Mapping[TypeSymbol]] types(M3Diff diff) {
	fieldsAdded =   { <f, typ> | <f, typ> <- diff.additions.types, isField(f) };
	fieldsRemoved = { <f, typ> | <f, typ> <- diff.removals.types,  isField(f) };

	result = {};
	for (<fieldAdded, typeAdded> <- fieldsAdded) {
		for (<fieldRemoved, typeRemoved> <- fieldsRemoved,
				fieldAdded == fieldRemoved,
				typeAdded != typeRemoved) {
			result += buildDeltaMapping(fieldRemoved, typeRemoved, typeAdded, 1.0, MATCH_SIGNATURE);
		}
	}

	return result;
}

/*
 * Identifying changes in class/interface extends/implements relations
 */
rel[loc, Mapping[loc]] extends(M3Diff diff) {
	result = {};
	
	for (<cls, oldSup> <- diff.removals.extends) {
		newSup = diff.additions.extends[cls];
		newSupLoc = size(newSup) == 1 ? getOneFrom(newSup) : |unknown:///|;
		result += buildDeltaMapping(cls, oldSup, newSupLoc, 1.0, MATCH_SIGNATURE);
	}

	for (<cls, newSup> <- diff.additions.extends) {
		oldSup = diff.removals.extends[cls];
		oldSupLoc = size(oldSup) == 1 ? getOneFrom(oldSup) : |unknown:///|;
		result += buildDeltaMapping(cls, oldSupLoc, newSup, 1.0, MATCH_SIGNATURE);
	}
	
	return result;
}

rel[loc, Mapping[set[loc]]] implements(M3Diff diff) {
	removals = diff.removals;
	additions = diff.additions;
	
	return { buildDeltaMapping(typ,
				removals.implements[typ],
				additions.implements[typ],
				1.0,
				MATCH_SIGNATURE)
			| typ <- domain(removals.implements) + domain(additions.implements) };
}