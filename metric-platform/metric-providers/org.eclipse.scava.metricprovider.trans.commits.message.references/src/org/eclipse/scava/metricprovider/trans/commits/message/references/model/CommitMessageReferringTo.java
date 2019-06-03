/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.commits.message.references.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class CommitMessageReferringTo extends Pongo {
	
	protected List<String> bugsReferred = null;
	protected List<String> commitsReferred = null;
	
	
	public CommitMessageReferringTo() { 
		super();
		dbObject.put("bugsReferred", new BasicDBList());
		dbObject.put("commitsReferred", new BasicDBList());
		REPOSITORY.setOwningType("org.eclipse.scava.metricprovider.trans.commits.messagereferences.model.CommitMessageReferringTo");
		REVISION.setOwningType("org.eclipse.scava.metricprovider.trans.commits.messagereferences.model.CommitMessageReferringTo");
		BUGSREFERRED.setOwningType("org.eclipse.scava.metricprovider.trans.commits.messagereferences.model.CommitMessageReferringTo");
		COMMITSREFERRED.setOwningType("org.eclipse.scava.metricprovider.trans.commits.messagereferences.model.CommitMessageReferringTo");
	}
	
	public static StringQueryProducer REPOSITORY = new StringQueryProducer("repository"); 
	public static StringQueryProducer REVISION = new StringQueryProducer("revision"); 
	public static ArrayQueryProducer BUGSREFERRED = new ArrayQueryProducer("bugsReferred");
	public static ArrayQueryProducer COMMITSREFERRED = new ArrayQueryProducer("commitsReferred");
	
	
	public String getRepository() {
		return parseString(dbObject.get("repository")+"", "");
	}
	
	public CommitMessageReferringTo setRepository(String repository) {
		dbObject.put("repository", repository);
		notifyChanged();
		return this;
	}
	public String getRevision() {
		return parseString(dbObject.get("revision")+"", "");
	}
	
	public CommitMessageReferringTo setRevision(String revision) {
		dbObject.put("revision", revision);
		notifyChanged();
		return this;
	}
	
	public List<String> getBugsReferred() {
		if (bugsReferred == null) {
			bugsReferred = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("bugsReferred"));
		}
		return bugsReferred;
	}
	public List<String> getCommitsReferred() {
		if (commitsReferred == null) {
			commitsReferred = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("commitsReferred"));
		}
		return commitsReferred;
	}
	
	
	
}