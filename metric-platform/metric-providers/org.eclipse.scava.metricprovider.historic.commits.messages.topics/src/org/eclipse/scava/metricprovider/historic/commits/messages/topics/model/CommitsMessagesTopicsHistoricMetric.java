package org.eclipse.scava.metricprovider.historic.commits.messages.topics.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


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