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
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Donation extends Pongo {
	
	protected List<String> charities = null;
	
	
	public Donation() { 
		super();
		dbObject.put("charities", new BasicDBList());
		COMMENT.setOwningType("org.eclipse.scava.repository.model.sourceforge.Donation");
		STATUS.setOwningType("org.eclipse.scava.repository.model.sourceforge.Donation");
		CHARITIES.setOwningType("org.eclipse.scava.repository.model.sourceforge.Donation");
	}
	
	public static StringQueryProducer COMMENT = new StringQueryProducer("comment"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static ArrayQueryProducer CHARITIES = new ArrayQueryProducer("charities");
	
	
	public String getComment() {
		return parseString(dbObject.get("comment")+"", "");
	}
	
	public Donation setComment(String comment) {
		dbObject.put("comment", comment);
		notifyChanged();
		return this;
	}
	public String getStatus() {
		return parseString(dbObject.get("status")+"", "");
	}
	
	public Donation setStatus(String status) {
		dbObject.put("status", status);
		notifyChanged();
		return this;
	}
	
	public List<String> getCharities() {
		if (charities == null) {
			charities = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("charities"));
		}
		return charities;
	}
	
	
	
}
