package org.eclipse.scava.metricprovider.trans.topics.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupTopic extends Pongo {
	
	protected List<String> labels = null;
	protected List<String> articlesId = null;
	
	
	public NewsgroupTopic() { 
		super();
		dbObject.put("labels", new BasicDBList());
		dbObject.put("articlesId", new BasicDBList());
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.NewsgroupTopic");
		LABELS.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.NewsgroupTopic");
		NUMBEROFDOCUMENTS.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.NewsgroupTopic");
		ARTICLESID.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.NewsgroupTopic");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer NUMBEROFDOCUMENTS = new NumericalQueryProducer("numberOfDocuments");
	public static ArrayQueryProducer LABELS = new ArrayQueryProducer("labels");
	public static ArrayQueryProducer ARTICLESID = new ArrayQueryProducer("articlesId");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgroupTopic setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
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
	
	public List<String> getLabels() {
		if (labels == null) {
			labels = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("labels"));
		}
		return labels;
	}
	public List<String> getArticlesId() {
		if (articlesId == null) {
			articlesId = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("articlesId"));
		}
		return articlesId;
	}
	
	
	
}