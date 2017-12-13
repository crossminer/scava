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
package org.eclipse.crossmeter.metricprovider.trans.bugs.requestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugStatistics extends Pongo {
	
	
	
	public BugStatistics() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.requestsreplies.model.BugStatistics");
		BUGID.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.requestsreplies.model.BugStatistics");
		FIRSTREQUEST.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.requestsreplies.model.BugStatistics");
		ANSWERED.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.requestsreplies.model.BugStatistics");
		RESPONSEDURATIONSEC.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.requestsreplies.model.BugStatistics");
		RESPONSEDATE.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.requestsreplies.model.BugStatistics");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer FIRSTREQUEST = new StringQueryProducer("firstRequest"); 
	public static StringQueryProducer ANSWERED = new StringQueryProducer("answered"); 
	public static NumericalQueryProducer RESPONSEDURATIONSEC = new NumericalQueryProducer("responseDurationSec");
	public static StringQueryProducer RESPONSEDATE = new StringQueryProducer("responseDate"); 
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugStatistics setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugStatistics setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public boolean getFirstRequest() {
		return parseBoolean(dbObject.get("firstRequest")+"", false);
	}
	
	public BugStatistics setFirstRequest(boolean firstRequest) {
		dbObject.put("firstRequest", firstRequest);
		notifyChanged();
		return this;
	}
	public boolean getAnswered() {
		return parseBoolean(dbObject.get("answered")+"", false);
	}
	
	public BugStatistics setAnswered(boolean answered) {
		dbObject.put("answered", answered);
		notifyChanged();
		return this;
	}
	public long getResponseDurationSec() {
		return parseLong(dbObject.get("responseDurationSec")+"", 0);
	}
	
	public BugStatistics setResponseDurationSec(long responseDurationSec) {
		dbObject.put("responseDurationSec", responseDurationSec);
		notifyChanged();
		return this;
	}
	public String getResponseDate() {
		return parseString(dbObject.get("responseDate")+"", "");
	}
	
	public BugStatistics setResponseDate(String responseDate) {
		dbObject.put("responseDate", responseDate);
		notifyChanged();
		return this;
	}
	
	
	
	
}