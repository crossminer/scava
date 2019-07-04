package org.eclipse.scava.metricprovider.trans.topics.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class ArticleTopicId extends Pongo {
	
	
	
	public ArticleTopicId() { 
		super();
		ARTICLENUMBER.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.ArticleTopicId");
	}
	
	public static NumericalQueryProducer ARTICLENUMBER = new NumericalQueryProducer("articleNumber");
	
	
	public long getArticleNumber() {
		return parseLong(dbObject.get("articleNumber")+"", 0);
	}
	
	public ArticleTopicId setArticleNumber(long articleNumber) {
		dbObject.put("articleNumber", articleNumber);
		notifyChanged();
		return this;
	}
	
	
	
	
}