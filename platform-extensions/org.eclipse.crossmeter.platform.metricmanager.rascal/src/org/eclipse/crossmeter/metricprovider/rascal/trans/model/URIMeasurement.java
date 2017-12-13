/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jurgen Vinju - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.rascal.trans.model;

import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class URIMeasurement extends Measurement {
	
	
	
	public URIMeasurement() { 
		super();
		super.setSuperTypes("org.eclipse.crossmeter.metricprovider.rascal.trans.model.Measurement");
		URI.setOwningType("org.eclipse.crossmeter.metricprovider.rascal.trans.model.URIMeasurement");
		VALUE.setOwningType("org.eclipse.crossmeter.metricprovider.rascal.trans.model.URIMeasurement");
	}
	
	public static StringQueryProducer URI = new StringQueryProducer("uri"); 
	public static StringQueryProducer VALUE = new StringQueryProducer("value"); 
	
	
	public String getValue() {
		return parseString(dbObject.get("value")+"", "");
	}
	
	public URIMeasurement setValue(String value) {
		dbObject.put("value", value);
		notifyChanged();
		return this;
	}
	
	
	
	
}