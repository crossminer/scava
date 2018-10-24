/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Properties extends NamedElement {
	
	
	
	public Properties() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.NamedElement");
		NAME.setOwningType("org.eclipse.scava.repository.model.Properties");
		KEY.setOwningType("org.eclipse.scava.repository.model.Properties");
		VALUE.setOwningType("org.eclipse.scava.repository.model.Properties");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer KEY = new StringQueryProducer("key"); 
	public static StringQueryProducer VALUE = new StringQueryProducer("value"); 
	
	
	public String getKey() {
		return parseString(dbObject.get("key")+"", "");
	}
	
	public Properties setKey(String key) {
		dbObject.put("key", key);
		notifyChanged();
		return this;
	}
	public String getValue() {
		return parseString(dbObject.get("value")+"", "");
	}
	
	public Properties setValue(String value) {
		dbObject.put("value", value);
		notifyChanged();
		return this;
	}
	
	
	
	
}