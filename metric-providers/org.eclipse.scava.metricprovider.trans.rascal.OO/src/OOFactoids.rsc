@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module OOFactoids

import Prelude;
import util::Math;

import org::eclipse::scava::metricprovider::MetricProvider;


private tuple[int, str] metricsWithinRange(lrel[num result, str label, real min, real max] tests) {
	ok = 0;
	txt = "";
	
	for (<r, l, mn, mx> <- tests) {
		txt += "<label>: <r>";
		if (r >= mn && r <= mx) {
			ok += 1;
		} else {
			txt += " (!)";
		}
		txt += "\n";	
	}

	return <ok, txt>;
}

private tuple[int, str] mapMetricsWithinRange(lrel[map[loc, num] result, str label, real min, real max] tests) {
	ok = 0;
	txt = "";
	
	for (<r, l, mn, mx> <- tests) {
		txt += "<label>: <r>";
		
		a = 0;  // TODO gini? or specific per metric?
		
		if (a >= mn && a <= mx) {
			ok += 1;
		} else {
			txt += " (!)";
		}
		txt += "\n";	
	}

	return <ok, txt>;
}


Factoid Coupling(
	str language,
	map[loc, int] cbo
) {
	if (cbo == ()) {
		throw undefined("No CBO data", |file:///|);
	}
	
	// CBO > 14 is too high according to:
	// Houari A. Sahraoui, Robert Godin, Thierry Miceli: Can Metrics Help Bridging the Gap Between the Improvement of OO Design Quality and Its Automation?
	
	threshold = 14;
	
	numClassesWithBadCoupling = ( 0 | it + 1 | c <- cbo, cbo[c] > threshold );
	
	badPercentage = round(numClassesWithBadCoupling * 100.0 / size(cbo), 0.01);
	
	stars = four();
	
	if (badPercentage > 20) {
		stars = \one();
	}
	else if (badPercentage > 10) {
		stars = two();
	}
	else if (badPercentage > 5) {
		stars = three();
	}

	txt = "The percentage of <language> classes with problematic coupling (higher than <threshold>) is <badPercentage>%."; 

	return factoid(txt, stars);
}


Factoid Cohesion(
	str language,
	map[loc, int] lcom4
) {
	if (lcom4 == ()) {
		throw undefined("No LCOM4 data", |file:///|);
	}

	// LCOM4: Hitz and Montazeri: Measuring coupling and cohesion in object oriented systems, 1995
	
	numClassesWithBadCohesion = ( 0 | it + 1 | c <- lcom4, lcom4[c] != 1 );
	
	badPercentage = round(numClassesWithBadCohesion * 100.0 / size(lcom4), 0.01);
	
	stars = four();
	
	if (badPercentage > 20) {
		stars = \one();
	}
	else if (badPercentage > 10) {
		stars = two();
	}
	else if (badPercentage > 5) {
		stars = three();
	}

	txt = "The percentage of <language> classes with problematic cohesion (higher than 1) is <badPercentage>%."; 

	return factoid(txt, stars);
}
