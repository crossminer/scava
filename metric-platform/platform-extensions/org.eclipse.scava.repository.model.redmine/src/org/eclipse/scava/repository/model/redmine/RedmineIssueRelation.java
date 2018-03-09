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
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class RedmineIssueRelation extends Pongo {
	
	protected RedmineIssue relatedIssue = null;
	
	
	public RedmineIssueRelation() { 
		super();
		dbObject.put("relatedIssue", new BasicDBObject());
		TYPE.setOwningType("org.eclipse.scava.repository.model.redmine.RedmineIssueRelation");
	}
	
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	
	
	public String getType() {
		return parseString(dbObject.get("type")+"", "");
	}
	
	public RedmineIssueRelation setType(String type) {
		dbObject.put("type", type);
		notifyChanged();
		return this;
	}
	
	
	
	public RedmineIssueRelation setRelatedIssue(RedmineIssue relatedIssue) {
		if (this.relatedIssue != relatedIssue) {
			if (relatedIssue == null) {
				dbObject.put("relatedIssue", new BasicDBObject());
			}
			else {
				createReference("relatedIssue", relatedIssue);
			}
			this.relatedIssue = relatedIssue;
			notifyChanged();
		}
		return this;
	}
	
	public RedmineIssue getRelatedIssue() {
		if (relatedIssue == null) {
			relatedIssue = (RedmineIssue) resolveReference("relatedIssue");
		}
		return relatedIssue;
	}
	
}
