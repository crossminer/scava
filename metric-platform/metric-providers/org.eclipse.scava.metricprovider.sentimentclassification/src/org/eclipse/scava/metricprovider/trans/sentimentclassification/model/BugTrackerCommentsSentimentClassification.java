package org.eclipse.scava.metricprovider.trans.sentimentclassification.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugTrackerCommentsSentimentClassification extends Pongo {
	
	
	
	public BugTrackerCommentsSentimentClassification() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsSentimentClassification");
		BUGID.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsSentimentClassification");
		COMMENTID.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsSentimentClassification");
		POLARITY.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsSentimentClassification");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer COMMENTID = new StringQueryProducer("commentId"); 
	public static StringQueryProducer POLARITY = new StringQueryProducer("polarity"); 
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerCommentsSentimentClassification setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugTrackerCommentsSentimentClassification setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getCommentId() {
		return parseString(dbObject.get("commentId")+"", "");
	}
	
	public BugTrackerCommentsSentimentClassification setCommentId(String commentId) {
		dbObject.put("commentId", commentId);
		notifyChanged();
		return this;
	}
	public String getPolarity() {
		return parseString(dbObject.get("polarity")+"", "");
	}
	
	public BugTrackerCommentsSentimentClassification setPolarity(String polarity) {
		dbObject.put("polarity", polarity);
		notifyChanged();
		return this;
	}
	
	
	
	
}