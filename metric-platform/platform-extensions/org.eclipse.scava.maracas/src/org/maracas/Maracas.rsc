module org::maracas::Maracas

import lang::java::m3::Core;
import org::maracas::delta::Delta;
import org::maracas::delta::DeltaBuilder;
import org::maracas::delta::Detector;
import org::maracas::m3::Core;


Delta delta(loc oldAPI, loc newAPI) 
	= createDelta(m3(oldAPI), m3(newAPI));

Delta deltaFromM3(M3 oldAPI, M3 newAPI)
	= createDelta(oldAPI, newAPI);

Delta classDelta(Delta delta)
	= getClassDelta(delta);

Delta methodDelta(Delta delta)
	= getMethodDelta(delta);
	
Delta fieldDelta(Delta delta)
	= getFieldDelta(delta);
	
set[Detection] detections(loc client, Delta delta) 
	= detections(m3(client), delta);