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


public class MailingList extends org.eclipse.scava.repository.model.CommunicationChannel {
	
	
	
	public MailingList() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.eclipse.CommunicationChannel");
		NAME.setOwningType("org.eclipse.scava.repository.model.eclipse.MailingList");
		DESCRIPTION.setOwningType("org.eclipse.scava.repository.model.eclipse.MailingList");
		TYPE.setOwningType("org.eclipse.scava.repository.model.eclipse.MailingList");
		ARCHIVEURL.setOwningType("org.eclipse.scava.repository.model.eclipse.MailingList");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	public static StringQueryProducer ARCHIVEURL = new StringQueryProducer("archiveUrl"); 
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public MailingList setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public MailingList setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public String getType() {
		return parseString(dbObject.get("type")+"", "");
	}
	
	public MailingList setType(String type) {
		dbObject.put("type", type);
		notifyChanged();
		return this;
	}
	public String getArchiveUrl() {
		return parseString(dbObject.get("archiveUrl")+"", "");
	}
	
	public MailingList setArchiveUrl(String archiveUrl) {
		dbObject.put("archiveUrl", archiveUrl);
		notifyChanged();
		return this;
	}
	
	
	
	
}
