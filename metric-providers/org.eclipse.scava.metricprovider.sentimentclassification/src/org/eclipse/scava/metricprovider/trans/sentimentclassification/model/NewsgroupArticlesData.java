/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.sentimentclassification.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupArticlesData extends Pongo {
	
	
	
	public NewsgroupArticlesData() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.NewsgroupArticlesData");
		ARTICLENUMBER.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.NewsgroupArticlesData");
		CLASSIFICATIONRESULT.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.NewsgroupArticlesData");
		EMOTIONALDIMENSIONS.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.NewsgroupArticlesData");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsGroupName"); 
	public static NumericalQueryProducer ARTICLENUMBER = new NumericalQueryProducer("articleNumber");
	public static StringQueryProducer CLASSIFICATIONRESULT = new StringQueryProducer("classificationResult"); 
	public static StringQueryProducer EMOTIONALDIMENSIONS = new StringQueryProducer("emotionalDimensions"); 
	
	
	public String getNewsGroupName() {
		return parseString(dbObject.get("newsGroupName")+"", "");
	}
	
	public NewsgroupArticlesData setNewsGroupName(String newsGroupName) {
		dbObject.put("newsGroupName", newsGroupName);
		notifyChanged();
		return this;
	}
	public int getArticleNumber() {
		return parseInteger(dbObject.get("articleNumber")+"", 0);
	}
	
	public NewsgroupArticlesData setArticleNumber(int articleNumber) {
		dbObject.put("articleNumber", articleNumber);
		notifyChanged();
		return this;
	}
	public String getClassificationResult() {
		return parseString(dbObject.get("classificationResult")+"", "");
	}
	
	public NewsgroupArticlesData setClassificationResult(String classificationResult) {
		dbObject.put("classificationResult", classificationResult);
		notifyChanged();
		return this;
	}
	public String getEmotionalDimensions() {
		return parseString(dbObject.get("emotionalDimensions")+"", "");
	}
	
	public NewsgroupArticlesData setEmotionalDimensions(String emotionalDimensions) {
		dbObject.put("emotionalDimensions", emotionalDimensions);
		notifyChanged();
		return this;
	}
	
	
	
	
}
