/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation,
 *    Juri Di Rocco - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.downloadcounter.model;

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