/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.activeusers.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class User extends Pongo {
	
	
	
	public User() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.User");
		USERID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.User");
		LASTACTIVITYDATE.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.User");
		COMMENTS.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.User");
		REQUESTS.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.User");
		REPLIES.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.User");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer USERID = new StringQueryProducer("userId"); 
	public static StringQueryProducer LASTACTIVITYDATE = new StringQueryProducer("lastActivityDate"); 
	public static NumericalQueryProducer COMMENTS = new NumericalQueryProducer("comments");
	public static NumericalQueryProducer REQUESTS = new NumericalQueryProducer("requests");
	public static NumericalQueryProducer REPLIES = new NumericalQueryProducer("replies");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public User setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getUserId() {
		return parseString(dbObject.get("userId")+"", "");
	}
	
	public User setUserId(String userId) {
		dbObject.put("userId", userId);
		notifyChanged();
		return this;
	}
	public String getLastActivityDate() {
		return parseString(dbObject.get("lastActivityDate")+"", "");
	}
	
	public User setLastActivityDate(String lastActivityDate) {
		dbObject.put("lastActivityDate", lastActivityDate);
		notifyChanged();
		return this;
	}
	public int getComments() {
		return parseInteger(dbObject.get("comments")+"", 0);
	}
	
	public User setComments(int comments) {
		dbObject.put("comments", comments);
		notifyChanged();
		return this;
	}
	public int getRequests() {
		return parseInteger(dbObject.get("requests")+"", 0);
	}
	
	public User setRequests(int requests) {
		dbObject.put("requests", requests);
		notifyChanged();
		return this;
	}
	public int getReplies() {
		return parseInteger(dbObject.get("replies")+"", 0);
	}
	
	public User setReplies(int replies) {
		dbObject.put("replies", replies);
		notifyChanged();
		return this;
	}
	
	
	
	
}
