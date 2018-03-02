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

import java.util.List;

import com.googlecode.pongo.runtime.PongoList;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.BasicDBList;


public class ListMeasurement extends Measurement {
	
	protected List<Measurement> value = null;
	
	
	public ListMeasurement() { 
		super();
		dbObject.put("value", new BasicDBList());
		super.setSuperTypes("org.eclipse.scava.metricprovider.rascal.trans.model.Measurement");
		URI.setOwningType("org.eclipse.scava.metricprovider.rascal.trans.model.ListMeasurement");
	}
	
	public static StringQueryProducer URI = new StringQueryProducer("uri"); 
	
	
	
	
	public List<Measurement> getValue() {
		if (value == null) {
			value = new PongoList<Measurement>(this, "value", true);
		}
		return value;
	}
	
	
}
