/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.sentimentclassification.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugTrackerCommentsData extends Pongo {
	
	
	
	public BugTrackerCommentsData() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.crossmeter.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsData");
		BUGID.setOwningType("org.eclipse.crossmeter.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsData");
		COMMENTID.setOwningType("org.eclipse.crossmeter.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsData");
		CLASSIFICATIONRESULT.setOwningType("org.eclipse.crossmeter.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsData");
		EMOTIONALDIMENSIONS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer COMMENTID = new StringQueryProducer("commentId"); 
	public static StringQueryProducer CLASSIFICATIONRESULT = new StringQueryProducer("classificationResult"); 
	public static StringQueryProducer EMOTIONALDIMENSIONS = new StringQueryProducer("emotionalDimensions"); 
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerCommentsData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugTrackerCommentsData setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getCommentId() {
		return parseString(dbObject.get("commentId")+"", "");
	}
	
	public BugTrackerCommentsData setCommentId(String commentId) {
		dbObject.put("commentId", commentId);
		notifyChanged();
		return this;
	}
	public String getClassificationResult() {
		return parseString(dbObject.get("classificationResult")+"", "");
	}
	
	public BugTrackerCommentsData setClassificationResult(String classificationResult) {
		dbObject.put("classificationResult", classificationResult);
		notifyChanged();
		return this;
	}
	public String getEmotionalDimensions() {
		return parseString(dbObject.get("emotionalDimensions")+"", "");
	}
	
	public BugTrackerCommentsData setEmotionalDimensions(String emotionalDimensions) {
		dbObject.put("emotionalDimensions", emotionalDimensions);
		notifyChanged();
		return this;
	}
	
	
	
	
}