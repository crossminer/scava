/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.downloadcounter.model;

import com.googlecode.pongo.runtime.Pongo;


public class Download extends Pongo {
	
	
	
	public Download() { 
		super();
	}
	
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public Download setDate(String date) {
		dbObject.put("date", date + "");
		notifyChanged();
		return this;
	}
	public int getCounter() {
		return parseInteger(dbObject.get("counter")+"", 0);
	}
	
	public Download setCounter(int counter) {
		dbObject.put("counter", counter + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}
