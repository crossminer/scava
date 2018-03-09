/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.eclipse;

import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class Release extends org.eclipse.scava.repository.model.NamedElement {
	
	
	
	public Release() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.eclipse.NamedElement");
		TYPE.setOwningType("org.eclipse.scava.repository.model.eclipse.Release");
		DATE.setOwningType("org.eclipse.scava.repository.model.eclipse.Release");
		LINK.setOwningType("org.eclipse.scava.repository.model.eclipse.Release");
	}
	
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	public static StringQueryProducer LINK = new StringQueryProducer("link"); 
	
	
	public String getType() {
		return parseString(dbObject.get("type")+"", "");
	}
	
	public Release setType(String type) {
		dbObject.put("type", type);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public Release setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	public String getLink() {
		return parseString(dbObject.get("link")+"", "");
	}
	
	public Release setLink(String link) {
		dbObject.put("link", link);
		notifyChanged();
		return this;
	}
	
	
	
	
}
