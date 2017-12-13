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
package org.eclipse.crossmeter.repository.model.cc.forum;

import com.googlecode.pongo.runtime.querying.*;


public class Forum extends org.eclipse.crossmeter.repository.model.CommunicationChannel {
	
	
	
	public Forum() { 
		super();
		super.setSuperTypes("org.eclipse.crossmeter.repository.model.cc.forum.CommunicationChannel");
		NAME.setOwningType("org.eclipse.crossmeter.repository.model.cc.forum.Forum");
		DESCRIPTION.setOwningType("org.eclipse.crossmeter.repository.model.cc.forum.Forum");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Forum setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public Forum setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	
	
	
	
}