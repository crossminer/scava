package org.eclipse.scava.metricprovider.trans.newsgroups.threads.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ThreadData extends Pongo {
	
	protected List<String> articlesId = null;
	protected List<ArticleData> articles = null;
	
	
	public ThreadData() { 
		super();
		dbObject.put("articlesId", new BasicDBList());
		dbObject.put("articles", new BasicDBList());
		THREADID.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ThreadData");
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ThreadData");
		SUBJECT.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ThreadData");
		ARTICLESID.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ThreadData");
	}
	
	public static NumericalQueryProducer THREADID = new NumericalQueryProducer("threadId");
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static StringQueryProducer SUBJECT = new StringQueryProducer("subject"); 
	public static ArrayQueryProducer ARTICLESID = new ArrayQueryProducer("articlesId");
	
	
	public int getThreadId() {
		return parseInteger(dbObject.get("threadId")+"", 0);
	}
	
	public ThreadData setThreadId(int threadId) {
		dbObject.put("threadId", threadId);
		notifyChanged();
		return this;
	}
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public ThreadData setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public String getSubject() {
		return parseString(dbObject.get("subject")+"", "");
	}
	
	public ThreadData setSubject(String subject) {
		dbObject.put("subject", subject);
		notifyChanged();
		return this;
	}
	
	public List<String> getArticlesId() {
		if (articlesId == null) {
			articlesId = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("articlesId"));
		}
		return articlesId;
	}
	
	public List<ArticleData> getArticles() {
		if (articles == null) {
			articles = new PongoList<ArticleData>(this, "articles", true);
		}
		return articles;
	}
	
	
}