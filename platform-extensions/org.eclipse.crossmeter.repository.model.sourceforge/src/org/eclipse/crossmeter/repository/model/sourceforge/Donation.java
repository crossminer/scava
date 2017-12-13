/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation,
 *    Juri Di Rocco - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.repository.model.sourceforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Donation extends Pongo {
	
	protected List<String> charities = null;
	
	
	public Donation() { 
		super();
		dbObject.put("charities", new BasicDBList());
		COMMENT.setOwningType("org.eclipse.crossmeter.repository.model.sourceforge.Donation");
		STATUS.setOwningType("org.eclipse.crossmeter.repository.model.sourceforge.Donation");
		CHARITIES.setOwningType("org.eclipse.crossmeter.repository.model.sourceforge.Donation");
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