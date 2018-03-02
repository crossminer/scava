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

import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class RealMeasurement extends Measurement {
	
	
	
	public RealMeasurement() { 
		super();
		super.setSuperTypes("org.eclipse.scava.metricprovider.rascal.trans.model.Measurement");
		URI.setOwningType("org.eclipse.scava.metricprovider.rascal.trans.model.RealMeasurement");
		VALUE.setOwningType("org.eclipse.scava.metricprovider.rascal.trans.model.RealMeasurement");
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
