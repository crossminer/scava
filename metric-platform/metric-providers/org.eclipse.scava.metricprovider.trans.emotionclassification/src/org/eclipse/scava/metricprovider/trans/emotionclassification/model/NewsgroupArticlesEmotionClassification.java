package org.eclipse.scava.metricprovider.trans.emotionclassification.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupArticlesEmotionClassification extends Pongo {
	
	protected List<String> emotions = null;
	
	
	public NewsgroupArticlesEmotionClassification() { 
		super();
		dbObject.put("emotions", new BasicDBList());
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.emotionclassification.model.NewsgroupArticlesEmotionClassification");
		ARTICLEID.setOwningType("org.eclipse.scava.metricprovider.trans.emotionclassification.model.NewsgroupArticlesEmotionClassification");
		EMOTIONS.setOwningType("org.eclipse.scava.metricprovider.trans.emotionclassification.model.NewsgroupArticlesEmotionClassification");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsGroupName"); 
	public static StringQueryProducer ARTICLEID = new StringQueryProducer("articleId"); 
	public static ArrayQueryProducer EMOTIONS = new ArrayQueryProducer("emotions");
	
	
	public String getNewsGroupName() {
		return parseString(dbObject.get("newsGroupName")+"", "");
	}
	
	public NewsgroupArticlesEmotionClassification setNewsGroupName(String newsGroupName) {
		dbObject.put("newsGroupName", newsGroupName);
		notifyChanged();
		return this;
	}
	public String getArticleId() {
		return parseString(dbObject.get("articleId")+"", "");
	}
	
	public NewsgroupArticlesEmotionClassification setArticleId(String articleId) {
		dbObject.put("articleId", articleId);
		notifyChanged();
		return this;
	}
	
	public List<String> getEmotions() {
		if (emotions == null) {
			emotions = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("emotions"));
		}
		return emotions;
	}
	
	
	
}