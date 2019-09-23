package org.eclipse.scava.metricprovider.historic.newsgroups.topics.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PrimitiveList;
import com.googlecode.pongo.runtime.querying.ArrayQueryProducer;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.BasicDBList;


public class NewsgrpTopic extends Pongo {
	
	protected List<String> labels = null;
	protected List<Long> articlesId = null;
	
	
	public NewsgrpTopic() { 
		super();
		dbObject.put("labels", new BasicDBList());
		dbObject.put("articlesId", new BasicDBList());
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.topics.model.NewsgrpTopic");
		LABELS.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.topics.model.NewsgrpTopic");
		NUMBEROFDOCUMENTS.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.topics.model.NewsgrpTopic");
		ARTICLESID.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.topics.model.NewsgrpTopic");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer NUMBEROFDOCUMENTS = new NumericalQueryProducer("numberOfDocuments");
	public static ArrayQueryProducer LABELS = new ArrayQueryProducer("labels");
	public static ArrayQueryProducer ARTICLESID = new ArrayQueryProducer("articlesId");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgrpTopic setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public int getNumberOfDocuments() {
		return parseInteger(dbObject.get("numberOfDocuments")+"", 0);
	}
	
	public NewsgrpTopic setNumberOfDocuments(int numberOfDocuments) {
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
	public List<Long> getArticlesId() {
		if (articlesId == null) {
			articlesId = new PrimitiveList<Long>(this, (BasicDBList) dbObject.get("articlesId"));
		}
		return articlesId;
	}
	
	
	
}