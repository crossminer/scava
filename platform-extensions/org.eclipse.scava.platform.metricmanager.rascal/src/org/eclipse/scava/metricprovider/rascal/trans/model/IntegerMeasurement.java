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


public class IntegerMeasurement extends Measurement {
	
	
	
	public IntegerMeasurement() { 
		super();
		super.setSuperTypes("org.eclipse.scava.metricprovider.rascal.trans.model.Measurement");
		URI.setOwningType("org.eclipse.scava.metricprovider.rascal.trans.model.IntegerMeasurement");
		VALUE.setOwningType("org.eclipse.scava.metricprovider.rascal.trans.model.IntegerMeasurement");
	}
	
	public static StringQueryProducer URI = new StringQueryProducer("uri"); 
	public static NumericalQueryProducer VALUE = new NumericalQueryProducer("value");
	
	
	public long getValue() {
		return parseLong(dbObject.get("value")+"", 0);
	}
	
	public IntegerMeasurement setValue(long value) {
		dbObject.put("value", value);
		notifyChanged();
		return this;
	}
	
	
	
	
}
