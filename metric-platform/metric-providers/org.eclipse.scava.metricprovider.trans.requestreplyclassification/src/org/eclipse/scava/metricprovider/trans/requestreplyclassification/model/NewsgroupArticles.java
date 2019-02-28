package org.eclipse.scava.metricprovider.trans.requestreplyclassification.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupArticles extends Pongo {
	
	
	
	public NewsgroupArticles() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.NewsgroupArticles");
		ARTICLENUMBER.setOwningType("org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.NewsgroupArticles");
		CLASSIFICATIONRESULT.setOwningType("org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.NewsgroupArticles");
		DATE.setOwningType("org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.NewsgroupArticles");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer ARTICLENUMBER = new NumericalQueryProducer("articleNumber");
	public static StringQueryProducer CLASSIFICATIONRESULT = new StringQueryProducer("classificationResult"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgroupArticles setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public long getArticleNumber() {
		return parseLong(dbObject.get("articleNumber")+"", 0);
	}
	
	public NewsgroupArticles setArticleNumber(long articleNumber) {
		dbObject.put("articleNumber", articleNumber);
		notifyChanged();
		return this;
	}
	public String getClassificationResult() {
		return parseString(dbObject.get("classificationResult")+"", "");
	}
	
	public NewsgroupArticles setClassificationResult(String classificationResult) {
		dbObject.put("classificationResult", classificationResult);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public NewsgroupArticles setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}