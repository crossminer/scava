/*******************************************************************************
 * Copyright (c) 2018 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.githubstars.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Stargazers extends Pongo {
	
	
	
	public Stargazers() { 
		super();
		LOGIN.setOwningType("org.eclipse.scava.metricprovider.trans.githubstars.model.Stargazers");
		DATESTAMP.setOwningType("org.eclipse.scava.metricprovider.trans.githubstars.model.Stargazers");
	}
	
	public static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	public static StringQueryProducer DATESTAMP = new StringQueryProducer("datestamp"); 
	
	
	public String getLogin() {
		return parseString(dbObject.get("login")+"", "");
	}
	
	public Stargazers setLogin(String login) {
		dbObject.put("login", login);
		notifyChanged();
		return this;
	}
	public String getDatestamp() {
		return parseString(dbObject.get("datestamp")+"", "");
	}
	
	public Stargazers setDatestamp(String datestamp) {
		dbObject.put("datestamp", datestamp);
		notifyChanged();
		return this;
	}
	
	
	
	
}
