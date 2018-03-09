/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.commits.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class CommitData extends Pongo {
	
	
	
	public CommitData() { 
		super();
		DATE.setOwningType("org.eclipse.scava.metricprovider.trans.commits.model.CommitData");
		TIME.setOwningType("org.eclipse.scava.metricprovider.trans.commits.model.CommitData");
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
