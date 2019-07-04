package org.eclipse.scava.metricprovider.trans.commits.message.topics.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class CommitMessageId extends Pongo {
	
	
	
	public CommitMessageId() { 
		super();
		REVISION.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitMessageId");
	}
	
	public static StringQueryProducer REVISION = new StringQueryProducer("revision"); 
	
	
	public String getRevision() {
		return parseString(dbObject.get("revision")+"", "");
	}
	
	public CommitMessageId setRevision(String revision) {
		dbObject.put("revision", revision);
		notifyChanged();
		return this;
	}
	
	
	
	
}