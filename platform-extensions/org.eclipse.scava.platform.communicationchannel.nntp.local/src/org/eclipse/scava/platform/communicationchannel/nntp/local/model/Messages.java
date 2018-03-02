/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.nntp.local.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class Messages extends PongoDB {
	
	public Messages() {}
	
	public Messages(DB db) {
		setDb(db);
	}
	
	protected NewsgroupDataCollection newsgroup = null;
	protected ArticleDataCollection articles = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public NewsgroupDataCollection getNewsgroup() {
		return newsgroup;
	}
	
	public ArticleDataCollection getArticles() {
		return articles;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newsgroup = new NewsgroupDataCollection(db.getCollection("Messages.newsgroup"));
		pongoCollections.add(newsgroup);
		articles = new ArticleDataCollection(db.getCollection("Messages.articles"));
		pongoCollections.add(articles);
	}
}
