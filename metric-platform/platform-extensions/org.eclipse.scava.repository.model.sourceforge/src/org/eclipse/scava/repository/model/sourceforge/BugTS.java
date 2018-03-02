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
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugTS extends Tracker {
	
	protected org.eclipse.scava.repository.model.Person assignee = null;
	protected org.eclipse.scava.repository.model.Person submitted = null;
	
	
	public BugTS() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.sourceforge.Tracker","org.eclipse.scava.repository.model.sourceforge.NamedElement");
		LOCATION.setOwningType("org.eclipse.scava.repository.model.sourceforge.BugTS");
		STATUS.setOwningType("org.eclipse.scava.repository.model.sourceforge.BugTS");
		DESCRIPTION.setOwningType("org.eclipse.scava.repository.model.sourceforge.BugTS");
		PRIORITY.setOwningType("org.eclipse.scava.repository.model.sourceforge.BugTS");
		RESOLUTIONSTATUS.setOwningType("org.eclipse.scava.repository.model.sourceforge.BugTS");
		BUGVISIBILITY.setOwningType("org.eclipse.scava.repository.model.sourceforge.BugTS");
	}
	
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static NumericalQueryProducer PRIORITY = new NumericalQueryProducer("priority");
	public static StringQueryProducer RESOLUTIONSTATUS = new StringQueryProducer("resolutionStatus"); 
	public static StringQueryProducer BUGVISIBILITY = new StringQueryProducer("bugVisibility"); 
	
	
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public BugTS setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public int getPriority() {
		return parseInteger(dbObject.get("priority")+"", 0);
	}
	
	public BugTS setPriority(int priority) {
		dbObject.put("priority", priority);
		notifyChanged();
		return this;
	}
	public String getResolutionStatus() {
		return parseString(dbObject.get("resolutionStatus")+"", "");
	}
	
	public BugTS setResolutionStatus(String resolutionStatus) {
		dbObject.put("resolutionStatus", resolutionStatus);
		notifyChanged();
		return this;
	}
	public String getBugVisibility() {
		return parseString(dbObject.get("bugVisibility")+"", "");
	}
	
	public BugTS setBugVisibility(String bugVisibility) {
		dbObject.put("bugVisibility", bugVisibility);
		notifyChanged();
		return this;
	}
	
	
	
	
	public org.eclipse.scava.repository.model.Person getAssignee() {
		if (assignee == null && dbObject.containsField("assignee")) {
			assignee = (org.eclipse.scava.repository.model.Person) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("assignee"));
		}
		return assignee;
	}
	
	public BugTS setAssignee(org.eclipse.scava.repository.model.Person assignee) {
		if (this.assignee != assignee) {
			if (assignee == null) {
				dbObject.removeField("assignee");
			}
			else {
				dbObject.put("assignee", assignee.getDbObject());
			}
			this.assignee = assignee;
			notifyChanged();
		}
		return this;
	}
	public org.eclipse.scava.repository.model.Person getSubmitted() {
		if (submitted == null && dbObject.containsField("submitted")) {
			submitted = (org.eclipse.scava.repository.model.Person) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("submitted"));
		}
		return submitted;
	}
	
	public BugTS setSubmitted(org.eclipse.scava.repository.model.Person submitted) {
		if (this.submitted != submitted) {
			if (submitted == null) {
				dbObject.removeField("submitted");
			}
			else {
				dbObject.put("submitted", submitted.getDbObject());
			}
			this.submitted = submitted;
			notifyChanged();
		}
		return this;
	}
}
