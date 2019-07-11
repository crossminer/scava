package org.eclipse.scava.metricprovider.trans.newsgroups.threads.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ThreadData extends Pongo {
	
	protected List<Long> articleNumbers = null;
	protected List<ArticleData> articles = null;
	
	
	public ThreadData() { 
		super();
		dbObject.put("articleNumbers", new BasicDBList());
		dbObject.put("articles", new BasicDBList());
		THREADID.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ThreadData");
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ThreadData");
		SUBJECT.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ThreadData");
		ARTICLENUMBERS.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ThreadData");
	}
	
	public static NumericalQueryProducer THREADID = new NumericalQueryProducer("threadId");
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static StringQueryProducer SUBJECT = new StringQueryProducer("subject"); 
	public static ArrayQueryProducer ARTICLENUMBERS = new ArrayQueryProducer("articleNumbers");
	
	
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
	
	public List<Long> getArticleNumbers() {
		if (articleNumbers == null) {
			articleNumbers = new PrimitiveList<Long>(this, (BasicDBList) dbObject.get("articleNumbers"));
		}
		return articleNumbers;
	}
	
	public List<ArticleData> getArticles() {
		if (articles == null) {
			articles = new PongoList<ArticleData>(this, "articles", true);
		}
		return articles;
	}
	
	
}