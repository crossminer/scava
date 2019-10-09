package org.eclipse.scava.metricprovider.trans.topics.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupArticlesData extends Pongo {
	
	
	
	public NewsgroupArticlesData() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.NewsgroupArticlesData");
		ARTICLEID.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.NewsgroupArticlesData");
		SUBJECT.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.NewsgroupArticlesData");
		TEXT.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.NewsgroupArticlesData");
		DATE.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.NewsgroupArticlesData");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static StringQueryProducer ARTICLEID = new StringQueryProducer("articleId"); 
	public static StringQueryProducer SUBJECT = new StringQueryProducer("subject"); 
	public static StringQueryProducer TEXT = new StringQueryProducer("text"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgroupArticlesData setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public String getArticleId() {
		return parseString(dbObject.get("articleId")+"", "");
	}
	
	public NewsgroupArticlesData setArticleId(String articleId) {
		dbObject.put("articleId", articleId);
		notifyChanged();
		return this;
	}
	public String getSubject() {
		return parseString(dbObject.get("subject")+"", "");
	}
	
	public NewsgroupArticlesData setSubject(String subject) {
		dbObject.put("subject", subject);
		notifyChanged();
		return this;
	}
	public String getText() {
		return parseString(dbObject.get("text")+"", "");
	}
	
	public NewsgroupArticlesData setText(String text) {
		dbObject.put("text", text);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public NewsgroupArticlesData setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}