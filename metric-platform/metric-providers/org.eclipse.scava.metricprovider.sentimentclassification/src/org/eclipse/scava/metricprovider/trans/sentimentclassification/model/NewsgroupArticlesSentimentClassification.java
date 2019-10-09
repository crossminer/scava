package org.eclipse.scava.metricprovider.trans.sentimentclassification.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupArticlesSentimentClassification extends Pongo {
	
	
	
	public NewsgroupArticlesSentimentClassification() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.NewsgroupArticlesSentimentClassification");
		ARTICLEID.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.NewsgroupArticlesSentimentClassification");
		POLARITY.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.NewsgroupArticlesSentimentClassification");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsGroupName"); 
	public static StringQueryProducer ARTICLEID = new StringQueryProducer("articleId"); 
	public static StringQueryProducer POLARITY = new StringQueryProducer("polarity"); 
	
	
	public String getNewsGroupName() {
		return parseString(dbObject.get("newsGroupName")+"", "");
	}
	
	public NewsgroupArticlesSentimentClassification setNewsGroupName(String newsGroupName) {
		dbObject.put("newsGroupName", newsGroupName);
		notifyChanged();
		return this;
	}
	public String getArticleId() {
		return parseString(dbObject.get("articleId")+"", "");
	}
	
	public NewsgroupArticlesSentimentClassification setArticleId(String articleId) {
		dbObject.put("articleId", articleId);
		notifyChanged();
		return this;
	}
	public String getPolarity() {
		return parseString(dbObject.get("polarity")+"", "");
	}
	
	public NewsgroupArticlesSentimentClassification setPolarity(String polarity) {
		dbObject.put("polarity", polarity);
		notifyChanged();
		return this;
	}
	
	
	
	
}