package org.eclipse.scava.metricprovider.trans.topics.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugTrackerTopic extends Pongo {
	
	protected List<String> labels = null;
	protected List<String> commentsId = null;
	
	
	public BugTrackerTopic() { 
		super();
		dbObject.put("labels", new BasicDBList());
		dbObject.put("commentsId", new BasicDBList());
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.BugTrackerTopic");
		LABELS.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.BugTrackerTopic");
		NUMBEROFDOCUMENTS.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.BugTrackerTopic");
		COMMENTSID.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.BugTrackerTopic");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static NumericalQueryProducer NUMBEROFDOCUMENTS = new NumericalQueryProducer("numberOfDocuments");
	public static ArrayQueryProducer LABELS = new ArrayQueryProducer("labels");
	public static ArrayQueryProducer COMMENTSID = new ArrayQueryProducer("commentsId");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerTopic setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public int getNumberOfDocuments() {
		return parseInteger(dbObject.get("numberOfDocuments")+"", 0);
	}
	
	public BugTrackerTopic setNumberOfDocuments(int numberOfDocuments) {
		dbObject.put("numberOfDocuments", numberOfDocuments);
		notifyChanged();
		return this;
	}
	
	public List<String> getLabels() {
		if (labels == null) {
			labels = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("labels"));
		}
		return labels;
	}
	public List<String> getCommentsId() {
		if (commentsId == null) {
			commentsId = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("commentsId"));
		}
		return commentsId;
	}
	
	
	
}