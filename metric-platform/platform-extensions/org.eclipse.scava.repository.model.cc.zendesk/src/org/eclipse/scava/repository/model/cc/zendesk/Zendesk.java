/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.cc.zendesk;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Zendesk extends org.eclipse.scava.repository.model.CommunicationChannel {
	
	
	
	public Zendesk() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.cc.zendesk.CommunicationChannel");
		AUTHENTICATIONREQUIRED.setOwningType("org.eclipse.scava.repository.model.cc.zendesk.Zendesk");
		USERNAME.setOwningType("org.eclipse.scava.repository.model.cc.zendesk.Zendesk");
		PASSWORD.setOwningType("org.eclipse.scava.repository.model.cc.zendesk.Zendesk");
		PORT.setOwningType("org.eclipse.scava.repository.model.cc.zendesk.Zendesk");
		DESCRIPTION.setOwningType("org.eclipse.scava.repository.model.cc.zendesk.Zendesk");
		NAME.setOwningType("org.eclipse.scava.repository.model.cc.zendesk.Zendesk");
		INTERVAL.setOwningType("org.eclipse.scava.repository.model.cc.zendesk.Zendesk");
		LASTARTICLECHECKED.setOwningType("org.eclipse.scava.repository.model.cc.zendesk.Zendesk");
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.repository.model.cc.zendesk.Zendesk");
	}
	
	public static StringQueryProducer AUTHENTICATIONREQUIRED = new StringQueryProducer("authenticationRequired"); 
	public static StringQueryProducer USERNAME = new StringQueryProducer("username"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	public static NumericalQueryProducer PORT = new NumericalQueryProducer("port");
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer INTERVAL = new NumericalQueryProducer("interval");
	public static StringQueryProducer LASTARTICLECHECKED = new StringQueryProducer("lastArticleChecked"); 
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsGroupName"); 
	
	
	public boolean getAuthenticationRequired() {
		return parseBoolean(dbObject.get("authenticationRequired")+"", false);
	}
	
	public Zendesk setAuthenticationRequired(boolean authenticationRequired) {
		dbObject.put("authenticationRequired", authenticationRequired);
		notifyChanged();
		return this;
	}
	public String getUsername() {
		return parseString(dbObject.get("username")+"", "");
	}
	
	public Zendesk setUsername(String username) {
		dbObject.put("username", username);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public Zendesk setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	public int getPort() {
		return parseInteger(dbObject.get("port")+"", 0);
	}
	
	public Zendesk setPort(int port) {
		dbObject.put("port", port);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public Zendesk setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Zendesk setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public int getInterval() {
		return parseInteger(dbObject.get("interval")+"", 0);
	}
	
	public Zendesk setInterval(int interval) {
		dbObject.put("interval", interval);
		notifyChanged();
		return this;
	}
	public String getLastArticleChecked() {
		return parseString(dbObject.get("lastArticleChecked")+"", "");
	}
	
	public Zendesk setLastArticleChecked(String lastArticleChecked) {
		dbObject.put("lastArticleChecked", lastArticleChecked);
		notifyChanged();
		return this;
	}
	public String getNewsGroupName() {
		return parseString(dbObject.get("newsGroupName")+"", "");
	}
	
	public Zendesk setNewsGroupName(String newsGroupName) {
		dbObject.put("newsGroupName", newsGroupName);
		notifyChanged();
		return this;
	}
	
	
	
	
}
