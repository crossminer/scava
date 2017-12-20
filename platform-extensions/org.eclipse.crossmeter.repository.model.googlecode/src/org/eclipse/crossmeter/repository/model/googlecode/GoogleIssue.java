/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.repository.model.googlecode;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GoogleIssue extends Pongo {
	
	protected List<GoogleIssueComment> comments = null;
	protected List<GoogleLabel> labels = null;
	protected GoogleUser owner = null;
	
	
	public GoogleIssue() { 
		super();
		dbObject.put("comments", new BasicDBList());
		dbObject.put("labels", new BasicDBList());
		CREATED_AT.setOwningType("org.eclipse.crossmeter.repository.model.googlecode.GoogleIssue");
		UPDATED_AT.setOwningType("org.eclipse.crossmeter.repository.model.googlecode.GoogleIssue");
		PRIORITY.setOwningType("org.eclipse.crossmeter.repository.model.googlecode.GoogleIssue");
		TYPE.setOwningType("org.eclipse.crossmeter.repository.model.googlecode.GoogleIssue");
		COMPONENT.setOwningType("org.eclipse.crossmeter.repository.model.googlecode.GoogleIssue");
		STATUS.setOwningType("org.eclipse.crossmeter.repository.model.googlecode.GoogleIssue");
		STARS.setOwningType("org.eclipse.crossmeter.repository.model.googlecode.GoogleIssue");
		SUMMARY.setOwningType("org.eclipse.crossmeter.repository.model.googlecode.GoogleIssue");
	}
	
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	public static StringQueryProducer PRIORITY = new StringQueryProducer("priority"); 
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	public static StringQueryProducer COMPONENT = new StringQueryProducer("component"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static NumericalQueryProducer STARS = new NumericalQueryProducer("stars");
	public static StringQueryProducer SUMMARY = new StringQueryProducer("summary"); 
	
	
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public GoogleIssue setCreated_at(String created_at) {
		dbObject.put("created_at", created_at);
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public GoogleIssue setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at);
		notifyChanged();
		return this;
	}
	public String getPriority() {
		return parseString(dbObject.get("priority")+"", "");
	}
	
	public GoogleIssue setPriority(String priority) {
		dbObject.put("priority", priority);
		notifyChanged();
		return this;
	}
	public String getType() {
		return parseString(dbObject.get("type")+"", "");
	}
	
	public GoogleIssue setType(String type) {
		dbObject.put("type", type);
		notifyChanged();
		return this;
	}
	public String getComponent() {
		return parseString(dbObject.get("component")+"", "");
	}
	
	public GoogleIssue setComponent(String component) {
		dbObject.put("component", component);
		notifyChanged();
		return this;
	}
	public String getStatus() {
		return parseString(dbObject.get("status")+"", "");
	}
	
	public GoogleIssue setStatus(String status) {
		dbObject.put("status", status);
		notifyChanged();
		return this;
	}
	public int getStars() {
		return parseInteger(dbObject.get("stars")+"", 0);
	}
	
	public GoogleIssue setStars(int stars) {
		dbObject.put("stars", stars);
		notifyChanged();
		return this;
	}
	public String getSummary() {
		return parseString(dbObject.get("summary")+"", "");
	}
	
	public GoogleIssue setSummary(String summary) {
		dbObject.put("summary", summary);
		notifyChanged();
		return this;
	}
	
	
	public List<GoogleIssueComment> getComments() {
		if (comments == null) {
			comments = new PongoList<GoogleIssueComment>(this, "comments", true);
		}
		return comments;
	}
	public List<GoogleLabel> getLabels() {
		if (labels == null) {
			labels = new PongoList<GoogleLabel>(this, "labels", true);
		}
		return labels;
	}
	
	
	public GoogleUser getOwner() {
		if (owner == null && dbObject.containsField("owner")) {
			owner = (GoogleUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("owner"));
		}
		return owner;
	}
	
	public GoogleIssue setOwner(GoogleUser owner) {
		if (this.owner != owner) {
			if (owner == null) {
				dbObject.removeField("owner");
			}
			else {
				dbObject.put("owner", owner.getDbObject());
			}
			this.owner = owner;
			notifyChanged();
		}
		return this;
	}
}
