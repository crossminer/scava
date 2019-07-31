module org::maracas::delta::stats::Core

import org::maracas::delta::Detector;

import Node;


map[str, int] getDeltaTypesMap() {
	set[DeltaType] types = getDeltaTypes();
	return ( getName(t) : 0 | t <- types);
}

private set[DeltaType] getDeltaTypes()
	= {
		accessModifiers(),
		finalModifiers(),
		staticModifiers(),
		abstractModifiers(),
		paramLists(),
		types(),
		extends(),
		implements(),
		deprecated(),
		renamed(),
		moved(),
		removed(),
		added()
	};