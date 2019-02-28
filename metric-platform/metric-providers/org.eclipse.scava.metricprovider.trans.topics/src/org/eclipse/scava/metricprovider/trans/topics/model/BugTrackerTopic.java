package org.eclipse.scava.metricprovider.trans.topics.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugTrackerTopic extends Pongo {
	
	
	
	public BugTrackerTopic() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.BugTrackerTopic");
		LABEL.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.BugTrackerTopic");
		NUMBEROFDOCUMENTS.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.BugTrackerTopic");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer LABEL = new StringQueryProducer("label"); 
	public static NumericalQueryProducer NUMBEROFDOCUMENTS = new NumericalQueryProducer("numberOfDocuments");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerTopic setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getLabel() {
		return parseString(dbObject.get("label")+"", "");
	}
	
	public BugTrackerTopic setLabel(String label) {
		dbObject.put("label", label);
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
	
	
	
	
}