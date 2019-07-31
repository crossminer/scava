module org::maracas::delta::Detector

import lang::java::m3::AST;
import lang::java::m3::Core;
import org::maracas::delta::Delta;
import org::maracas::m3::Core;
import Relation;
import Set;


//----------------------------------------------
// ADT
//----------------------------------------------

data Detection = detection (
	loc jar,
	loc elem,
	loc used,
	Mapping[&T] mapping, 
	DeltaType typ
);

data DeltaType
	= accessModifiers()
	| finalModifiers()
	| staticModifiers()
	| abstractModifiers()
	| paramLists()
	| types()
	| extends()
	| implements()
	| deprecated()
	| renamed()
	| moved()
	| removed()
	| added()
	;
	

//----------------------------------------------
// Builder
//----------------------------------------------

set[Detection] detections(M3 client, Delta delta) 
	= detections(client, delta, accessModifiers())
	+ detections(client, delta, finalModifiers())
	+ detections(client, delta, staticModifiers())
	+ detections(client, delta, abstractModifiers())
	+ detections(client, delta, paramLists())
	+ detections(client, delta, types())
	+ detections(client, delta, extends())
	+ detections(client, delta, implements())
	+ detections(client, delta, deprecated())
	+ detections(client, delta, renamed())
	+ detections(client, delta, moved())
	+ detections(client, delta, removed())
	+ detections(client, delta, added())
	;

// Accessing anything that is now access-restricted is problematic, so
// defer to the generic detections() function (though I'm not sure about
// what's precisely inside typeDependency, so there could be false positives
private set[Detection] detections(M3 client, Delta delta, accessModifiers()) 
	= detections(client, delta.accessModifiers, accessModifiers());

// The only problematic cases are if the client overrides a now-final method
// or extends a now-final class
//
// Note: Final fields must be initialized locally or in a constructor.
//       The compiler may inline the initial value where the field is
//       accessed, rather than explictly accessing the field. It seems
//       to depend on whether the field is initialized locally (inlined)
//       or in a construtor (non-inlined). Also, probably compiler-dependent.
private set[Detection] detections(M3 client, Delta delta, finalModifiers()) {
	M3 m3to = createM3(delta.id.to);
	set[Detection] result = {};

	for (<loc f, Mapping[&T] mapping> <- delta.finalModifiers) {
		if (isClass(f))
			// Look for extending classes
			// TODO: Transitive closure?
			result += { detection(client.id, elem, f, mapping, finalModifiers()) | <loc elem, f> <- client.extends };
		
		if (isMethod(f)) {
			// client.methodOverrides is scoped by the client, so we don't know which API methods it's overriding
			// buggy workaround: look for methods of the same name in classes that extend the parent API class
			// TODO: transitive closure
			loc cont = getOneFrom(invert(m3to.containment)[f]);
			result += { detection(client.id, elem, f, mapping, finalModifiers()) | <loc cls, elem> <- client.containment,
																		<cls, cont> <- client.extends,
																		invert(client.names)[elem] == invert(m3to.names)[f] };
		}
	}

	return result;
}

private set[Detection] detections(M3 client, Delta delta, staticModifiers()) 
	= detections(client, delta.staticModifiers, staticModifiers());

// Problematic cases: the client invokes the constructor of a now-abstract class;
// the client directly invokes a now-abstract method (which is fine if the call
// is dynamically dispatched to a class with an actual implementation of the abstract method)
// TODO
private set[Detection] detections(M3 client, Delta delta, abstractModifiers()) 
	= detections(client, delta.abstractModifiers, abstractModifiers());

private set[Detection] detections(M3 client, Delta delta, deprecated()) 
	= detections(client, delta.deprecated, deprecated());

private set[Detection] detections(M3 client, Delta delta, renamed()) 
	= detections(client, delta.renamed, renamed());
	
private set[Detection] detections(M3 client, Delta delta, moved()) 
	= detections(client, delta.moved, moved());

private set[Detection] detections(M3 client, Delta delta, removed()) 
	= detections(client, delta.removed, removed());

private set[Detection] detections(M3 client, Delta delta, extends()) 
	= detections(client, delta.extends, extends());
	
private set[Detection] detections(M3 client, Delta delta, implements()) 
	= detections(client, delta.implements, implements());
	
private set[Detection] detections(M3 client, Delta delta, paramLists()) 
	= detections(client, delta.paramLists, paramLists());

private set[Detection] detections(M3 client, Delta delta, types()) 
	= detections(client, delta.types, types());

// Creates a Detection for every non-abstract class in the client that
// extends/implements a class/interface that exposes a new abstract method
private set[Detection] detections(M3 client, Delta delta, added()) {
	M3 m3to = createM3(delta.id.to);
	set[Detection] result = {};
	
	for (<loc m, Mapping[&T] mapping> <- delta.added, mayBeHookMethod(m3to, m)) {
		loc cont = getOneFrom(invert(m3to.containment)[m]);
		
		// Should be a transitive closure, taking into account
		// potential intermediate implementers along the path
		result += { detection(client.id, elem, cont, mapping, added()) 
			| <loc elem, cont> <- (client.extends + client.implements),
			isClass(elem), \abstract() notin client.modifiers[elem] } ;
	}
	
	return result;
}

private set[Detection] detections(M3 client, rel[loc, Mapping[&T]] deltaRel, DeltaType typ) {	
	set[loc] dom = domain(deltaRel);
	uses = rangeR(client.typeDependency, dom)
		+ rangeR(client.methodInvocation, dom)
		+ rangeR(client.fieldAccess, dom)
		+ rangeR(client.implements, dom) // Transitive closure?
		+ rangeR(client.extends, dom)   // Transitive closure?
		+ rangeR(client.annotations, dom);
		
	return { detection(client.id, elem, used, mapping, typ) | <loc elem, loc used> <- uses, mapping <- deltaRel[used] };
}

bool isInDetections(loc elem, loc used, DeltaType typ, set[Detection] detections) {
	for (d <- detections) {
		if (detection(_, elem, used, _, typ) := d) {
			return true;
		}
	}
	return false;
}

set[Detection] getDetectionsPerType(DeltaType typ, set[Detection] detections)
	= { d | d <- detections, detection(_, _, _, _, typ) := d};