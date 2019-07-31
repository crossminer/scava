module org::maracas::\test::delta::TestApiOldApiNew

import Set;
import org::maracas::m3::Core;
import lang::java::m3::AST;
import lang::java::m3::TypeSymbol;
import org::maracas::delta::Delta;
import org::maracas::Maracas;
import org::maracas::config::Options;
import org::maracas::delta::Detector;
import util::ValueUI;

// Assuming they have been imported and built beforehand
loc v1 =     |project://api-old/target/old-0.0.1-SNAPSHOT.jar|;
loc v2 =     |project://api-new/target/new-0.0.1-SNAPSHOT.jar|;
loc client = |project://client/target/client-0.0.1-SNAPSHOT.jar|;

Delta delta = delta(v1, v2);
Delta fdelta = fieldDelta(delta);
Delta mdelta = methodDelta(delta);
Delta cdelta = classDelta(delta);

set[Detection] ds = detections(createM3(client), breakingChanges(delta));

//------------------------
//        DELTA
//------------------------

// final api.FinalModifierRemoved -> api.FinalModifierRemoved
test bool classFinalModifierRemoved() =
	<|java+class:///api/FinalModifierRemoved|, <\final(), \default(), 1.0, MATCH_SIGNATURE>>
	in cdelta.finalModifiers;

// api.FinalModifierAdded -> final api.FinalModifierAdded
test bool classFinalModifierAdded() =
	<|java+class:///api/FinalModifierAdded|, <\default(), \final(), 1.0, MATCH_SIGNATURE>>
	in cdelta.finalModifiers;

test bool classNoMoreFinalModifiers() =
	size(cdelta.finalModifiers) == 2;

// abstract api.AbstractModifierRemoved -> api.AbstractModifierRemoved
test bool classAbstractModifierRemoved() =
	<|java+class:///api/AbstractModifierRemoved|, <\abstract(), \default(), 1.0, MATCH_SIGNATURE>>
	in cdelta.abstractModifiers
	&& size(cdelta.abstractModifiers) == 2;

// api.AbstractModifierAdded -> abstract api.AbstractModifierAdded
test bool classAbstractModifierAdded() =
	<|java+class:///api/AbstractModifierAdded|, <\default(), \abstract(), 1.0, MATCH_SIGNATURE>>
	in cdelta.abstractModifiers
	&& size(cdelta.abstractModifiers) == 2;

test bool classNoMoreAbstractModifiers() =
	size(cdelta.abstractModifiers) == 2;

// public api.AccessModifierRemoved -> api.AccessModifierRemoved
test bool classAccessModifierRemoved() =
	<|java+class:///api/AccessModifierRemoved|, <\public(), \defaultAccess(), 1.0, MATCH_SIGNATURE>>
	in cdelta.accessModifiers;

// api.AccessModifierAdded -> public api.AccessModifierAdded
test bool classAccessModifierRemoved() =
	<|java+class:///api/AccessModifierAdded|, <\defaultAccess(), \public(), 1.0, MATCH_SIGNATURE>>
	in cdelta.accessModifiers;

// interface api.InterfaceAccessModifierAdded -> public interface api.InterfaceAccessModifierAdded
test bool interfaceAccessModifierAdded() =
	<|java+interface:///api/InterfaceAccessModifierAdded|, <\defaultAccess(), \public(), 1.0, MATCH_SIGNATURE>>
	in cdelta.accessModifiers;

// public interface api.InterfaceAccessModifierRemoved-> interface api.InterfaceAccessModifierRemoved
test bool interfaceAccessModifierRemoved() =
	<|java+interface:///api/InterfaceAccessModifierRemoved|, <\public(), \defaultAccess(), 1.0, MATCH_SIGNATURE>>
	in cdelta.accessModifiers;

test bool classNoMoreAccessModifiers() =
	size(cdelta.accessModifiers) == 4;

// api.DeprecatedAdded -> @Deprecated api.DeprecatedAdded
test bool classDeprecated() =
	<|java+class:///api/DeprecatedAdded|, <|java+class:///api/DeprecatedAdded|, |java+class:///api/DeprecatedAdded|, 1.0, MATCH_SIGNATURE>>
	in cdelta.deprecated;

test bool classNoMoreDeprecated() =
	size(cdelta.deprecated) == 1;

// api.AccessModifierAdded.AccessModifierAdded() -> public api.AccessModifierAdded.AccessModifierAdded()
test bool constructorAccessModifierAdded() =
	<|java+constructor:///api/AccessModifierAdded/AccessModifierAdded()|, <\defaultAccess(), \public(), 1.0, MATCH_SIGNATURE>>
	in mdelta.accessModifiers;

// api.AccessModifierRemoved.AccessModifierRemoved() -> public api.AccessModifierRemoved.AccessModifierRemoved()
test bool constructorAccessModifierRemoved() =
	<|java+constructor:///api/AccessModifierRemoved/AccessModifierRemoved()|, <\public(), \defaultAccess(), 1.0, MATCH_SIGNATURE>>
	in mdelta.accessModifiers;

// private api.A.mAccessModifierPrivateToPublic() -> public api.A.mAccessModifierPrivateToPublic()
test bool methodAccessModifierPrivateToPublic() =
	<|java+method:///api/A/mAccessModifierPrivateToPublic()|, <\private(), \public(), 1.0, MATCH_SIGNATURE>>
	in mdelta.accessModifiers;

// public api.A.mAccessModifierPublicToPrivate() -> private api.A.mAccessModifierPublicToPrivate()
test bool methodAccessModifierPublicToPrivate() =
	<|java+method:///api/A/mAccessModifierPublicToPrivate()|, <\public(), \private(), 1.0, MATCH_SIGNATURE>>
	in mdelta.accessModifiers;

// api.A.mAccessModifierDefaultToPrivate() -> private api.A.mAccessModifierDefaultToPrivate()
test bool methodAccessModifierDefaultToPrivate() =
	<|java+method:///api/A/mAccessModifierDefaultToPrivate()|, <\defaultAccess(), \private(), 1.0, MATCH_SIGNATURE>>
	in mdelta.accessModifiers;

// api.A.mAccessModifierDefaultToPublic() -> public api.A.mAccessModifierDefaultToPublic()
test bool methodAccessModifierDefaultToPublic() =
	<|java+method:///api/A/mAccessModifierDefaultToPublic()|, <\defaultAccess(), \public(), 1.0, MATCH_SIGNATURE>>
	in mdelta.accessModifiers;

// private api.A.mAccessModifierPrivateToDefault() -> api.A.mAccessModifierPrivateToDefault()
test bool methodAccessModifierPrivateToDefault() =
	<|java+method:///api/A/mAccessModifierPrivateToDefault()|, <\private(), \defaultAccess(), 1.0, MATCH_SIGNATURE>>
	in mdelta.accessModifiers;

// public api.A.mAccessModifierPublicToDefault() -> api.A.mAccessModifierPublicToDefault()
test bool methodAccessModifierPublicToDefault() =
	<|java+method:///api/A/mAccessModifierPublicToDefault()|, <\public(), \defaultAccess(), 1.0, MATCH_SIGNATURE>>
	in mdelta.accessModifiers;

test bool methodNoMoreAccessModifiers() =
	size(mdelta.accessModifiers) == 8;

// final api.A.mFinalModifierRemoved -> api.A.mFinalModifierRemoved
test bool methodFinalModifierRemoved() =
	<|java+method:///api/A/mFinalModifierRemoved()|, <\final(), \default(), 1.0, MATCH_SIGNATURE>>
	in mdelta.finalModifiers;

// api.A.mFinalModifierAdded -> final api.A.mFinalModifierAdded
test bool methodFinalModifierAdded() =
	<|java+method:///api/A/mFinalModifierAdded()|, <\default(), \final(), 1.0, MATCH_SIGNATURE>>
	in mdelta.finalModifiers;

test bool methodNoMoreFinalModifier() =
	size(mdelta.finalModifiers) == 2;

// static api.A.mStaticModifierRemoved -> api.A.mStaticModifierRemoved
test bool methodStaticModifierRemoved() =
	<|java+method:///api/A/mStaticModifierRemoved()|, <\static(), \default(), 1.0, MATCH_SIGNATURE>>
	in mdelta.staticModifiers;

// api.A.mStaticModifierAdded -> static api.A.mStaticModifierAdded
test bool methodStaticModifierAdded() =
	<|java+method:///api/A/mStaticModifierAdded()|, <\default(), \static(), 1.0, MATCH_SIGNATURE>>
	in mdelta.staticModifiers;

test bool methodNoMoreFinalModifier() =
	size(mdelta.staticModifiers) == 2;

// api.AbstractModifierAdded.foo() -> abstract api.AbstractModifierAdded.foo()
test bool methodAbstractModifierAdded() =
	<|java+method:///api/AbstractModifierAdded/mAbstractModifier()|, <\default(), \abstract(), 1.0, MATCH_SIGNATURE>>
	in mdelta.abstractModifiers;

// abstract api.AbstractModifierRemoved.foo() -> api.AbstractModifierRemoved.foo()
test bool methodAbstractModifierRemoved() =
	<|java+method:///api/AbstractModifierRemoved/foo()|, <\abstract(), \default(), 1.0, MATCH_SIGNATURE>>
	in mdelta.abstractModifiers;

test bool methodNoMoreAbstractModifier() =
	size(mdelta.abstractModifiers) == 2;

// api.A.mParameterRemoved(int a, int b) -> api.A.mParameterRemoved(int a)
test bool methodParameterRemoved() =
	<|java+method:///api/A/mParameterRemoved(int,int)|, <[TypeSymbol::\int(), TypeSymbol::\int()], [TypeSymbol::\int()], 1.0, "signature">>
	in mdelta.paramLists;

// api.A.mParameterAdded(int a) -> api.A.mParameterAdded(int a, int b)
test bool methodParameterAdded() =
	<|java+method:///api/A/mParameterAdded(int)|, <[TypeSymbol::\int()], [TypeSymbol::\int(), TypeSymbol::\int()], 1.0, "signature">>
	in mdelta.paramLists;

test bool noMoreParamChanged() =
	size(mdelta.paramLists) == 2;

// String api.A.mChangedType(int a) -> int api.A.mChangedType(int a)
test bool methodChangedType() =
	<|java+method:///api/A/mChangedType(int)|, <TypeSymbol::\class(|java+class:///java/lang/String|, []), TypeSymbol::\int(), 1.0, "signature">>
	in mdelta.types;

test bool methodNoMoreChangedType() =
	size(mdelta.types) == 1;

// api.A.mDeprecated -> @Deprecated api.A.mDeprecated
test bool methodDeprecated() =
	<|java+method:///api/A/mDeprecated()|, <|java+method:///api/A/mDeprecated()|, |java+method:///api/A/mDeprecated()|, 1.0, MATCH_SIGNATURE>>
	in mdelta.deprecated;

test bool methodNoMoreDeprecated() =
	size(mdelta.deprecated) == 1;

// public api.A.fPublicToPrivate -> private api.A.fPublicToPrivate
test bool fieldPublicToPrivate() =
	<|java+field:///api/A/fPublicToPrivate|, <\public(), \private(), 1.0, MATCH_SIGNATURE>>
	in fdelta.accessModifiers;

// api.A.fDefaultToPrivate -> private api.A.fDefaultToPrivate
test bool fieldDefaultToPrivate() =
	<|java+field:///api/A/fDefaultToPrivate|, <\defaultAccess(), \private(), 1.0, MATCH_SIGNATURE>>
	in fdelta.accessModifiers;

// public api.A.fPublicToDefault -> api.A.fPublicToDefault
test bool fieldPublicToDefault() =
	<|java+field:///api/A/fPublicToDefault|, <\public(), \defaultAccess(), 1.0, MATCH_SIGNATURE>>
	in fdelta.accessModifiers;

test bool fieldNoMoreAccessModifiers() =
	size(fdelta.accessModifiers) == 3;

// String api.A.fFinalModifierAdded -> final String api.A.fFinalModifierAdded
test bool fieldFinalModifierAdded() =
	<|java+field:///api/A/fFinalModifierAdded|, <\default(), \final(), 1.0, MATCH_SIGNATURE>>
	in fdelta.finalModifiers;

// final String api.A.fFinalModifierRemoved -> String api.A.fFinalModifierRemoved
test bool fieldFinalModifierRemoved() =
	<|java+field:///api/A/fFinalModifierRemoved|, <\final(), \default(), 1.0, MATCH_SIGNATURE>>
	in fdelta.finalModifiers;

test bool fieldNoMoreFinalModifiers() =
	size(fdelta.finalModifiers) == 2;

// float api.A.fStaticModifierAdded -> static float api.A.fStaticModifierAdded
test bool fieldStaticModifierAdded() =
	<|java+field:///api/A/fStaticModifierAdded|, <\default(), \static(), 1.0, MATCH_SIGNATURE>>
	in fdelta.staticModifiers;

// static float api.A.fStaticModifierRemoved -> float api.A.fStaticModifierRemoved
test bool fieldStaticModifierRemoved() =
	<|java+field:///api/A/fStaticModifierRemoved|, <\static(), \default(), 1.0, MATCH_SIGNATURE>>
	in fdelta.staticModifiers;

test bool fieldNoMoreStaticModifiers() =
	size(fdelta.staticModifiers) == 2;

// int api.A.fDeprecated -> @Deprecated int api.A.fDeprecated
test bool fieldDeprecated() =
	<|java+field:///api/A/fDeprecated|, <|java+field:///api/A/fDeprecated|, |java+field:///api/A/fDeprecated|, 1.0, MATCH_SIGNATURE>>
	in fdelta.deprecated;

test bool fieldNoMoreDeprecated() =
	size(fdelta.deprecated) == 1;

// String api.A.fStringToInt -> int api.A.fStringToInt
test bool fieldStringToInt() =
	<|java+field:///api/A/fStringToInt|, <TypeSymbol::\class(|java+class:///java/lang/String|, []), TypeSymbol::\int(), 1.0, MATCH_SIGNATURE>>
	in fdelta.types;

// String api.A.fStringToList -> List<String> api.A.fStringToList
// FIXME: Fix when we fix the bug java+class:// -> java+interface:/
test bool fieldStringToList() =
	<|java+field:///api/A/fStringToList|, <TypeSymbol::\class(|java+class:///java/lang/String|, []), TypeSymbol::\class(|java+class:///java/util/List|, []), 1.0, MATCH_SIGNATURE>>
	in fdelta.types;

// RateLimiter api.A.fLimiterToGuard -> Guard api.A.fLimiterToGuard
test bool fieldExternalTypeToExternalType() =
	<|java+field:///api/A/fLimiterToGuard|, <TypeSymbol::\class(|java+class:///com/google/common/util/concurrent/RateLimiter|, []), TypeSymbol::\class(|java+class:///com/google/common/util/concurrent/Monitor$Guard|, []), 1.0, MATCH_SIGNATURE>>
	in fdelta.types;

test bool fieldNoMoreChangedType() =
	size(fdelta.types) == 3;

// api.ClassExtendsRemoved extends api.A -> api.ClassExtendsRemoved
test bool classExtendsRemoved() =
	<|java+class:///api/ClassExtendsRemoved|, <|java+class:///api/A|, |unknown:///|, 1.0, MATCH_SIGNATURE>>
	in cdelta.extends;

// api.ClassExtendsAdded -> api.ClassExtendsAdded extends api.A
test bool classExtendsAdded() =
	<|java+class:///api/ClassExtendsAdded|, <|unknown:///|, |java+class:///api/A|, 1.0, MATCH_SIGNATURE>>
	in cdelta.extends;

// api.ClassExtendsChanged extends api.ClassExtendsAdded -> api.ClassExtendsChanged extends api.ClassExtendsRemoved
test bool classExtendsChanged() =
	<|java+class:///api/ClassExtendsChanged|, <|java+class:///api/ClassExtendsAdded|, |java+class:///api/ClassExtendsRemoved|, 1.0, MATCH_SIGNATURE>>
	in cdelta.extends;

test bool classNoMoreExtends() =
	size(cdelta.extends) == 3;

// api.ClassImplementsAdded -> api.ClassImplementsAdded implements InterfaceExtendsRemoved
test bool classImplementsAdded() =
	<|java+class:///api/ClassImplementsAdded|, <{}, {|java+interface:///api/InterfaceExtendsRemoved|}, 1.0, MATCH_SIGNATURE>>
	in cdelta.implements;

// api.ClassImplementsRemoved implements api.InterfaceExtendsAdded -> api.ClassImplementsRemoved
test bool classImplementsRemoved() =
	<|java+class:///api/ClassImplementsRemoved|, <{|java+interface:///api/InterfaceExtendsAdded|}, {}, 1.0, MATCH_SIGNATURE>>
	in cdelta.implements;

// api.ClassImplementsChanged -> api.ClassImplementsChanged
test bool classImplementsChanged() =
	<|java+class:///api/ClassImplementsChanged|, <{|java+interface:///api/InterfaceAccessModifierAdded|}, {|java+interface:///api/InterfaceExtendsAdded|}, 1.0, MATCH_SIGNATURE>>
	in cdelta.implements;

// api.InterfaceExtendsAdded -> api.InterfaceExtendsAdded extends api.InterfaceExtendsRemoved
test bool interfaceExtendsAdded() =
	<|java+interface:///api/InterfaceExtendsAdded|, <{}, {|java+interface:///api/InterfaceExtendsRemoved|}, 1.0, MATCH_SIGNATURE>>
	in cdelta.implements;

// api.InterfaceExtendsRemoved extends api.InterfaceExtendsAdded -> api.InterfaceExtendsRemoved
test bool interfaceExtendsRemoved() =
	<|java+interface:///api/InterfaceExtendsRemoved|, <{|java+interface:///api/InterfaceExtendsAdded|}, {}, 1.0, MATCH_SIGNATURE>>
	in cdelta.implements;

test bool noMoreImplements() =
	size(cdelta.implements) == 5;

//------------------------
//      DETECTIONS
//------------------------

test bool dFieldPublicToPrivate() =
	detection(
		client,
	    |java+method:///client/AClient/fields()|,
	    |java+field:///api/A/fPublicToPrivate|,
	    <\public(), \private(), 1.0, "signature">,
	    accessModifiers()
	) in ds;

test bool dFieldPublicToDefault() =
	detection(
		client,
	    |java+method:///client/AClient/fields()|,
	    |java+field:///api/A/fPublicToDefault|,
	    <\public(), \defaultAccess(), 1.0, "signature">,
	    accessModifiers()
	) in ds;

test bool dMethodPublicToDefault() =
	detection(
		client,
		|java+method:///client/AClient/methods()|,
		|java+method:///api/A/mAccessModifierPublicToDefault()|,
		<\public(), \defaultAccess(), 1.0, "signature">,
		accessModifiers()
	) in ds;

test bool dMethodPublicToPrivate() =
	detection(
		client,
		|java+method:///client/AClient/methods()|,
		|java+method:///api/A/mAccessModifierPublicToPrivate()|,
		<\public(), \private(), 1.0, "signature">,
		accessModifiers()
	) in ds;

test bool dClassPublicToDefault() =
	detection(
		client,
		|java+field:///client/AClient/amr|,
		|java+class:///api/AccessModifierRemoved|,
		<\public(), \defaultAccess(), 1.0, MATCH_SIGNATURE>,
		accessModifiers()
	) in ds;

test bool dNoMoreAccessModifiers() =
	size(filterD(ds, accessModifiers())) == 5;

test bool dClassFinalModifierAdded() =
	detection(
		client,
		|java+class:///client/ClientFinalModifierAdded|,
		|java+class:///api/FinalModifierAdded|,
		<\default(), \final(), 1.0, "signature">,
		finalModifiers()
	) in ds;

test bool dMethodFinalModifierAdded() =
	detection(
		client,
		|java+method:///client/MethodFinalModifierAdded/mFinalModifierAdded()|,
		|java+method:///api/A/mFinalModifierAdded()|,
		<\default(), \final(), 1.0, "signature">,
		finalModifiers()
	) in ds;

test bool dNoMoreFinalModifiers() =
	size(filterD(ds, finalModifiers())) == 2;

test bool dFieldStaticModifierRemoved() =
	detection(
		client,
		|java+method:///client/AClient/fields()|,
		|java+field:///api/A/fStaticModifierRemoved|,
		<\static(), \default(), 1.0, MATCH_SIGNATURE>,
		staticModifiers()
	) in ds;

test bool dMethodStaticModifierRemoved() =
	detection(
		client,
		|java+method:///client/AClient/methods()|,
		|java+method:///api/A/mStaticModifierRemoved()|,
		<\static(), \default(), 1.0, MATCH_SIGNATURE>,
		staticModifiers()
	) in ds;

test bool dNoMoreStaticModifiers() =
	size(filterD(ds, staticModifiers())) == 2;

test bool dMethodAbstractModifierAdded() =
	detection(
		client,
		|java+method:///client/AClient/abstractCall()|,
		|java+method:///api/AbstractModifierAdded/mAbstractModifier()|,
		<\default(), \abstract(), 1.0, MATCH_SIGNATURE>,
		abstractModifiers()
	) in ds;

test bool dClassAbstractModifierAdded() =
	detection(
		client,
		|java+method:///client/AClient/abstractCall()|,
		|java+class:///api/AbstractModifierAdded|,
		<\default(), \abstract(), 1.0, MATCH_SIGNATURE>,
		abstractModifiers()
	) in ds;

test bool dNoMoreAbstractModifiers() =
	size(filterD(ds, abstractModifiers())) == 2;


test bool dMethodParameterAdded() =
	detection(
		client,
		|java+method:///client/AClient/methods()|,
		|java+method:///api/A/mParameterAdded(int)|,
		<[TypeSymbol::\int()], [TypeSymbol::\int(), TypeSymbol::\int()], 1.0, "signature">,
		paramLists()
	) in ds;

test bool dMethodParameterRemoved() =
	detection(
		client,
		|java+method:///client/AClient/methods()|,
		|java+method:///api/A/mParameterRemoved(int,int)|,
		<[TypeSymbol::\int(), TypeSymbol::\int()], [TypeSymbol::\int()], 1.0, "signature">,
		paramLists()
	) in ds;

test bool dNoMoreParamLists() =
	size(filterD(ds, paramLists())) == 2;

test bool dFieldStringToInt() =
	detection(
		client,
	    |java+method:///client/AClient/fields()|,
	    |java+field:///api/A/fStringToInt|,
	    <class(|java+class:///java/lang/String|, []), TypeSymbol::\int(), 1.0, "signature">,
	    types()
	) in ds;

test bool dFieldStringToList() =
	detection(
		client,
	    |java+method:///client/AClient/fields()|,
	    |java+field:///api/A/fStringToList|,
	    <class(|java+class:///java/lang/String|, []), class(|java+class:///java/util/List|, []), 1.0, "signature">,
	    types()
	) in ds;

test bool dMethodChangedType() =
	detection(
		client,
		|java+method:///client/AClient/methods()|,
		|java+method:///api/A/mChangedType(int)|,
		<class(|java+class:///java/lang/String|, []), TypeSymbol::\int(), 1.0, "signature">,
		types()
	) in ds;

test bool dNoMoreTypes() =
	size(filterD(ds, types())) == 3;

test bool dFieldDeprecated() =
	detection(
		client,
	    |java+method:///client/AClient/fields()|,
	    |java+field:///api/A/fDeprecated|,
	    <|java+field:///api/A/fDeprecated|, |java+field:///api/A/fDeprecated|, 1.0, "signature">,
	    deprecated()
	) in ds;

test bool dMethodDeprecated() =
	detection(
		client,
		|java+method:///client/AClient/methods()|,
		|java+method:///api/A/mDeprecated()|,
		<|java+method:///api/A/mDeprecated()|, |java+method:///api/A/mDeprecated()|, 1.0, "signature">,
		deprecated()
	) in ds;

test bool dClassDeprecated() =
	detection(
		client,
		|java+field:///client/AClient/da|,
		|java+class:///api/DeprecatedAdded|,
		<|java+class:///api/DeprecatedAdded|, |java+class:///api/DeprecatedAdded|, 1.0, MATCH_SIGNATURE>,
		deprecated()
	) in ds;

// Because of final field inlining, there is no fieldAccess detected for it
//test bool dFieldFinalModifierRemoved() = 
//	detection(
//	    |java+method:///client/AClient/fields()|,
//	    |java+field:///api/A/fFinalModifierRemoved|,
//	    <\final(), \default(), 1.0, "signature">,
//	    finalModifiers()
//	) in ds;

test bool dMethodMoved() = // Just checking the best candidate, but there might be others
	detection(
		client,
		|java+method:///client/AClient/methods()|,
		|java+method:///api/A/mMoved()|,
		<|java+method:///api/A/mMoved()|, |java+method:///api/C/mMoved()|, 1.0, "levenshtein">,
		moved()
	) in ds;

test bool dMethodRenamed() = // Just checking the best candidate, but there might be others
	detection(
		client,
		|java+method:///client/AClient/methods()|,
		|java+method:///api/A/mRenamed()|,
		<|java+method:///api/A/mRenamed()|, |java+method:///api/A/mRenamed2()|, 0.9971509971509972, "levenshtein">,
		renamed()
	) in ds;

test bool dHollywoodClass() =
	detection(
		client,
		|java+class:///client/HollywoodClassClient|,
		|java+class:///api/HollywoodClass|,
		<|unknown:///|, |java+method:///api/HollywoodClass/bar()|, 1.0, "signature">,
		added()
	) in ds;

test bool dHolywoodInterface() =
	detection(
		client,
		|java+class:///client/HollywoodInterfaceClient|,
		|java+interface:///api/HollywoodInterface|,
		<|unknown:///|, |java+method:///api/HollywoodInterface/bar()|, 1.0, "signature">,
		added()
	) in ds;

private set[Detection] filterD(set[Detection] ds, DeltaType typ) =
	{d | d <- ds, d.typ == typ};

test bool print() {
	text(ds);
	return true;
}