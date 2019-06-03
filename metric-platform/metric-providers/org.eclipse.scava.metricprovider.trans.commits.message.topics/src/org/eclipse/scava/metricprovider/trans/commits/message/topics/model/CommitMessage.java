package org.eclipse.scava.metricprovider.trans.commits.message.topics.model;

import java.util.Date;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class CommitMessage extends Pongo {
	
	
	
	public CommitMessage() { 
		super();
		REPOSITORY.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitMessage");
		REVISION.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitMessage");
		SUBJECT.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitMessage");
		MESSAGE.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitMessage");
		DATE.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitMessage");
	}
	
	public static StringQueryProducer REPOSITORY = new StringQueryProducer("repository"); 
	public static StringQueryProducer REVISION = new StringQueryProducer("revision"); 
	public static StringQueryProducer SUBJECT = new StringQueryProducer("subject"); 
	public static StringQueryProducer MESSAGE = new StringQueryProducer("message"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getRepository() {
		return parseString(dbObject.get("repository")+"", "");
	}
	
	public CommitMessage setRepository(String repository) {
		dbObject.put("repository", repository);
		notifyChanged();
		return this;
	}
	public String getRevision() {
		return parseString(dbObject.get("revision")+"", "");
	}
	
	public CommitMessage setRevision(String revision) {
		dbObject.put("revision", revision);
		notifyChanged();
		return this;
	}
	public String getSubject() {
		return parseString(dbObject.get("subject")+"", "");
	}
	
	public CommitMessage setSubject(String subject) {
		dbObject.put("subject", subject);
		notifyChanged();
		return this;
	}
	public String getMessage() {
		return parseString(dbObject.get("message")+"", "");
	}
	
	public CommitMessage setMessage(String message) {
		dbObject.put("message", message);
		notifyChanged();
		return this;
	}
	public Date getDate() {
		return parseDate(dbObject.get("date")+"", null);
	}
	
	public CommitMessage setDate(Date date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}