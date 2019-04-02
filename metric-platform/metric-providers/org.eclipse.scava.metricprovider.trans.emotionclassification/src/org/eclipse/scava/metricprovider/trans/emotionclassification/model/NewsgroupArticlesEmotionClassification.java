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
		ARTICLENUMBER.setOwningType("org.eclipse.scava.metricprovider.trans.emotionclassification.model.NewsgroupArticlesEmotionClassification");
		EMOTIONS.setOwningType("org.eclipse.scava.metricprovider.trans.emotionclassification.model.NewsgroupArticlesEmotionClassification");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsGroupName"); 
	public static NumericalQueryProducer ARTICLENUMBER = new NumericalQueryProducer("articleNumber");
	public static ArrayQueryProducer EMOTIONS = new ArrayQueryProducer("emotions");
	
	
	public String getNewsGroupName() {
		return parseString(dbObject.get("newsGroupName")+"", "");
	}
	
	public NewsgroupArticlesEmotionClassification setNewsGroupName(String newsGroupName) {
		dbObject.put("newsGroupName", newsGroupName);
		notifyChanged();
		return this;
	}
	public long getArticleNumber() {
		return parseLong(dbObject.get("articleNumber")+"", 0);
	}
	
	public NewsgroupArticlesEmotionClassification setArticleNumber(long articleNumber) {
		dbObject.put("articleNumber", articleNumber);
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