/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.articles.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class NewsgroupsArticlesTransMetric extends PongoDB {
	
	public NewsgroupsArticlesTransMetric() {}
	
	public NewsgroupsArticlesTransMetric(DB db) {
		setDb(db);
	}
	
	protected NewsgroupDataCollection newsgroups = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public NewsgroupDataCollection getNewsgroups() {
		return newsgroups;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newsgroups = new NewsgroupDataCollection(db.getCollection("NewsgroupsArticlesTransMetric.newsgroups"));
		pongoCollections.add(newsgroups);
	}
}
