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

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public abstract class Measurement extends Pongo {
	
	
	
	public Measurement() { 
		super();
		URI.setOwningType("org.eclipse.scava.metricprovider.rascal.trans.model.Measurement");
	}
	
	public static StringQueryProducer URI = new StringQueryProducer("uri"); 
	
	
	public String getUri() {
		return parseString(dbObject.get("uri")+"", "");
	}
	
	public Measurement setUri(String uri) {
		dbObject.put("uri", uri);
		notifyChanged();
		return this;
	}
	
	
	
	
}
