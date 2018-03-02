/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
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


public class Company extends NamedElement {
	
	
	
	public Company() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.NamedElement");
		NAME.setOwningType("org.eclipse.scava.repository.model.Company");
		URL.setOwningType("org.eclipse.scava.repository.model.Company");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public Company setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	
	
	
	
}
