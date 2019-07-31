module org::maracas::\test::delta::DetectorTest

import lang::java::m3::AST;
import org::maracas::delta::Delta;
import org::maracas::delta::Detector;
import org::maracas::config::Options;
import org::maracas::Maracas;

loc api0 = |project://maracas/src/org/maracas/test/data/minimalbc.1.0.jar|;
loc api1 = |project://maracas/src/org/maracas/test/data/minimalbc.1.1.jar|;
loc client = |project://maracas/src/org/maracas/test/data/minimalbc-client.1.0.jar|;

Delta delta = delta(api0, api1);
public Delta fdelta = fieldDelta(delta);
public Delta mdelta = methodDelta(delta);
public Delta cdelta = classDelta(delta);

public set[Detection] fd = detections(client, fdelta);
public set[Detection] md = detections(client, mdelta);
public set[Detection] cd = detections(client, cdelta);


//----------------------------------------------
// Changed access modifier tests
//----------------------------------------------
test bool fieldAccessModifiers() 
	= detection(
		client,
		|java+method:///client/ChangedAccessModifier/fieldChangedAccessModifier()|,
		|java+field:///p1/ChangedAccessModifier3/field3|,
		<\public(),\private(),1.0,MATCH_SIGNATURE>,
		accessModifiers())
	in fd;
    
test bool methodAccessModifiers() 
	= detection(
		client,
		|java+method:///client/ChangedAccessModifier/methodChangedAccessModifier()|,
	    |java+method:///p1/ChangedAccessModifier2/m1()|,
	    <\public(),\private(),1.0,MATCH_SIGNATURE>,
	    accessModifiers())
	in md;


//----------------------------------------------
// Changed final modifier tests
//----------------------------------------------
test bool fieldChangedFinalModifier() 
	= { detection(
			client,
		    |java+method:///client/ChangedFinalModifier/fieldChangedFinalModifier()|,
		    |java+field:///p1/ChangedFinalModifier3/field1|,
		    <\final(),\default(),1.0,MATCH_SIGNATURE>,
		    finalModifiers()),
		detection(
			client,
		    |java+method:///client/ChangedFinalModifier/fieldChangedFinalModifier()|,
		    |java+field:///p1/ChangedFinalModifier3/field2|,
		    <\default(),\final(),1.0,MATCH_SIGNATURE>,
		    finalModifiers()) }
    <= fd;

test bool methodChangedFinalModifier() 
	= { detection(
			client,
		    |java+method:///client/ChangedFinalModifier/methodChangedFinalModifier()|,
		    |java+method:///p1/ChangedFinalModifier2/m1()|,
		    <\default(),\final(),1.0,MATCH_SIGNATURE>,
		    finalModifiers()),
    	detection(
    		client,
		    |java+method:///client/ChangedFinalModifier/methodChangedFinalModifier()|,
		    |java+method:///p1/ChangedFinalModifier2/m2()|,
		    <\final(),\default(),1.0,MATCH_SIGNATURE>,
		    finalModifiers()),
  		detection(
  			client,
		    |java+method:///client/ChangedFinalModifier/methodChangedFinalModifier()|,
		    |java+method:///p1/ChangedFinalModifier2/m4()|,
		    <\default(),\final(),1.0,MATCH_SIGNATURE>,
		    finalModifiers()) }
	<= md;

test bool classChangedFinalModifier1() 
	= detection(
		client,
    	|java+field:///client/ChangedFinalModifier/classField|,
    	|java+class:///p1/ChangedFinalModifier1|,
    	<\default(),\final(),1.0,MATCH_SIGNATURE>,
    	finalModifiers())
    in cd;

test bool classChangedFinalModifier2() 
	= detection(
		client,
	    |java+method:///client/ChangedFinalModifier/classChangedFinalModifier()|,
	    |java+class:///p1/ChangedFinalModifier1|,
	    <\default(),\final(),1.0,MATCH_SIGNATURE>,
	    finalModifiers())
    in cd;
    

//----------------------------------------------
// Changed static modifier tests
//----------------------------------------------
test bool fieldStaticModifiers() 
	= { detection(
			client,
			|java+method:///client/ChangedStaticModifier/fieldChangedStaticModifier()|,
			|java+field:///p1/ChangedStaticModifier3/field1|,
			<\static(),\default(),1.0,MATCH_SIGNATURE>,
			staticModifiers()),
		detection(
			client,
			|java+method:///client/ChangedStaticModifier/fieldChangedStaticModifier()|,
			|java+field:///p1/ChangedStaticModifier3/field2|,
			<\default(),\static(),1.0,MATCH_SIGNATURE>,
			staticModifiers()) }
	<= fd;
    
test bool methodStaticModifiers() 
	= { detection(
			client,
			|java+method:///client/ChangedStaticModifier/methodChangedStaticModifier()|,
		    |java+method:///p1/ChangedStaticModifier2/m1()|,
		    <\default(),\static(),1.0,MATCH_SIGNATURE>,
		    staticModifiers()),
		detection(
			client,
			|java+method:///client/ChangedStaticModifier/methodChangedStaticModifier()|,
		    |java+method:///p1/ChangedStaticModifier2/m2()|,
		    <\static(),\default(),1.0,MATCH_SIGNATURE>,
		    staticModifiers()) }
	<= md;


//----------------------------------------------
// Changed abstract modifier tests
//----------------------------------------------
test bool methodAbstractModifiers() 
	= { detection(
			client,
		    |java+method:///client/ChangedAbstractModifier/methodChangedAccessModifier()|,
		    |java+method:///p1/ChangedAbstractModifier2/m2()|,
		    <\default(),\abstract(),1.0,MATCH_SIGNATURE>,
		    abstractModifiers()),
	  	detection(
	  		client,
		    |java+method:///client/ChangedAbstractModifier/methodChangedAccessModifier()|,
		    |java+method:///p1/ChangedAbstractModifier2/m1()|,
		    <\abstract(),\default(),1.0,MATCH_SIGNATURE>,
		    abstractModifiers()) }
    <= md;

test bool classAbstractModifiers() 
	= { detection(
			client,
			|java+field:///client/ChangedAbstractModifier/classField|,
		    |java+class:///p1/ChangedAbstractModifier1|,
		    <\default(),\abstract(),1.0,MATCH_SIGNATURE>,
		    abstractModifiers()),
		detection(
			client,
		    |java+method:///client/ChangedAbstractModifier/classChangedAccessModifier()|,
		    |java+class:///p1/ChangedAbstractModifier1|,
		    <\default(),\abstract(),1.0,MATCH_SIGNATURE>,
		    abstractModifiers()) }
    <= cd;
    
    
//----------------------------------------------
// Deprecated tests
//----------------------------------------------
test bool fieldDeprecated() 
	= { detection(
			client,
		    |java+method:///client/Deprecated/fieldDeprecated()|,
		    |java+field:///p2/Deprecated3/field1|,
		    <|java+field:///p2/Deprecated3/field1|,|java+field:///p2/Deprecated3/field1|,1.0,MATCH_SIGNATURE>,
		    deprecated()),
		detection(
			client,
		    |java+method:///client/Deprecated/fieldDeprecated()|,
		    |java+field:///p2/Deprecated3/field3|,
		    <|java+field:///p2/Deprecated3/field3|,|java+field:///p2/Deprecated3/field3|,1.0,MATCH_SIGNATURE>,
		    deprecated()) }
	<= fd;
	
test bool methodDeprecated() 
	= { detection(
			client,
		    |java+method:///client/Deprecated/methodDeprecated()|,
		    |java+method:///p2/Deprecated2/m1()|,
		    <|java+method:///p2/Deprecated2/m1()|,|java+method:///p2/Deprecated2/m1()|,1.0,MATCH_SIGNATURE>,
		    deprecated()), 
		detection(
			client,
		    |java+method:///client/Deprecated/methodDeprecated()|,
		    |java+method:///p2/Deprecated2/m3()|,
		    <|java+method:///p2/Deprecated2/m3()|,|java+method:///p2/Deprecated2/m3()|,1.0,MATCH_SIGNATURE>,
		    deprecated()) }
    <= md;

test bool classDeprecated()
	= { detection(
			client,
			|java+method:///client/Deprecated/classDeprecated()|,
		    |java+class:///p2/Deprecated1|,
		    <|java+class:///p2/Deprecated1|,|java+class:///p2/Deprecated1|,1.0,MATCH_SIGNATURE>,
		    deprecated()),
		detection(
			client,
		    |java+field:///client/Deprecated/classField|,
		    |java+class:///p2/Deprecated1|,
		    <|java+class:///p2/Deprecated1|,|java+class:///p2/Deprecated1|,1.0,MATCH_SIGNATURE>,
		    deprecated()) }
	<= cd;
	
	
//----------------------------------------------
// Renamed tests
//----------------------------------------------
test bool methodRenamed() 
	= isInDetections(
		|java+method:///client/Renamed/methodRenamed()|,
	    |java+method:///p2/Renamed2/m3(java.lang.String%5B%5D)|,
		renamed(),
		md
	);

test bool classRenamed1() 
	= isInDetections(
		|java+method:///client/Renamed/classRenamed()|,
		|java+class:///p2/Renamed1|,
		renamed(),
		cd
	);

test bool classRenamed2() 
	= isInDetections(
		|java+field:///client/Renamed/classField|,
		|java+class:///p2/Renamed1|,
		renamed(),
		cd
	);
	

//----------------------------------------------
// Moved tests
//----------------------------------------------
test bool methodMovedDueToRenamedClass1() 
	= isInDetections(
		|java+method:///client/Renamed/classRenamed()|,
		|java+method:///p2/Renamed1/getF2()|,
		moved(),
		md
	);

test bool methodMovedDueToRenamedClass2() 
	= isInDetections(
		|java+method:///client/Renamed/classRenamed()|,
		|java+method:///p2/Renamed1/isF3()|,
		moved(),
		md
	);

test bool methodMovedDueToRenamedClass3() 
	= isInDetections(
		|java+method:///client/Renamed/classRenamed()|,
		|java+method:///p2/Renamed1/getF1()|,
		moved(),
		md
	);

test bool methodMovedDueToMovedClass1() 
	= isInDetections(
		|java+method:///client/Moved/movedClass()|,
		|java+method:///p2/Moved1/getF3()|,
		moved(),
		md
	);

test bool methodMovedDueToMovedClass2() 
	= isInDetections(
		|java+method:///client/Moved/movedClass()|,
		|java+method:///p2/Moved1/getF4()|,
		moved(),
		md
	);

test bool methodMovedDueToMovedClass3() 
	= isInDetections(
		|java+method:///client/Moved/movedClass()|,
		|java+method:///p2/Moved1/getF5()|,
		moved(),
		md
	);

test bool methodMovedDueToMovedClass4() 
	= isInDetections(
		|java+method:///client/Moved/movedClass()|,
		|java+method:///p2/Moved1/getF6()|,
		moved(),
		md
	);  

test bool classMoved1() 
    = isInDetections(
		|java+method:///client/Moved/movedClass()|,
		|java+class:///p2/Moved1|,
		moved(),
		cd
	);  
	
test bool classMoved2() 
    = isInDetections(
		|java+field:///client/Moved/classField|,
		|java+class:///p2/Moved1|,
		moved(),
		cd
	);  
	
	
//----------------------------------------------
// Removed tests
//----------------------------------------------
test bool methodRemovedClassConstructor() 
	= detection(
		client,
	    |java+method:///client/Removed/classRemoved()|,
	    |java+constructor:///p2/Removed1/Removed1(boolean,boolean,int,int)|,
	    <|java+constructor:///p2/Removed1/Removed1(boolean,boolean,int,int)|,|unknown:///|,1.0,MATCH_SIGNATURE>,
	    removed())
    in md;

test bool methodRemoved()
	= detection(
		client,
	    |java+method:///client/Removed/methodRemoved()|,
	    |java+method:///p2/Removed2/populateMatrices()|,
	    <|java+method:///p2/Removed2/populateMatrices()|,|unknown:///|,1.0,MATCH_SIGNATURE>,
	    removed())
    in md;

test bool classRemoved() 
	= { detection(
			client,
		    |java+method:///client/Removed/classRemoved()|,
		    |java+class:///p2/Removed1|,
		    <|java+class:///p2/Removed1|,|unknown:///|,1.0,MATCH_SIGNATURE>,
		    removed()),
		detection(
			client,
		    |java+field:///client/Removed/classField|,
		    |java+class:///p2/Removed1|,
		    <|java+class:///p2/Removed1|,|unknown:///|,1.0,MATCH_SIGNATURE>,
		    removed()) }
    <= cd;


//----------------------------------------------
// isInDetections
//----------------------------------------------

set[Detection] basicDetections = { 
	detection(
		client,
	    |java+method:///path/to/a()|,
	    |java+class:///path/to/A|,
	    <|java+class:///path/to/A|,|unknown:///|,1.0,MATCH_SIGNATURE>,
	    removed()),
	detection(
		client,
	    |java+field:///path/to/a|,
	    |java+class:///path/to/A|,
	    <|java+class:///path/to/A|,|java+class:///path/to/Ar|,0.7,MATCH_LEVENSHTEIN>,
	    moved()),
	detection(
		client,
	    |java+method:///path/to/a|,
	    |java+method:///path/to/b()|,
	    <|java+method:///path/to/b()|,|java+method:///path/to/br()|,0.7,MATCH_JACCARD>,
	    renamed()) };
	    
test bool isInDetections1()
	= isInDetections(
		|java+method:///path/to/a()|,
		|java+class:///path/to/A|,
		removed(),
		basicDetections
	) == true;
	
test bool isInDetections2()
	= isInDetections(
		|java+field:///path/to/a|,
	    |java+class:///path/to/A|,
		moved(),
		basicDetections
	) == true;
	
test bool isInDetections3()
	= isInDetections(
		|java+method:///path/to/a|,
	    |java+method:///path/to/b()|,
		renamed(),
		basicDetections
	) == true;
	
test bool isNotInDetections1()
	= isInDetections(
		|java+method:///path/to/a()|,
		|java+class:///path/to/A|,
		moved(),
		basicDetections
	) == false;
	
test bool isNotInDetections2()
	= isInDetections(
		|java+field:///path/to/a|,
	    |java+class:///path/to/B|,
		moved(),
		basicDetections
	) == false;
	
test bool isNotInDetections3()
	= isInDetections(
		|java+field:///path/to/a|,
	    |java+method:///path/to/b()|,
		renamed(),
		basicDetections
	) == false;
