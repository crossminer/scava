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
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugTrackerCommentsData extends Pongo {
	
	
	
	public BugTrackerCommentsData() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsData");
		BUGID.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsData");
		COMMENTID.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsData");
		CLASSIFICATIONRESULT.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsData");
		EMOTIONALDIMENSIONS.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsData");
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
