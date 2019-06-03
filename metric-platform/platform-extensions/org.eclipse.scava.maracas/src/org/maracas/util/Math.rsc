module org::maracas::util::Math

import List;
import util::Math;


// TODO: Consider moving to the util::Math module.
int max(list[int] integers) {
	if (isEmpty(integers)) {
		throw "The list is empty."; 
	}
	
	global = 0;
	for (i <- integers) {
		global = max(global, i);
	}
	return global;
}