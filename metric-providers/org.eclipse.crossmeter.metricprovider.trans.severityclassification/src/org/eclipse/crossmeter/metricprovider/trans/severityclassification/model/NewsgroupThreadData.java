/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.severityclassification.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupThreadData extends Pongo {
	
	
	
	public NewsgroupThreadData() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.crossmeter.metricprovider.trans.severityclassification.model.NewsgroupThreadData");
		THREADID.setOwningType("org.eclipse.crossmeter.metricprovider.trans.severityclassification.model.NewsgroupThreadData");
		SEVERITY.setOwningType("org.eclipse.crossmeter.metricprovider.trans.severityclassification.model.NewsgroupThreadData");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer THREADID = new NumericalQueryProducer("threadId");
	public static StringQueryProducer SEVERITY = new StringQueryProducer("severity"); 
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgroupThreadData setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public int getThreadId() {
		return parseInteger(dbObject.get("threadId")+"", 0);
	}
	
	public NewsgroupThreadData setThreadId(int threadId) {
		dbObject.put("threadId", threadId);
		notifyChanged();
		return this;
	}
	public String getSeverity() {
		return parseString(dbObject.get("severity")+"", "");
	}
	
	public NewsgroupThreadData setSeverity(String severity) {
		dbObject.put("severity", severity);
		notifyChanged();
		return this;
	}
	
	
	
	
}