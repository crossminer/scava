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
package org.eclipse.crossmeter.platform.communicationchannel.nntp.local.model;

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