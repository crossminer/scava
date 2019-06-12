package org.eclipse.scava.metricprovider.trans.commits.message.topics.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class CommitsTopic extends Pongo {
	
	
	
	public CommitsTopic() { 
		super();
		REPOSITORY.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitsTopic");
		LABEL.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitsTopic");
		NUMBEROFMESSAGES.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitsTopic");
	}
	
	public static StringQueryProducer REPOSITORY = new StringQueryProducer("repository"); 
	public static StringQueryProducer LABEL = new StringQueryProducer("label"); 
	public static NumericalQueryProducer NUMBEROFMESSAGES = new NumericalQueryProducer("numberOfMessages");
	
	
	public String getRepository() {
		return parseString(dbObject.get("repository")+"", "");
	}
	
	public CommitsTopic setRepository(String repository) {
		dbObject.put("repository", repository);
		notifyChanged();
		return this;
	}
	public String getLabel() {
		return parseString(dbObject.get("label")+"", "");
	}
	
	public CommitsTopic setLabel(String label) {
		dbObject.put("label", label);
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
	
	
	
	
}