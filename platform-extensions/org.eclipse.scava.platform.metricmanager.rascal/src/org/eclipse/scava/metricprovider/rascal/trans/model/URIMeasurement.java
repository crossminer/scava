/*******************************************************************************
 * Copyright (c) 2017 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.rascal.trans.model;

import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class URIMeasurement extends Measurement {
	
	
	
	public URIMeasurement() { 
		super();
		super.setSuperTypes("org.eclipse.scava.metricprovider.rascal.trans.model.Measurement");
		URI.setOwningType("org.eclipse.scava.metricprovider.rascal.trans.model.URIMeasurement");
		VALUE.setOwningType("org.eclipse.scava.metricprovider.rascal.trans.model.URIMeasurement");
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
