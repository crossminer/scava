package org.eclipse.scava.metricprovider.historic.commits.messages.topics.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class CommitMessageTopic extends Pongo {
	
	
	
	public CommitMessageTopic() { 
		super();
		REPOSITORY.setOwningType("org.eclipse.scava.metricprovider.historic.commits.messages.topics.model.CommitMessageTopic");
		LABEL.setOwningType("org.eclipse.scava.metricprovider.historic.commits.messages.topics.model.CommitMessageTopic");
		NUMBEROFMESSAGES.setOwningType("org.eclipse.scava.metricprovider.historic.commits.messages.topics.model.CommitMessageTopic");
	}
	
	public static StringQueryProducer REPOSITORY = new StringQueryProducer("repository"); 
	public static StringQueryProducer LABEL = new StringQueryProducer("label"); 
	public static NumericalQueryProducer NUMBEROFMESSAGES = new NumericalQueryProducer("numberOfMessages");
	
	
	public String getRepository() {
		return parseString(dbObject.get("repository")+"", "");
	}
	
	public CommitMessageTopic setRepository(String repository) {
		dbObject.put("repository", repository);
		notifyChanged();
		return this;
	}
	public String getLabel() {
		return parseString(dbObject.get("label")+"", "");
	}
	
	public CommitMessageTopic setLabel(String label) {
		dbObject.put("label", label);
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
	
	
	
	
}