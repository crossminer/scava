module org::maracas::\test::util::MathTest

import org::maracas::util::Math;


// max
test bool max1() = max([1,2,3,4,5,6,7,8,9,10]) == 10;
test bool max2() = max([1000,500,250,125]) == 1000;
test bool max3() = max([1,3,5,7,9,2,4,6,8]) == 9;
test bool max4() = max([1,1,2,1,2,2]) == 2;
test bool max5() { 
	try {
		max([]);
		return false;
	} 
	catch : return true;
}

