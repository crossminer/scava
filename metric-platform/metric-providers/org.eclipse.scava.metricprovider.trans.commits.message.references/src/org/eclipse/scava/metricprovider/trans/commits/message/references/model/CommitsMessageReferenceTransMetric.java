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

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class CommitsMessageReferenceTransMetric extends PongoDB {
	
	public CommitsMessageReferenceTransMetric() {}
	
	public CommitsMessageReferenceTransMetric(DB db) {
		setDb(db);
	}
	
	protected CommitMessageReferringToCollection commitsMessagesReferringTo = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public CommitMessageReferringToCollection getCommitsMessagesReferringTo() {
		return commitsMessagesReferringTo;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		commitsMessagesReferringTo = new CommitMessageReferringToCollection(db.getCollection("CommitsMessageReferenceTransMetric.commitsMessagesReferringTo"));
		pongoCollections.add(commitsMessagesReferringTo);
	}
}