package org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupsMigrationIssue extends Pongo {
	
	protected List<String> changes = null;
	
	
	public NewsgroupsMigrationIssue() { 
		super();
		dbObject.put("changes", new BasicDBList());
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssue");
		THREADID.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssue");
		ARTICLEID.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssue");
		SUMMARY.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssue");
		CHANGES.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssue");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer THREADID = new NumericalQueryProducer("threadId");
	public static StringQueryProducer ARTICLEID = new StringQueryProducer("articleId"); 
	public static StringQueryProducer SUMMARY = new StringQueryProducer("summary"); 
	public static ArrayQueryProducer CHANGES = new ArrayQueryProducer("changes");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgroupsMigrationIssue setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public int getThreadId() {
		return parseInteger(dbObject.get("threadId")+"", 0);
	}
	
	public NewsgroupsMigrationIssue setThreadId(int threadId) {
		dbObject.put("threadId", threadId);
		notifyChanged();
		return this;
	}
	public String getArticleId() {
		return parseString(dbObject.get("articleId")+"", "");
	}
	
	public NewsgroupsMigrationIssue setArticleId(String articleId) {
		dbObject.put("articleId", articleId);
		notifyChanged();
		return this;
	}
	public String getSummary() {
		return parseString(dbObject.get("summary")+"", "");
	}
	
	public NewsgroupsMigrationIssue setSummary(String summary) {
		dbObject.put("summary", summary);
		notifyChanged();
		return this;
	}
	
	public List<String> getChanges() {
		if (changes == null) {
			changes = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("changes"));
		}
		return changes;
	}
	
	
	
}