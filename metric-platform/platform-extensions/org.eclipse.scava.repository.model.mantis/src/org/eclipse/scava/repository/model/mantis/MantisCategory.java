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


public class MantisCategory extends Pongo {
	
	
	public MantisCategory() { 
		super();
		_ID.setOwningType("org.eclipse.scava.repository.model.mantis.MantisCategory");
		NAME.setOwningType("org.eclipse.scava.repository.model.mantis.MantisCategory");
	}
	
	public static StringQueryProducer _ID = new StringQueryProducer("_id"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	public String get_id() {
		return parseString(dbObject.get("_id")+"", "");
	}
	
	public MantisCategory set_id(String _id) {
		dbObject.put("_id", _id);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public MantisCategory setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	
	
	
	
}