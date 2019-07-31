module org::maracas::m3::M3Diff

import lang::java::m3::Core;
import Relation;


data M3Diff(
		M3 removals = m3(|file:///|),
		M3 additions = m3(|file:///|),
		set[loc] removedDecls = {},
		set[loc] addedDecls = {}
	) 
	= m3Diff(M3 from, M3 to);


M3Diff createM3Diff(M3 from, M3 to)
	= m3Diff (
		from, 
		to,
		removals = m3Removals(from, to),
		additions = m3Additions(from, to),
		removedDecls = m3RemovedDecls(from, to),
		addedDecls = m3AddedDecls(from, to)
	);


private M3 m3Removals(M3 from, M3 to) 
	= diffJavaM3(from.id, [from, to]);
	 
private M3 m3Additions(M3 from, M3 to) 
	= diffJavaM3(to.id, [to, from]);


private set[loc] m3RemovedDecls(M3 from, M3 to) 
	= m3DiffDecls(from, to);
	
private set[loc] m3AddedDecls(M3 from, M3 to) 
	= m3DiffDecls(to, from);
	
private set[loc] m3DiffDecls(M3 target, M3 rest)
	= domain(target.declarations) - domain(rest.declarations);