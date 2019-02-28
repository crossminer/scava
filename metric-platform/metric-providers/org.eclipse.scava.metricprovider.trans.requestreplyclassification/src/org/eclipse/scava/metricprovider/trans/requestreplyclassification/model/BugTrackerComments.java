package org.eclipse.scava.metricprovider.trans.requestreplyclassification.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugTrackerComments extends Pongo {
	
	
	
	public BugTrackerComments() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.BugTrackerComments");
		BUGID.setOwningType("org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.BugTrackerComments");
		COMMENTID.setOwningType("org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.BugTrackerComments");
		CLASSIFICATIONRESULT.setOwningType("org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.BugTrackerComments");
		DATE.setOwningType("org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.BugTrackerComments");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer COMMENTID = new StringQueryProducer("commentId"); 
	public static StringQueryProducer CLASSIFICATIONRESULT = new StringQueryProducer("classificationResult"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerComments setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugTrackerComments setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getCommentId() {
		return parseString(dbObject.get("commentId")+"", "");
	}
	
	public BugTrackerComments setCommentId(String commentId) {
		dbObject.put("commentId", commentId);
		notifyChanged();
		return this;
	}
	public String getClassificationResult() {
		return parseString(dbObject.get("classificationResult")+"", "");
	}
	
	public BugTrackerComments setClassificationResult(String classificationResult) {
		dbObject.put("classificationResult", classificationResult);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public BugTrackerComments setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}