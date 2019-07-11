package org.eclipse.scava.metricprovider.historic.newsgroups.topics.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class NewsgroupTopicsHistoricMetric extends Pongo {
	
	protected List<NewsgrpTopic> newsgrpTopics = null;
	
	
	public NewsgroupTopicsHistoricMetric() { 
		super();
		dbObject.put("newsgrpTopics", new BasicDBList());
	}
	
	
	
	
	
	public List<NewsgrpTopic> getNewsgrpTopics() {
		if (newsgrpTopics == null) {
			newsgrpTopics = new PongoList<NewsgrpTopic>(this, "newsgrpTopics", true);
		}
		return newsgrpTopics;
	}
	
	
}