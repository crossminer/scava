@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module OOJava

import Relation;
import Map;

import lang::java::m3::Core;
import lang::java::m3::AST;
import lang::java::m3::TypeSymbol;
import analysis::graphs::Graph;
import org::eclipse::scava::metricprovider::MetricProvider;
import Java;

import OO;
import OOFactoids;
import ck::NOC;
import ck::DIT;
import ck::RFC;
import ck::CBO;
import ck::LCOM;
import ck::NOM;
import ck::NOA;
import mood::PF;
import mood::MHF;
import mood::MIF;

import analysis::graphs::Graph;
import org::eclipse::scava::metricprovider::MetricProvider;
import Prelude;

@memo
map[loc, set[loc]] containmentMap(M3 m) = toMap(m@containment);

@memo
set[loc] enums(M3 m) = { e | e <- m@declarations<name>, e.scheme == "java+enum" };

@memo
set[loc] anonymousClasses(M3 m) = { e | e <- m@declarations<name>, e.scheme == "java+anonymousClass" };

@memo
set[loc] allTypes(M3 m) = classes(m) + interfaces(m) + enums(m) + anonymousClasses(m);

@memo
rel[loc, loc] superTypes(M3 m) = m@extends + m@implements;

@memo
rel[loc, loc] typeDependencies(M3 m3) = typeDependencies(superTypes(m3), m3@methodInvocation, m3@fieldAccess, typeSymbolsToTypeDependencies(m3@types), domainR(m3@containment+, allTypes(m3)), allTypes(m3));

@memo
rel[loc, loc] allMethods(M3 m3) = { <t, m> | t <- allTypes(m3), m <- (containmentMap(m3))[t] ? {}, isMethod(m) };

@memo
map[loc, set[loc]] allMethodsMap(M3 m3) = ( t : { m | m <- (containmentMap(m3))[t]? {}, isMethod(m) } | t <- allTypes(m3) );

@memo
rel[loc, loc] allFields(M3 m3) = { <t, f> | t <- allTypes(m3), f <- (containmentMap(m3))[t]? {}, isField(f) };

@memo
map[loc, set[loc]] allFieldsMap(M3 m3) = ( t : { f | f <- (containmentMap(m3))[t]? {}, isField(f) } | t <- allTypes(m3) );

@memo
map[loc, set[loc]] emptyMethodsMap(M3 m) = (me:{} | me <- methods(m));

@memo
map[loc, set[loc]] methodFieldAccesses(M3 m) = toMap(m@fieldAccess) - emptyMethodsMap(m);

@memo
map[loc, set[loc]] methodMethodCalls(M3 m) = toMap(m@methodInvocation) - emptyMethodsMap(m);

@memo
rel[loc, loc] transitiveContainment(M3 m) = m@containment+;

bool isType(loc l) = isClass(l) || isInterface(l) || l.scheme == "java+enum" || l.scheme == "java+anonymousClass";

@memo
rel[loc, loc] packageTypes(M3 m3) = { <p, t> | <p, t> <- transitiveContainment(m3), isPackage(p), isType(t) };

@memo
map[loc, set[Modifier]] modifiersMap(M3 m) = toMap(m@modifiers);

@memo
rel[loc, loc] overridableMethods(M3 m3) = { <p, m> | <p, m> <- allMethods(m3), ({\private(), \final(), \static()} & (modifiersMap(m3))[m]? {}) == {} };

@metric{A-Java}
@doc{Abstractness (Java)}
@friendlyName{Abstractness (Java)}
@appliesTo{java()}
@historic
real A_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);
  
  types = allTypes(m3);
  
  abstractTypes = { t | t <- types, \abstract() in ((modifiersMap(m3))[t]?{}) };
  
  return A(abstractTypes, types);
}

@metric{RR-Java}
@doc{Reuse ratio (Java)}
@friendlyName{Reuse ratio (Java)}
@appliesTo{java()}
@historic
real RR_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);

  return RR(superTypes(m3), allTypes(m3));
}

@metric{SR-Java}
@doc{Specialization ratio (Java)}
@friendlyName{Specialization ratio (Java)}
@appliesTo{java()}
@historic
real SR_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	return SR(superTypes(systemM3(m3s, delta = delta)));
}

@metric{DIT-Java}
@doc{Depth of inheritance tree (Java)}
@friendlyName{Depth of inheritance tree (Java)}
@appliesTo{java()}
map[loc, int] DIT_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);
  
  return DIT(superTypes(m3), allTypes(m3));
}

@metric{NOC-Java}
@doc{Number of children (Java)}
@friendlyName{Number of children (Java)}
@appliesTo{java()}
map[loc, int] NOC_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);
  
  return NOC(superTypes(m3), allTypes(m3));
}

private rel[loc, loc] typeSymbolsToTypeDependencies(rel[loc, TypeSymbol] typs) {
  return { *({l} * { t2.decl | /TypeSymbol t2 := t, t2 has decl, isType(t2.decl) }) |  <l, t> <- typs };
}

@metric{CBO-Java}
@doc{Coupling between objects (Java)}
@friendlyName{Coupling between objects (Java)}
@appliesTo{java()}
map[loc, int] CBO_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);
	
	return CBO(typeDependencies(m3), allTypes(m3));
}

@memo
private tuple[map[loc, int], map[loc, int]] dac_mpc(rel[Language, loc, AST] asts) {

	map[loc, int] dac = ();
	map[loc, int] mpc = ();

	for (/Declaration c <- asts[\java()], \class(_,_,_,_) := c || \class(_) := c) {
		dac[c@decl] = 0;
		mpc[c@decl] = 0;
	
		top-down-break visit (c) {
			case newObject(_, _): dac[c@decl] += 1;
			case newObject(_, _, _): dac[c@decl] += 1;
			case methodCall(_, _, _): mpc[c@decl] += 1;
			case methodCall(_, _, _, _): mpc[c@decl] += 1;
			case constructorCall(_, _): mpc[c@decl] += 1;
			case constructorCall(_, _, _): mpc[c@decl] += 1;
		}	
	}

	return <dac, mpc>;
}


@metric{DAC-Java}
@doc{Data abstraction coupling (Java)}
@friendlyName{Data abstraction coupling (Java)}
@appliesTo{java()}
// DAC for java is also measured in lang::java::style::Metrics
map[loc, int] DAC_Java(rel[Language, loc, AST] asts = {}) {
  return dac_mpc(asts)[0];
}

@metric{MPC-Java}
@doc{Message passing coupling (Java)}
@friendlyName{Message passing coupling (Java)}
@appliesTo{java()}
map[loc, int] MPC_Java(rel[Language, loc, AST] asts = {}) {
  return dac_mpc(asts)[1];
}

@metric{CF-Java}
@doc{Coupling factor (Java)}
@friendlyName{Coupling factor (Java)}
@appliesTo{java()}
@historic
real CF_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);
  typeDependenciesNoInherits = typeDependencies({}, m3@methodInvocation, m3@fieldAccess, typeSymbolsToTypeDependencies(m3@types), domainR(m3@containment+, allTypes(m3)), allTypes(m3));
  return CF(typeDependenciesNoInherits, allTypes(m3));
}

@metric{Ca-Java}
@doc{Afferent coupling (Java)}
@friendlyName{Afferent coupling (Java)}
@appliesTo{java()}
map[loc, int] Ca_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);
  return Ca(packageTypes(m3), typeDependencies(m3));
}

@metric{Ce-Java}
@doc{Efferent coupling (Java)}
@friendlyName{Efferent coupling (Java)}
@appliesTo{java()}
map[loc, int] Ce_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);
  return Ce(packageTypes(m3), typeDependencies(m3));
}

@metric{I-Java}
@doc{Instability (Java)}
@friendlyName{Instability (Java)}
@appliesTo{java()}
@uses = ("Ce-Java" : "ce", "Ca-Java" : "ca")
map[loc, real] I_Java(map[loc, int] ce = (), map[loc, int] ca = ()) {
  set[loc] packages = domain(ca) + domain(ce);

  return ( p : I(ca[p]?0, ce[p]?0) | p <- packages );
}

@metric{RFC-Java}
@doc{Response for class (Java)}
@friendlyName{Response for class (Java)}
@appliesTo{java()}
map[loc, int] RFC_Java(rel[Language, loc, M3] m3s = {}, ProjectDelta delta = ProjectDelta::\empty()) {
  M3 m3 = systemM3(m3s,delta=delta);
  return RFC(m3@methodInvocation, allMethodsMap(m3), allTypes(m3));
}

@metric{MIF-Java}
@doc{Method inheritance factor (Java)}
@friendlyName{Method inheritance factor (Java)}
@appliesTo{java()}
map[loc, real] MIF_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);

	// TODO package visibility?	
	inheritableMethods = { <t, m> | <t, m> <- allMethods(m3), ({\private(), \abstract()} & (modifiersMap(m3))[m]?{}) == {} };
	
	return MIF(allMethodsMap(m3), inheritableMethods, m3@extends, classes(m3));
}

@metric{AIF-Java}
@doc{Attribute inheritance factor (Java)}
@friendlyName{Attribute inheritance factor (Java)}
@appliesTo{java()}
map[loc, real] AIF_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);

	// TODO package visibility?	
	publicAndProtectedFields = { <t, f> | <t, f> <- allFields(m3), \private() notin ((modifiersMap(m3))[f]?{}) };
	
	return MIF(allFieldsMap(m3), publicAndProtectedFields, superTypes(m3), allTypes(m3));
}

@doc{
	Reuseable method for AHF and MHF
}
private real hidingFactor(M3 m3, rel[loc, loc] members) {
	set[loc] publicMembers = {};
	rel[loc, loc] protectedMembers = {};
	rel[loc, loc] packageVisibleMembers = {};
	
	for (<t, m> <- members) {
		mods = (modifiersMap(m3))[m]?{};
		if (\private() in mods) {
			; // ignored
		} else if (\protected() in mods) {
			protectedMembers += {<t, m>};
		} else if (\public() in mods) {
			publicMembers += {m};
		} else {
			packageVisibleMembers += {<t, m>};
		}
	}
	
	subTypes = invert(superTypes(m3))+;

	packageType = rangeR(domainR(m3@containment, packages(m3)), allTypes(m3));
	packageFriends = invert(packageType) o packageType;
	
	visible = allTypes(m3) * publicMembers
			+ { <s, m> | <t, m> <- protectedMembers, s <- subTypes[t] }
			+ { <f, m> | <t, m> <- packageVisibleMembers, f <- packageFriends[t] };

	return MHF(members, visible, allTypes(m3));
}

@metric{MHF-Java}
@doc{Method hiding factor (Java)}
@friendlyName{Method hiding factor (Java)}
@appliesTo{java()}
@historic
real MHF_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);
	return hidingFactor(m3, allMethods(m3));
}

@metric{AHF-Java}
@doc{Attribute hiding factor (Java)}
@friendlyName{Attribute hiding factor (Java)}
@appliesTo{java()}
@historic
real AHF_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);
	return hidingFactor(m3, allFields(m3));
}

@metric{PF-Java}
@doc{Polymorphism factor (Java)}
@friendlyName{Polymorphism factor (Java)}
@appliesTo{java()}
@historic
real PF_Java(rel[Language, loc, M3] m3s = {}, ProjectDelta delta = ProjectDelta::\empty()) {
	M3 m3 = systemM3(m3s,delta=delta);

	return PF(superTypes(m3), m3@methodOverrides, overridableMethods(m3), allTypes(m3));
}

@metric{LCOM-Java}
@doc{Lack of cohesion in methods (Java)}
@friendlyName{Lack of cohesion in methods (Java)}
@appliesTo{java()}
map[loc, int] LCOM_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);
	return LCOM(methodFieldAccesses(m3), allMethodsMap(m3), allFieldsMap(m3), allTypes(m3));
}

@metric{LCOM4-Java}
@doc{Lack of cohesion in methods 4 (Java)}
@friendlyName{Lack of cohesion in methods 4 (Java)}
@appliesTo{java()}
map[loc, int] LCOM4_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);
	return LCOM4(methodMethodCalls(m3), methodFieldAccesses(m3), allMethodsMap(m3), allFieldsMap(m3), allTypes(m3));
}

@metric{TCC-Java}
@doc{Tight class cohesion (Java)}
@friendlyName{Tight class cohesion (Java)}
@appliesTo{java()}
map[loc, real] TCC_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);
	return TCC(allMethodsMap(m3), allFieldsMap(m3), methodMethodCalls(m3), methodFieldAccesses(m3), allTypes(m3));
}

@metric{LCC-Java}
@doc{Loose class cohesion (Java)}
@friendlyName{Loose class cohesion (Java)}
@appliesTo{java()}
map[loc, real] LCC_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);
	return LCC(allMethodsMap(m3), allFieldsMap(m3), methodMethodCalls(m3), methodFieldAccesses(m3), allTypes(m3));
}


@metric{NOM-Java}
@doc{Number of methods (Java)}
@friendlyName{Number of methods (Java)}
@appliesTo{java()}
map[loc, int] NOM_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);
	return NOM(allMethodsMap(m3), allTypes(m3));
}

@metric{NOA-Java}
@doc{Number of attributes (Java)}
@friendlyName{Number of attributes (Java)}
@appliesTo{java()}
map[loc, int] NOA_Java(ProjectDelta delta = ProjectDelta::\empty(), rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s, delta = delta);
	return NOA(allFieldsMap(m3), allTypes(m3));
}


// -------- factoids


@metric{Coupling-Java}
@doc{Coupling between Java classes counts dependencies between class definitions. The higher the coupling between classes, the harder a system is to test and to maintain.}
@friendlyName{Java coupling}
@appliesTo{java()}
@uses{("CBO-Java": "cbo")}
Factoid Coupling_Java(map[loc, int] cbo = ()) {
	return Coupling("Java", cbo);
}


@metric{Cohesion-Java}
@doc{Java cohesion}
@friendlyName{Java cohesion}
@appliesTo{java()}
@uses{("LCOM4-Java": "lcom4")}
Factoid Cohesion_Java(map[loc, int] lcom4 = ()) {
	return Cohesion("Java", lcom4);
}


//------ derived metrics for visualisation


@metric{DIT-Java-Quartiles}
@doc{Depth of inheritance tree quartiles (Java)}
@friendlyName{Depth of inheritance tree quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("DIT-Java":"val")}
map[str, real] DIT_Java_Q(map[loc, int] val = ()) {
	return quartiles(val);
}

@metric{NOC-Java-Quartiles}
@doc{Number of children quartiles (Java)}
@friendlyName{Number of children quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("NOC-Java":"val")}
map[str, real] NOC_Java_Q(map[loc, int] val = ()) {
	return quartiles(val);
}

@metric{CBO-Java-Quartiles}
@doc{Coupling between objects quartiles (Java)}
@friendlyName{Coupling between objects quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("CBO-Java":"val")}
map[str, real] CBO_Java_Q(map[loc, int] val = ()) {
	return quartiles(val);
}


@metric{DAC-Java-Quartiles}
@doc{Data abstraction coupling quartiles (Java)}
@friendlyName{Data abstraction coupling quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("DAC-Java":"val")}
map[str, real] DAC_Java_Q(map[loc, int] val = ()) {
	return quartiles(val);
}

@metric{MPC-Java-Quartiles}
@doc{Message passing coupling quartiles (Java)}
@friendlyName{Message passing coupling quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("MPC-Java":"val")}
map[str, real] MPC_Java_Q(map[loc, int] val = ()) {
	return quartiles(val);
}

@metric{Ca-Java-Quartiles}
@doc{Afferent coupling quartiles (Java)}
@friendlyName{Afferent coupling quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("Ca-Java":"val")}
map[str, real] Ca_Java_Q(map[loc, int] val = ()) {
	return quartiles(val);
}

@metric{Ce-Java-Quartiles}
@doc{Efferent coupling quartiles (Java)}
@friendlyName{Efferent coupling quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("Ce-Java":"val")}
map[str, real] Ce_Java_Q(map[loc, int] val = ()) {
	return quartiles(val);
}

@metric{I-Java-Quartiles}
@doc{Instability quartiles (Java)}
@friendlyName{Instability quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("I-Java":"val")}
map[str, real] I_Java_Q(map[loc, real] val = ()) {
	return quartiles(val);
}

@metric{RFC-Java-Quartiles}
@doc{Response for class quartiles (Java)}
@friendlyName{Response for class quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("RFC-Java":"val")}
map[str, real] RFC_Java_Q(map[loc, int] val = ()) {
	return quartiles(val);
}

@metric{MIF-Java-Quartiles}
@doc{Method inheritance factor quartiles (Java)}
@friendlyName{Method inheritance factor quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("MIF-Java":"val")}
map[str, real] MIF_Java_Q(map[loc, real] val = ()) {
	return quartiles(val);
}

@metric{AIF-Java-Quartiles}
@doc{Attribute inheritance factor quartiles (Java)}
@friendlyName{Attribute inheritance factor quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("AIF-Java":"val")}
map[str, real] AIF_Java_Q(map[loc, real] val = ()) {
	return quartiles(val);
}


@metric{LCOM-Java-Quartiles}
@doc{Lack of cohesion in methods quartiles (Java)}
@friendlyName{Lack of cohesion in methods quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("LCOM-Java":"val")}
map[str, real] LCOM_Java_Q(map[loc, int] val = ()) {
	return quartiles(val);
}


@metric{LCOM4-Java-Quartiles}
@doc{Lack of cohesion in methods 4 quartiles (Java)}
@friendlyName{Lack of cohesion in methods 4 quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("LCOM4-Java":"val")}
map[str, real] LCOM4_Java_Q(map[loc, int] val = ()) {
	return quartiles(val);
}

@metric{TCC-Java-Quartiles}
@doc{Tight class cohesion quartiles (Java)}
@friendlyName{Tight class cohesion quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("TCC-Java":"val")}
map[str, real] TCC_Java_Q(map[loc, real] val = ()) {
	return quartiles(val);
}

@metric{LCC-Java-Quartiles}
@doc{Loose class cohesion quartiles (Java)}
@friendlyName{Loose class cohesion quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("LCC-Java":"val")}
map[str, real] LCC_Java_Q(map[loc, real] val = ()) {
	return quartiles(val);
}

@metric{NOM-Java-Quartiles}
@doc{Number of methods quartiles (Java)}
@friendlyName{Number of methods quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("NOM-Java":"val")}
map[str, real] NOM_Java_Q(map[loc, int] val = ()) {
	return quartiles(val);
}

@metric{NOA-Java-Quartiles}
@doc{Number of attributes quartiles (Java)}
@friendlyName{Number of attributes quartiles (Java)}
@appliesTo{java()}
@historic
@uses{("NOA-Java":"val")}
map[str, real] NOA_Java_Q(map[loc, int] val = ()) {
	return quartiles(val);
}
