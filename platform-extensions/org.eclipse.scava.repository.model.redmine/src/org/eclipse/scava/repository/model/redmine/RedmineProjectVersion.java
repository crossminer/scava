/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.redmine;

import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class RedmineProjectVersion extends Pongo {
	
	
	
	public RedmineProjectVersion() { 
		super();
		NAME.setOwningType("org.eclipse.scava.repository.model.redmine.RedmineProjectVersion");
		DESCRIPTION.setOwningType("org.eclipse.scava.repository.model.redmine.RedmineProjectVersion");
		CREATED_ON.setOwningType("org.eclipse.scava.repository.model.redmine.RedmineProjectVersion");
		UPDATED_ON.setOwningType("org.eclipse.scava.repository.model.redmine.RedmineProjectVersion");
		STATUS.setOwningType("org.eclipse.scava.repository.model.redmine.RedmineProjectVersion");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer CREATED_ON = new StringQueryProducer("created_on"); 
	public static StringQueryProducer UPDATED_ON = new StringQueryProducer("updated_on"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public RedmineProjectVersion setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public RedmineProjectVersion setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public String getCreated_on() {
		return parseString(dbObject.get("created_on")+"", "");
	}
	
	public RedmineProjectVersion setCreated_on(String created_on) {
		dbObject.put("created_on", created_on);
		notifyChanged();
		return this;
	}
	public String getUpdated_on() {
		return parseString(dbObject.get("updated_on")+"", "");
	}
	
	public RedmineProjectVersion setUpdated_on(String updated_on) {
		dbObject.put("updated_on", updated_on);
		notifyChanged();
		return this;
	}
	public String getStatus() {
		return parseString(dbObject.get("status")+"", "");
	}
	
	public RedmineProjectVersion setStatus(String status) {
		dbObject.put("status", status);
		notifyChanged();
		return this;
	}
	
	
	
	
}
