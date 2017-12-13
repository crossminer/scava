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
package org.eclipse.crossmeter.metricprovider.trans.newsgroups.threads.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ThreadData extends Pongo {
	
	protected List<ArticleData> articles = null;
	
	
	public ThreadData() { 
		super();
		dbObject.put("articles", new BasicDBList());
		THREADID.setOwningType("org.eclipse.crossmeter.metricprovider.trans.newsgroups.threads.model.ThreadData");
	}
	
	public static NumericalQueryProducer THREADID = new NumericalQueryProducer("threadId");
	
	
	public int getThreadId() {
		return parseInteger(dbObject.get("threadId")+"", 0);
	}
	
	public ThreadData setThreadId(int threadId) {
		dbObject.put("threadId", threadId);
		notifyChanged();
		return this;
	}
	
	
	public List<ArticleData> getArticles() {
		if (articles == null) {
			articles = new PongoList<ArticleData>(this, "articles", true);
		}
		return articles;
	}
	
	
}