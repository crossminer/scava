package org.eclipse.scava.metricprovider.historic.commits.messages.topics.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class CommitMessageTopic extends Pongo {
	
	protected List<String> labels = null;
	
	
	public CommitMessageTopic() { 
		super();
		dbObject.put("labels", new BasicDBList());
		REPOSITORY.setOwningType("org.eclipse.scava.metricprovider.historic.commits.messages.topics.model.CommitMessageTopic");
		LABELS.setOwningType("org.eclipse.scava.metricprovider.historic.commits.messages.topics.model.CommitMessageTopic");
		NUMBEROFMESSAGES.setOwningType("org.eclipse.scava.metricprovider.historic.commits.messages.topics.model.CommitMessageTopic");
	}
	
	public static StringQueryProducer REPOSITORY = new StringQueryProducer("repository"); 
	public static NumericalQueryProducer NUMBEROFMESSAGES = new NumericalQueryProducer("numberOfMessages");
	public static ArrayQueryProducer LABELS = new ArrayQueryProducer("labels");
	
	
	public String getRepository() {
		return parseString(dbObject.get("repository")+"", "");
	}
	
	public CommitMessageTopic setRepository(String repository) {
		dbObject.put("repository", repository);
		notifyChanged();
		return this;
	}
	public int getNumberOfMessages() {
		return parseInteger(dbObject.get("numberOfMessages")+"", 0);
	}
	
	public CommitMessageTopic setNumberOfMessages(int numberOfMessages) {
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
	
	
	
}