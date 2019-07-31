module org::maracas::delta::Delta

import lang::java::m3::AST;
import lang::java::m3::Core;
import org::maracas::m3::Core;
import Relation;
import Set;

data Delta (
	rel[loc elem, Mapping[Modifier] mapping] accessModifiers = {},
	rel[loc elem, Mapping[Modifier] mapping] finalModifiers = {},
	rel[loc elem, Mapping[Modifier] mapping] staticModifiers = {},
	rel[loc elem, Mapping[Modifier] mapping] abstractModifiers = {},
	rel[loc elem, Mapping[list[TypeSymbol]] mapping] paramLists = {},
	rel[loc elem, Mapping[TypeSymbol] mapping] types = {},
	rel[loc elem, Mapping[loc] mapping] extends = {},
	rel[loc elem, Mapping[set[loc]] mapping] implements = {},
	rel[loc elem, Mapping[loc] mapping] deprecated = {},
	rel[loc elem, Mapping[loc] mapping] renamed = {},
	rel[loc elem, Mapping[loc] mapping] moved = {},
	rel[loc elem, Mapping[loc] mapping] removed = {},
	rel[loc elem, Mapping[loc] mapping] added = {},
	map[str, str] options = ())
	= delta(tuple[loc from, loc to] id);

alias Mapping[&T] 
	= tuple [
		&T from, 
		&T to,
		real conf,
		str method
	];

Delta getClassDelta(Delta delta)
	= getFilteredDelta(delta, isType);	
	
Delta getMethodDelta(Delta delta)
	= getFilteredDelta(delta, isMethod);	
	
Delta getFieldDelta(Delta delta)
	= getFilteredDelta(delta, isField);	
	
private Delta getFilteredDelta(Delta delta, bool (loc) fun) {
	delta.accessModifiers   = { m | m <- delta.accessModifiers, fun(m.elem) };
	delta.finalModifiers    = { m | m <- delta.finalModifiers, fun(m.elem) };
	delta.staticModifiers   = { m | m <- delta.staticModifiers, fun(m.elem) };
	delta.abstractModifiers = { m | m <- delta.abstractModifiers, fun(m.elem) };
	delta.paramLists        = { m | m <- delta.paramLists, fun(m.elem) };
	delta.types             = { m | m <- delta.types, fun(m.elem) };
	delta.extends           = { m | m <- delta.extends, fun(m.elem) };
	delta.implements        = { m | m <- delta.implements, fun(m.elem) };
	delta.deprecated        = { m | m <- delta.deprecated, fun(m.elem) };
	delta.renamed           = { m | m <- delta.renamed, fun(m.elem) };
	delta.moved             = { m | m <- delta.moved, fun(m.elem) };
	delta.removed           = { m | m <- delta.removed, fun(m.elem) };
	delta.added             = { m | m <- delta.added, fun(m.elem) };
	
	return delta;
}

tuple[loc, Mapping[&T]] buildDeltaMapping(loc elem, &T from, &T to, real score, str meth)
	= <elem, buildMapping(from, to, score, meth)>;

tuple[loc, Mapping[&T]] buildDeltaMapping(loc elem, Mapping[&T] mapping)
	= <elem, mapping>;
	
Mapping[&T] buildMapping(&T from, &T to, real score, str meth)
	= <from, to, score, meth>;

Delta breakingChanges(Delta delta) {
	M3 m3from = createM3(delta.id.from);
	M3 m3to   = createM3(delta.id.to);

	delta.accessModifiers   = { <e, m> | <e, m> <- delta.accessModifiers,   isAPI(m3from, e), !isAPI(m3to, e) };
	// Only problematic when going from static to non-static
	delta.finalModifiers    = { <e, m> | <e, m> <- delta.finalModifiers,    isAPI(m3from, e), m[1] == \final() };
	// Just a warning if miscalling a static method in a non-static way
	delta.staticModifiers   = { <e, m> | <e, m> <- delta.staticModifiers,   isAPI(m3from, e), m[0] == \static() };
	delta.abstractModifiers = { <e, m> | <e, m> <- delta.abstractModifiers, isAPI(m3from, e), m[1] == \abstract() };
	// May not be BC do to subtyping, autoboxing, etc.
	delta.paramLists        = { <e, m> | <e, m> <- delta.paramLists,        isAPI(m3from, e) };
	// May not be BC do to subtyping, autoboxing, etc.
	delta.types             = { <e, m> | <e, m> <- delta.types,             isAPI(m3from, e) };
	// May not be BC if replaced by a subtype / other cases
	delta.extends           = { <e, m> | <e, m> <- delta.extends,           isAPI(m3from, e) };
	// May not be BC if replaced by a subtype / other cases
	delta.implements        = { <e, m> | <e, m> <- delta.implements,        isAPI(m3from, e) };
	// Included as BC for now, though not really
	delta.deprecated        = { <e, m> | <e, m> <- delta.deprecated,        isAPI(m3from, e) };
	delta.renamed           = { <e, m> | <e, m> <- delta.renamed,           isAPI(m3from, e) };
	delta.moved             = { <e, m> | <e, m> <- delta.moved,             isAPI(m3from, e) };
	delta.removed           = { <e, m> | <e, m> <- delta.removed,           isAPI(m3from, e) };
	delta.added             = { <e, m> | <e, m> <- delta.added,             mayBeHookMethod(m3to, e) };

	return delta;
}

bool isAPI(M3 m, loc elem) { // FIXME: check the whole visibility path for nested elements
	if (isType(elem) || isEnum(elem))    // public or default, ie. package-private
		return \public() in m.modifiers[elem];
	if (isMethod(elem) || isField(elem)) // private, protected, public, or default
		return {\public(), \protected()} & m.modifiers[elem] != {}
			&& isAPI(m, getOneFrom(invert(m.containment)[elem]));
	return false;
}

bool mayBeHookMethod(M3 m, loc elem) {
	if (!isMethod(elem))
		return false;
	
	set[loc] types = invert(m.containment)[elem];
	if (types != {}) {
		loc typ = getOneFrom(types);	
		return isAPI(m, typ)                     // The method belongs to a public type
			&& \abstract() in m.modifiers[elem]; // and the method is abstract
			                                     // (don't need other checks, since if the method
			                                     // is abstract, the type is too, hence it cannot
			                                     // be final)
	}
	return false;
}
