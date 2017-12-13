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
package org.eclipse.crossmeter.repository.model.sourceforge;

import com.googlecode.pongo.runtime.querying.*;


public abstract class Tracker extends org.eclipse.crossmeter.repository.model.NamedElement {
	
	
	
	public Tracker() { 
		super();
		super.setSuperTypes("org.eclipse.crossmeter.repository.model.sourceforge.NamedElement");
		LOCATION.setOwningType("org.eclipse.crossmeter.repository.model.sourceforge.Tracker");
		STATUS.setOwningType("org.eclipse.crossmeter.repository.model.sourceforge.Tracker");
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