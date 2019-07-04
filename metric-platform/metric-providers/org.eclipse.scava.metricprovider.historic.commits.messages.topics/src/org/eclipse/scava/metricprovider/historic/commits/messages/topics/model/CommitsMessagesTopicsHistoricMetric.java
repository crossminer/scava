package org.eclipse.scava.metricprovider.historic.commits.messages.topics.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class CommitsMessagesTopicsHistoricMetric extends Pongo {
	
	protected List<CommitMessageTopic> commitMessageTopics = null;
	
	
	public CommitsMessagesTopicsHistoricMetric() { 
		super();
		dbObject.put("commitMessageTopics", new BasicDBList());
	}
	
	
	
	
	
	public List<CommitMessageTopic> getCommitMessageTopics() {
		if (commitMessageTopics == null) {
			commitMessageTopics = new PongoList<CommitMessageTopic>(this, "commitMessageTopics", true);
		}
		return commitMessageTopics;
	}
	
	
}