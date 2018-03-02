/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.redmine;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class RedmineIssue extends Pongo {
	
	protected List<RedmineIssueRelation> relations = null;
	protected RedmineCategory category = null;
	protected RedmineFeature feature = null;
	protected RedmineUser author = null;
	protected RedmineUser assignedTo = null;
	
	
	public RedmineIssue() { 
		super();
		dbObject.put("author", new BasicDBObject());
		dbObject.put("assignedTo", new BasicDBObject());
		dbObject.put("category", new BasicDBObject());
		dbObject.put("feature", new BasicDBObject());
		dbObject.put("relations", new BasicDBList());
		DESCRIPTION.setOwningType("org.eclipse.scava.repository.model.redmine.RedmineIssue");
		STATUS.setOwningType("org.eclipse.scava.repository.model.redmine.RedmineIssue");
		PRIORITY.setOwningType("org.eclipse.scava.repository.model.redmine.RedmineIssue");
		TEMPLATE.setOwningType("org.eclipse.scava.repository.model.redmine.RedmineIssue");
		START_DATE.setOwningType("org.eclipse.scava.repository.model.redmine.RedmineIssue");
		UPDATE_DATE.setOwningType("org.eclipse.scava.repository.model.redmine.RedmineIssue");
		DUE_DATE.setOwningType("org.eclipse.scava.repository.model.redmine.RedmineIssue");
	}
	
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer PRIORITY = new StringQueryProducer("priority"); 
	public static StringQueryProducer TEMPLATE = new StringQueryProducer("template"); 
	public static StringQueryProducer START_DATE = new StringQueryProducer("start_date"); 
	public static StringQueryProducer UPDATE_DATE = new StringQueryProducer("update_date"); 
	public static StringQueryProducer DUE_DATE = new StringQueryProducer("due_date"); 
	
	
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public RedmineIssue setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public String getStatus() {
		return parseString(dbObject.get("status")+"", "");
	}
	
	public RedmineIssue setStatus(String status) {
		dbObject.put("status", status);
		notifyChanged();
		return this;
	}
	public String getPriority() {
		return parseString(dbObject.get("priority")+"", "");
	}
	
	public RedmineIssue setPriority(String priority) {
		dbObject.put("priority", priority);
		notifyChanged();
		return this;
	}
	public String getTemplate() {
		return parseString(dbObject.get("template")+"", "");
	}
	
	public RedmineIssue setTemplate(String template) {
		dbObject.put("template", template);
		notifyChanged();
		return this;
	}
	public String getStart_date() {
		return parseString(dbObject.get("start_date")+"", "");
	}
	
	public RedmineIssue setStart_date(String start_date) {
		dbObject.put("start_date", start_date);
		notifyChanged();
		return this;
	}
	public String getUpdate_date() {
		return parseString(dbObject.get("update_date")+"", "");
	}
	
	public RedmineIssue setUpdate_date(String update_date) {
		dbObject.put("update_date", update_date);
		notifyChanged();
		return this;
	}
	public String getDue_date() {
		return parseString(dbObject.get("due_date")+"", "");
	}
	
	public RedmineIssue setDue_date(String due_date) {
		dbObject.put("due_date", due_date);
		notifyChanged();
		return this;
	}
	
	
	public List<RedmineIssueRelation> getRelations() {
		if (relations == null) {
			relations = new PongoList<RedmineIssueRelation>(this, "relations", true);
		}
		return relations;
	}
	
	public RedmineIssue setAuthor(RedmineUser author) {
		if (this.author != author) {
			if (author == null) {
				dbObject.put("author", new BasicDBObject());
			}
			else {
				createReference("author", author);
			}
			this.author = author;
			notifyChanged();
		}
		return this;
	}
	
	public RedmineUser getAuthor() {
		if (author == null) {
			author = (RedmineUser) resolveReference("author");
		}
		return author;
	}
	public RedmineIssue setAssignedTo(RedmineUser assignedTo) {
		if (this.assignedTo != assignedTo) {
			if (assignedTo == null) {
				dbObject.put("assignedTo", new BasicDBObject());
			}
			else {
				createReference("assignedTo", assignedTo);
			}
			this.assignedTo = assignedTo;
			notifyChanged();
		}
		return this;
	}
	
	public RedmineUser getAssignedTo() {
		if (assignedTo == null) {
			assignedTo = (RedmineUser) resolveReference("assignedTo");
		}
		return assignedTo;
	}
	
	public RedmineCategory getCategory() {
		if (category == null && dbObject.containsField("category")) {
			category = (RedmineCategory) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("category"));
			category.setContainer(this);
		}
		return category;
	}
	
	public RedmineIssue setCategory(RedmineCategory category) {
		if (this.category != category) {
			if (category == null) {
				dbObject.removeField("category");
			}
			else {
				dbObject.put("category", category.getDbObject());
			}
			this.category = category;
			notifyChanged();
		}
		return this;
	}
	public RedmineFeature getFeature() {
		if (feature == null && dbObject.containsField("feature")) {
			feature = (RedmineFeature) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("feature"));
			feature.setContainer(this);
		}
		return feature;
	}
	
	public RedmineIssue setFeature(RedmineFeature feature) {
		if (this.feature != feature) {
			if (feature == null) {
				dbObject.removeField("feature");
			}
			else {
				dbObject.put("feature", feature.getDbObject());
			}
			this.feature = feature;
			notifyChanged();
		}
		return this;
	}
}
