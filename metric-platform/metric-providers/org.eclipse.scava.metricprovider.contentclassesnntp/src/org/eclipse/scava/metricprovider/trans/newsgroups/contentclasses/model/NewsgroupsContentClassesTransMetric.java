package org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class NewsgroupsContentClassesTransMetric extends PongoDB {
	
	public NewsgroupsContentClassesTransMetric() {}
	
	public NewsgroupsContentClassesTransMetric(DB db) {
		setDb(db);
	}
	
	protected NewsgroupDataCollection newsgroups = null;
	protected ContentClassCollection contentClasses = null;
	protected NewsgroupArticleContentClassCollection articlesContentClass = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public NewsgroupDataCollection getNewsgroups() {
		return newsgroups;
	}
	
	public ContentClassCollection getContentClasses() {
		return contentClasses;
	}
	
	public NewsgroupArticleContentClassCollection getArticlesContentClass() {
		return articlesContentClass;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newsgroups = new NewsgroupDataCollection(db.getCollection("NewsgroupsContentClassesTransMetric.newsgroups"));
		pongoCollections.add(newsgroups);
		contentClasses = new ContentClassCollection(db.getCollection("NewsgroupsContentClassesTransMetric.contentClasses"));
		pongoCollections.add(contentClasses);
		articlesContentClass = new NewsgroupArticleContentClassCollection(db.getCollection("NewsgroupsContentClassesTransMetric.articlesContentClass"));
		pongoCollections.add(articlesContentClass);
	}
}