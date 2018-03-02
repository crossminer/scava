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

import com.mongodb.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class Request extends Tracker {
	
	protected org.eclipse.scava.repository.model.Person creator = null;
	
	
	public Request() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.sourceforge.Tracker","org.eclipse.scava.repository.model.sourceforge.NamedElement");
		LOCATION.setOwningType("org.eclipse.scava.repository.model.sourceforge.Request");
		STATUS.setOwningType("org.eclipse.scava.repository.model.sourceforge.Request");
		SUMMARY.setOwningType("org.eclipse.scava.repository.model.sourceforge.Request");
		CREATED_AT.setOwningType("org.eclipse.scava.repository.model.sourceforge.Request");
		UPDATED_AT.setOwningType("org.eclipse.scava.repository.model.sourceforge.Request");
	}
	
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer SUMMARY = new StringQueryProducer("summary"); 
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	
	
	public String getSummary() {
		return parseString(dbObject.get("summary")+"", "");
	}
	
	public Request setSummary(String summary) {
		dbObject.put("summary", summary);
		notifyChanged();
		return this;
	}
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public Request setCreated_at(String created_at) {
		dbObject.put("created_at", created_at);
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public Request setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at);
		notifyChanged();
		return this;
	}
	
	
	
	
	public org.eclipse.scava.repository.model.Person getCreator() {
		if (creator == null && dbObject.containsField("creator")) {
			creator = (org.eclipse.scava.repository.model.Person) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("creator"));
		}
		return creator;
	}
	
	public Request setCreator(org.eclipse.scava.repository.model.Person creator) {
		if (this.creator != creator) {
			if (creator == null) {
				dbObject.removeField("creator");
			}
			else {
				dbObject.put("creator", creator.getDbObject());
			}
			this.creator = creator;
			notifyChanged();
		}
		return this;
	}
}
