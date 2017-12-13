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

import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class RealMeasurement extends Measurement {
	
	
	
	public RealMeasurement() { 
		super();
		super.setSuperTypes("org.eclipse.crossmeter.metricprovider.rascal.trans.model.Measurement");
		URI.setOwningType("org.eclipse.crossmeter.metricprovider.rascal.trans.model.RealMeasurement");
		VALUE.setOwningType("org.eclipse.crossmeter.metricprovider.rascal.trans.model.RealMeasurement");
	}
	
	public static StringQueryProducer URI = new StringQueryProducer("uri"); 
	public static NumericalQueryProducer VALUE = new NumericalQueryProducer("value");
	
	
	public float getValue() {
		return parseFloat(dbObject.get("value")+"", 0.0f);
	}
	
	public RealMeasurement setValue(float value) {
		dbObject.put("value", value);
		notifyChanged();
		return this;
	}
	
	
	
	
}