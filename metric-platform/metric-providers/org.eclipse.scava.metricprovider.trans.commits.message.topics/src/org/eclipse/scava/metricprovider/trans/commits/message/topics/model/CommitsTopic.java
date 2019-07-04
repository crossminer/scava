package org.eclipse.scava.metricprovider.trans.commits.message.topics.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class CommitsTopic extends Pongo {
	
	protected List<String> labels = null;
	protected List<CommitMessageId> commitsMessageId = null;
	
	
	public CommitsTopic() { 
		super();
		dbObject.put("labels", new BasicDBList());
		dbObject.put("commitsMessageId", new BasicDBList());
		REPOSITORY.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitsTopic");
		LABELS.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitsTopic");
		NUMBEROFMESSAGES.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitsTopic");
	}
	
	public static StringQueryProducer REPOSITORY = new StringQueryProducer("repository"); 
	public static NumericalQueryProducer NUMBEROFMESSAGES = new NumericalQueryProducer("numberOfMessages");
	public static ArrayQueryProducer LABELS = new ArrayQueryProducer("labels");
	
	
	public String getRepository() {
		return parseString(dbObject.get("repository")+"", "");
	}
	
	public CommitsTopic setRepository(String repository) {
		dbObject.put("repository", repository);
		notifyChanged();
		return this;
	}
	public int getNumberOfMessages() {
		return parseInteger(dbObject.get("numberOfMessages")+"", 0);
	}
	
	public CommitsTopic setNumberOfMessages(int numberOfMessages) {
		dbObject.put("numberOfMessages", numberOfMessages);
		notifyChanged();
		return this;
	}
	
	public List<String> getLabels() {
		if (labels == null) {
			labels = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("labels"));
		}
		return labels;
	}
	
	public List<CommitMessageId> getCommitsMessageId() {
		if (commitsMessageId == null) {
			commitsMessageId = new PongoList<CommitMessageId>(this, "commitsMessageId", true);
		}
		return commitsMessageId;
	}
	
	
}