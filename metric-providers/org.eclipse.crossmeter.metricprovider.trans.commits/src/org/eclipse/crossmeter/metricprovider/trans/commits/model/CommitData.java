/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.commits.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class CommitData extends Pongo {
	
	
	
	public CommitData() { 
		super();
		DATE.setOwningType("org.eclipse.crossmeter.metricprovider.trans.commits.model.CommitData");
		TIME.setOwningType("org.eclipse.crossmeter.metricprovider.trans.commits.model.CommitData");
	}
	
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	public static StringQueryProducer TIME = new StringQueryProducer("time"); 
	
	
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public CommitData setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	public String getTime() {
		return parseString(dbObject.get("time")+"", "");
	}
	
	public CommitData setTime(String time) {
		dbObject.put("time", time);
		notifyChanged();
		return this;
	}
	
	
	
	
}