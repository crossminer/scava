@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module OO

import Prelude;
import util::Math;
import analysis::graphs::Graph;

import analysis::m3::Core;
import analysis::m3::AST;

import Generic;



@doc{
	Reuse Ratio (No. of superclasses / total no. of classes)
}
real RR(rel[loc, loc] superTypes, set[loc] allTypes) {
	if (size(allTypes) > 0) {
		return round(size(range(superTypes)) / toReal(size(allTypes)), 0.01);
	}
	return 0.0;
}


@doc{
	Specialization Ratio (No. of subclasses/ no. of super classes)
}
real SR(rel[loc, loc] superTypes) {
	nrOfSubTypes = size(domain(superTypes));
	nrOfSuperTypes = size(range(superTypes));

	if (nrOfSuperTypes > 0) {
		return round(nrOfSubTypes / toReal(nrOfSuperTypes), 0.01);
	}
	return 0.0;
}

@doc{
	Abstractness (nr of abstract types / nr of concrete types)
}
real A(set[loc] abstractTypes, set[loc] allTypes) {
	numConcreteTypes = size(allTypes - abstractTypes);
	if (numConcreteTypes > 0) {
		return round(size(abstractTypes) / toReal(numConcreteTypes), 0.01);
	}
	return 0.0;
}


@doc{
	Efferent Coupling
}
map[loc, int] Ce(rel[loc package, loc \type] packageTypes, rel[loc depender, loc dependee] typeDependencies) {
	packages = domain(packageTypes);
	
	otherPackageDependencies = typeDependencies o invert(packageTypes) - invert(packageTypes);
	
	return ( p : ( 0 | it + 1 | t <- packageTypes[p], otherPackageDependencies[t] != {} ) | p <- packages ); 
}

@doc{
	Afferent Coupling
}
map[loc, int] Ca(rel[loc package, loc \type] packageTypes, rel[loc depender, loc dependee] typeDependencies) {
	otherPackageDependencies = typeDependencies o invert(packageTypes) - invert(packageTypes);

	typesDependingOnPackage = packageTypes o invert(typeDependencies);	

	return ( p : size(typesDependingOnPackage[p]) | p <- domain(packageTypes) );
}

@doc{
	Instability
}
real I(int Ca, int Ce) {
	divisor = Ca + Ce;
	if (divisor > 0) {
		return round(Ce / toReal(divisor), 0.01);
	}
	return 0.0;
}


@doc{
	Calculate type dependencies (can be used for CBO and CF)
}
public rel[loc, loc] typeDependencies( 
  rel[loc subtype, loc supertype] superTypes,
  rel[loc caller, loc callee] methodCalls,
  rel[loc method, loc attribute] attributeAccesses,
  rel[loc ent, loc \type] entityTypeDependencies, // entities are variables, fields, parameters, exceptions, methods, etc.
  rel[loc \type, loc ent] typeMembers, // entities are variables, fields, parameters, exceptions, methods, inner classes (!)
  set[loc] allTypes) {
  
  dependencies = typeMembers o (methodCalls + attributeAccesses) o invert(typeMembers); // uses of members of other types
  dependencies += typeMembers o entityTypeDependencies;     // include types of variables, fields, parameters, etc
  dependencies += rangeR(typeMembers, allTypes); // include inner classes etc.
  dependencies += superTypes+; // include ancestor types

  dependencies = carrierR(dependencies, allTypes); // remove unknown types
  dependencies -= ident(allTypes); // remove self references

  return dependencies;
}


@doc{
	Coupling Factor (typeDependencies should not include inheritance related coupling)
}
public real CF(rel[loc, loc] typeDependencies, set[loc] allTypes) {
	numTypes = size(allTypes);
	
	numDependencies = size(typeDependencies + invert(typeDependencies));
	
	numPossibleDependencies = numTypes * (numTypes - 1);
	
	if (numPossibleDependencies > 0) {
		return round(numDependencies / toReal(numPossibleDependencies), 0.01);
	}
	else {
		return 0.0;
	}
}


@doc{
	Tight Class Cohesion per type
}
map[loc, real] TCC(
	map[loc \type, set[loc] methods] typeMethods,
 	map[loc \type, set[loc] fields] typeFields,
 	map[loc caller, set[loc] callees] calls,
 	map[loc method, set[loc] fields] fieldAccesses,
 	set[loc] allTypes) {

	map[loc, real] tcc = ();

	for (t <- allTypes) {
		methodsOfT = typeMethods[t]?{};
		numMethods = size(methodsOfT);
		maxConnections = numMethods * (numMethods - 1);

		if (maxConnections > 0) {
			fieldsOfT = typeFields[t]?{};
			
			// fieldConnections = carrierR(calls, methodsOfT)* o rangeR(fieldAccesses, typeFields[t]);
			callsTC = { <a, b> | a <- methodsOfT, b <- calls[a]?{}, b in methodsOfT }*;
			fieldConnections = { <a, c> | <a, b> <- callsTC, c <- fieldAccesses[b]?{}, c in fieldsOfT };
			
			methodConnections = fieldConnections o invert(fieldConnections);
			
			// directConnections = methodConnections - ident(methodsOfT);
			numDirectConnections = ( 0 | it + 1 | <a, b> <- methodConnections, a != b );

			// tcc[t] = round(size(directConnections) / toReal(maxConnections), 0.01);
			tcc[t] = round(numDirectConnections / toReal(maxConnections), 0.01);
		} else {
			tcc[t] = 0.0;
		}
	}
	
	return tcc;
}


@doc{
	Loose Class Cohesion per type
}
map[loc, real] LCC(
	map[loc \type, set[loc] methods] typeMethods,
 	map[loc \type, set[loc] fields] typeFields,
 	map[loc caller, set[loc] callees] calls,
 	map[loc method, set[loc] fields] fieldAccesses,
 	set[loc] allTypes) {

	map[loc, real] lcc = ();

	for (t <- allTypes) {
		methodsOfT = typeMethods[t]?{};
		numMethods = size(methodsOfT);
		maxConnections = numMethods * (numMethods - 1);

		if (maxConnections > 0) {
			fieldsOfT = typeFields[t]?{};
			
			// fieldConnections = carrierR(calls, methodsOfT)* o rangeR(fieldAccesses, typeFields[t]);
			callsTC = { <a, b> | a <- methodsOfT, b <- calls[a]?{}, b in methodsOfT }*;
			fieldConnections = { <a, c> | <a, b> <- callsTC, c <- fieldAccesses[b]?{}, c in fieldsOfT };
			
			methodConnections = fieldConnections o invert(fieldConnections);
			
			// indirectConnections = (methodConnections*) - ident(methodsOfT);		
			numIndirectConnections = ( 0 | it + 1 | <a, b> <- methodConnections*, a != b );
		
			// lcc[t] = round(size(indirectConnections) / toReal(maxConnections), 0.01);
			lcc[t] = round(numIndirectConnections / toReal(maxConnections), 0.01);
		} else {
			lcc[t] = 0.0;
		}
	}
	
	return lcc;
}

@doc{
	LCOM4: Lack of Cohesion of Methods as defined by Hitz & Montazeri
	The number of connected components within the related methods graph of a class.
	Two methods are related if one of them calls the other of if they both access the same field.
}
map[loc, int] LCOM4(
	map[loc caller, set[loc] callees] methodCalls,
	map[loc method, set[loc] fields] fieldAccesses,
	map[loc \type, set[loc] method] methods,
	map[loc \type, set[loc] field] fields,
	set[loc] allTypes) {
	
	map[loc, int] lcom = ();
	
	for (t <- allTypes) {
		fs = fields[t]?{};
		ms = methods[t]?{};
		
		localAccesses = { <a, b> | a <- fieldAccesses, a in ms, b <- fieldAccesses[a], b in fs }; //rangeR(domainR(fieldAccesses, ms), fs);
		
		relatedMethods = { <a, b> | a <- methodCalls, a in ms, b <- methodCalls[a], b in ms }; //carrierR(methodCalls, ms);
		relatedMethods += localAccesses o invert(localAccesses);
		
		lcom[t] = size(connectedComponents(relatedMethods));
	}
	
	return lcom;
}
