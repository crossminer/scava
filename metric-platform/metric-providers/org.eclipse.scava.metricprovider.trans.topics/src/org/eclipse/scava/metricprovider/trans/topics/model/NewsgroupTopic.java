package org.eclipse.scava.metricprovider.trans.topics.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupTopic extends Pongo {
	
	
	
	public NewsgroupTopic() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.NewsgroupTopic");
		LABEL.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.NewsgroupTopic");
		NUMBEROFDOCUMENTS.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.NewsgroupTopic");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static StringQueryProducer LABEL = new StringQueryProducer("label"); 
	public static NumericalQueryProducer NUMBEROFDOCUMENTS = new NumericalQueryProducer("numberOfDocuments");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgroupTopic setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public String getLabel() {
		return parseString(dbObject.get("label")+"", "");
	}
	
	public NewsgroupTopic setLabel(String label) {
		dbObject.put("label", label);
		notifyChanged();
		return this;
	}
	public int getNumberOfDocuments() {
		return parseInteger(dbObject.get("numberOfDocuments")+"", 0);
	}
	
	public NewsgroupTopic setNumberOfDocuments(int numberOfDocuments) {
		dbObject.put("numberOfDocuments", numberOfDocuments);
		notifyChanged();
		return this;
	}
	
	
	
	
}