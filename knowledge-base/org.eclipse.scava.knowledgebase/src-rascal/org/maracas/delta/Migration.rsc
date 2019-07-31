module org::maracas::delta::Migration

import Relation;
import Set;
import IO;
import String;

import org::maracas::delta::Detector;
import org::maracas::m3::Core;
import lang::java::m3::Core;

data Migration (
	loc oldDecl = |unknown:///|,
	loc newDecl = |unknown:///|,
	loc oldUsed = |unknown:///|,
	loc newUsed = |unknown:///|,
	set[loc] oldUses = {},
	set[loc] newUses = {} ) 
	= migration(loc oldClient, loc newClient, Detection d);


set[Migration] migrations(loc newClient, set[Detection] detects) {
	set[Migration] migs = {};
	
	if (detects != {}) {
		loc oldClient = getOneFrom(detects).jar;
		M3 oldM3 = createM3(oldClient);
		M3 newM3 = createM3(newClient);
			
		for (detect <- detects) {
			loc oldDecl = detect.elem;
			loc newDecl = detect.elem; // 1-to-1 for now. Let's assume it's the same decl
			loc oldUsed = detect.used;
			loc newUsed = replacement(detect);
			
			Migration m = migration(oldClient, newClient, detect);
			
			if (oldDecl in domain(oldM3.declarations)) {
				m.oldDecl = oldDecl;
				m.oldUses = uses(oldM3, oldDecl);
		
				if (oldUsed in m.oldUses)
					m.oldUsed = oldUsed;
			}
		
			if (newDecl in domain(newM3.declarations)) {
				m.newDecl = newDecl;
				m.newUses = uses(newM3, newDecl);
				
				if (newUsed in m.newUses)
					m.newUsed = newUsed;
			}
			
			migs += m;
		}
		
	}
	
	return migs;
}

Migration buildMigration(Detection detect, loc newClient) {
	loc oldClient = detect.jar;
	loc oldDecl = detect.elem;
	loc newDecl = detect.elem; // 1-to-1 for now
	loc oldUsed = detect.used;
	loc newUsed = replacement(detect);
	M3 oldM3 = createM3(oldClient);
	M3 newM3 = createM3(newClient);
	
	Migration m = migration(oldClient, newClient, detect);
	
	if (oldDecl in domain(oldM3.declarations)) {
		m.oldDecl = oldDecl;
		m.oldUses = uses(oldM3, oldDecl);

		if (oldUsed in m.oldUses)
			m.oldUsed = oldUsed;
	}

	if (newDecl in domain(newM3.declarations)) {
		m.newDecl = newDecl;
		m.newUses = uses(newM3, newDecl);
		
		if (newUsed in m.newUses)
			m.newUsed = newUsed;
	}

	return m;
}

set[Migration] buildMigrations(set[Detection] detects, loc clientsPath) {
	set[Migration] result = {};

	int i = 0;
	for (Detection d <- detects) {
		i = i + 1;
		if (/<name: \S*>-[0-9]+/ := d.jar.file) {
			loc newClient = findJar(clientsPath, name);
			println("[<i>/<size(detects)>] Analyzing <newClient> [<d.typ>]");
			if (newClient != |unknown:///|)
				result += buildMigration(d, newClient);
		}
	}
	
	return result;
}

void checkMigrations(set[Migration] ms) {
	for (Migration m <- ms) {
		println("[<m.d.typ>] For <m.oldClient> -\> <m.newClient>");
	
		if (m.oldDecl == |unknown:///|)
			println("Couldn\'t find old declaration in old JAR");
		
		if (m.newDecl == |unknown:///|)
			println("Couldn\'t find new declaration in new JAR");
		
		if (m.oldUsed notin m.oldUses)
			println("oldUsed notin oldUses");
		
		if (m.newUsed notin m.newUses)
			println("newUsed notin newUses");
			
		if (m.oldUsed in m.newUses)
			println("oldUsed still in newUses");
	}
}

loc findJar(loc directory, str name) {
	for (str e <- listEntries(directory)) {
		loc jar = directory + e;
		
		if (contains(jar.file, name))
			return jar;
	}

	return |unknown:///|;
}

set[Migration] getCasesPerType(DeltaType typ, set[Migration] migs)
	= { m | m <- migs, migration(_, _, detection(_, _, _, _, typ)) := m };
	
set[Migration] removeCasesPerType(DeltaType typ, set[Migration] migs)
	= { m | m <- migs, migration(_, _, detection(_, _, _, _, t)) := m, t != typ };

set[loc] uses(M3 m, loc decl)
	= m.typeDependency[decl]
	+ m.methodInvocation[decl]
	+ m.fieldAccess[decl]
	+ m.implements[decl]
	+ m.extends[decl]
	+ m.annotations[decl]
	; 

// TODO: change elem by used
loc replacement(detection(_, _, used, _, accessModifiers())) = used; // If it's now private, maps to nothing, if protected, depends on IoC
loc replacement(detection(_, _, used, _, finalModifiers())) = used; // Depends on IoC (?)
loc replacement(detection(_, _, used, _, staticModifiers())) = used; // same
loc replacement(detection(_, _, used, _, abstractModifiers())) = used; // Depends on IoC; clients might have their own new implementation; or, most likely, the API now has a new Class that implements the now-abstract method, or...
loc replacement(detection(_, _, used, _, paramLists())) = used; // NOPE, FIXME
loc replacement(detection(_, _, used, _, types())) = used;
loc replacement(detection(_, _, used, _, extends())) = used;
loc replacement(detection(_, _, used, _, implements())) = used;
loc replacement(detection(_, _, used, _, deprecated())) = used;
loc replacement(detection(_, _, used, <old, new, _, _>, renamed())) = new;
loc replacement(detection(_, _, used, <old, new, _, _>, moved())) = new;
loc replacement(detection(_, _, used, <old, new, _, _>, removed())) = new;
loc replacement(detection(_, _, used, <old, new, _, _>, added())) = new;
