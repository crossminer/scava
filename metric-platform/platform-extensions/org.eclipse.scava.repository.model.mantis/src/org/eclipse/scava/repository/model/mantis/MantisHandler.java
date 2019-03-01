/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.mantis;

import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class MantisHandler extends Pongo {
	
	
	
	public MantisHandler() { 
		super();
		_ID.setOwningType("org.eclipse.scava.repository.model.mantis.MantisHandler");
		NAME.setOwningType("org.eclipse.scava.repository.model.mantis.MantisHandler");
		REAL_NAME.setOwningType("org.eclipse.scava.repository.model.mantis.MantisHandler");
		EMAIL.setOwningType("org.eclipse.scava.repository.model.mantis.MantisHandler");
	}
	
	public static StringQueryProducer _ID = new StringQueryProducer("_id"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer REAL_NAME = new StringQueryProducer("real_name"); 
	public static StringQueryProducer EMAIL = new StringQueryProducer("email"); 
	
	
	public String get_id() {
		return parseString(dbObject.get("_id")+"", "");
	}
	
	public MantisHandler set_id(String _id) {
		dbObject.put("_id", _id);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public MantisHandler setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getReal_name() {
		return parseString(dbObject.get("real_name")+"", "");
	}
	
	public MantisHandler setReal_name(String real_name) {
		dbObject.put("real_name", real_name);
		notifyChanged();
		return this;
	}
	public String getEmail() {
		return parseString(dbObject.get("email")+"", "");
	}
	
	public MantisHandler setEmail(String email) {
		dbObject.put("email", email);
		notifyChanged();
		return this;
	}
	
	
	
	
}