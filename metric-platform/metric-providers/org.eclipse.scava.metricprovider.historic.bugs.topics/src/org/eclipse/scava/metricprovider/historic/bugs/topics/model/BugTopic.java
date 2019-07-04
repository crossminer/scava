package org.eclipse.scava.metricprovider.historic.bugs.topics.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugTopic extends Pongo {
	
	protected List<String> labels = null;
	
	
	public BugTopic() { 
		super();
		dbObject.put("labels", new BasicDBList());
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.topics.model.BugTopic");
		LABELS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.topics.model.BugTopic");
		NUMBEROFDOCUMENTS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.topics.model.BugTopic");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static NumericalQueryProducer NUMBEROFDOCUMENTS = new NumericalQueryProducer("numberOfDocuments");
	public static ArrayQueryProducer LABELS = new ArrayQueryProducer("labels");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTopic setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public int getNumberOfDocuments() {
		return parseInteger(dbObject.get("numberOfDocuments")+"", 0);
	}
	
	public BugTopic setNumberOfDocuments(int numberOfDocuments) {
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
	
	
	
}