/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.cc.wiki;

import com.googlecode.pongo.runtime.querying.*;


public class Wiki extends org.eclipse.scava.repository.model.CommunicationChannel {
	
	
	
	public Wiki() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.cc.wiki.CommunicationChannel");
		NAME.setOwningType("org.eclipse.scava.repository.model.cc.wiki.Wiki");
		DESCRIPTION.setOwningType("org.eclipse.scava.repository.model.cc.wiki.Wiki");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Wiki setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public Wiki setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	
	@Override
	public String getCommunicationChannelType() {
		return "Wiki";
	}

	@Override
	public String getInstanceId() {
		return getName();
	}
	
	
}
