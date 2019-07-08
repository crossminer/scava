package org.eclipse.scava.metricprovider.historic.bugs.topics.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class BugsTopicsHistoricMetric extends Pongo {
	
	protected List<BugTopic> bugTopics = null;
	
	
	public BugsTopicsHistoricMetric() { 
		super();
		dbObject.put("bugTopics", new BasicDBList());
	}
	
	
	
	
	
	public List<BugTopic> getBugTopics() {
		if (bugTopics == null) {
			bugTopics = new PongoList<BugTopic>(this, "bugTopics", true);
		}
		return bugTopics;
	}
	
	
}