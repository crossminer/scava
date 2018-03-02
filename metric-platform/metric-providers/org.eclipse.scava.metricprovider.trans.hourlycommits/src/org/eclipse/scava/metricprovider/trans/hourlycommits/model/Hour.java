/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.hourlycommits.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class Hour extends Pongo {
	
	
	
	public Hour() { 
		super();
		HOUR.setOwningType("org.eclipse.scava.metricprovider.trans.hourlycommits.model.Hour");
		NUMBEROFCOMMITS.setOwningType("org.eclipse.scava.metricprovider.trans.hourlycommits.model.Hour");
	}
	
	public static StringQueryProducer HOUR = new StringQueryProducer("hour"); 
	public static NumericalQueryProducer NUMBEROFCOMMITS = new NumericalQueryProducer("numberOfCommits");
	
	
	public String getHour() {
		return parseString(dbObject.get("hour")+"", "");
	}
	
	public Hour setHour(String hour) {
		dbObject.put("hour", hour);
		notifyChanged();
		return this;
	}
	public int getNumberOfCommits() {
		return parseInteger(dbObject.get("numberOfCommits")+"", 0);
	}
	
	public Hour setNumberOfCommits(int numberOfCommits) {
		dbObject.put("numberOfCommits", numberOfCommits);
		notifyChanged();
		return this;
	}
	
	
	
	
}
