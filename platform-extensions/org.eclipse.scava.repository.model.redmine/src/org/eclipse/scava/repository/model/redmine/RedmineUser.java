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

import com.googlecode.pongo.runtime.querying.*;


public class RedmineUser extends org.eclipse.scava.repository.model.Person {
	
	
	
	public RedmineUser() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.redmine.Person");
		LOGIN.setOwningType("org.eclipse.scava.repository.model.redmine.RedmineUser");
		ID.setOwningType("org.eclipse.scava.repository.model.redmine.RedmineUser");
	}
	
	public static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	public static StringQueryProducer ID = new StringQueryProducer("ID"); 
	
	
	public String getLogin() {
		return parseString(dbObject.get("login")+"", "");
	}
	
	public RedmineUser setLogin(String login) {
		dbObject.put("login", login);
		notifyChanged();
		return this;
	}
	public String getID() {
		return parseString(dbObject.get("ID")+"", "");
	}
	
	public RedmineUser setID(String ID) {
		dbObject.put("ID", ID);
		notifyChanged();
		return this;
	}
	
	
	
	
}
