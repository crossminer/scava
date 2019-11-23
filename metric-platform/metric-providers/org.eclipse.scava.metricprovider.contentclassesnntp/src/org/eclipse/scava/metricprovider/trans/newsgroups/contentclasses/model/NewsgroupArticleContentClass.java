package org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupArticleContentClass extends Pongo {
	
	
	
	public NewsgroupArticleContentClass() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.model.NewsgroupArticleContentClass");
		ARTICLEID.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.model.NewsgroupArticleContentClass");
		CLASSLABEL.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.model.NewsgroupArticleContentClass");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static StringQueryProducer ARTICLEID = new StringQueryProducer("articleId"); 
	public static StringQueryProducer CLASSLABEL = new StringQueryProducer("classLabel"); 
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgroupArticleContentClass setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public String getArticleId() {
		return parseString(dbObject.get("articleId")+"", "");
	}
	
	public NewsgroupArticleContentClass setArticleId(String articleId) {
		dbObject.put("articleId", articleId);
		notifyChanged();
		return this;
	}
	public String getClassLabel() {
		return parseString(dbObject.get("classLabel")+"", "");
	}
	
	public NewsgroupArticleContentClass setClassLabel(String classLabel) {
		dbObject.put("classLabel", classLabel);
		notifyChanged();
		return this;
	}
	
	
	
	
}