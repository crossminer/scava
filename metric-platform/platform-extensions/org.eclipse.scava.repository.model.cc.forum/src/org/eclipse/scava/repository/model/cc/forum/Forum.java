/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.cc.forum;

import com.googlecode.pongo.runtime.querying.*;


public class Forum extends org.eclipse.scava.repository.model.CommunicationChannel {
	
	
	
	public Forum() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.cc.forum.CommunicationChannel");
		NAME.setOwningType("org.eclipse.scava.repository.model.cc.forum.Forum");
		DESCRIPTION.setOwningType("org.eclipse.scava.repository.model.cc.forum.Forum");
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
	
	@Override
	public String getCommunicationChannelType() {
		return "OssmeterForum";
	}

	@Override
	public String getInstanceId() {
		return getName();
	}
	
	
}
