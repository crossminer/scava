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
package org.eclipse.crossmeter.metricprovider.trans.bugs.bugmetadata.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class BugsBugMetadataTransMetric extends PongoDB {
	
	public BugsBugMetadataTransMetric() {}
	
	public BugsBugMetadataTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugDataCollection bugData = null;
	protected CommentDataCollection comments = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugDataCollection getBugData() {
		return bugData;
	}
	
	public CommentDataCollection getComments() {
		return comments;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugData = new BugDataCollection(db.getCollection("BugsBugMetadataTransMetric.bugData"));
		pongoCollections.add(bugData);
		comments = new CommentDataCollection(db.getCollection("BugsBugMetadataTransMetric.comments"));
		pongoCollections.add(comments);
	}
}