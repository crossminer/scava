/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.sourceforge;

import com.googlecode.pongo.runtime.querying.*;


public abstract class Tracker extends org.eclipse.scava.repository.model.NamedElement {
	
	
	
	public Tracker() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.sourceforge.NamedElement");
		LOCATION.setOwningType("org.eclipse.scava.repository.model.sourceforge.Tracker");
		STATUS.setOwningType("org.eclipse.scava.repository.model.sourceforge.Tracker");
	}
	
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	
	
	public String getLocation() {
		return parseString(dbObject.get("location")+"", "");
	}
	
	public Tracker setLocation(String location) {
		dbObject.put("location", location);
		notifyChanged();
		return this;
	}
	public String getStatus() {
		return parseString(dbObject.get("status")+"", "");
	}
	
	public Tracker setStatus(String status) {
		dbObject.put("status", status);
		notifyChanged();
		return this;
	}
	
	
	
	
}
