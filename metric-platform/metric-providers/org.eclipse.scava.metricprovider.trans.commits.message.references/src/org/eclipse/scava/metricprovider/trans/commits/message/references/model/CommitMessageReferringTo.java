package org.eclipse.scava.metricprovider.trans.commits.message.references.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PrimitiveList;
import com.googlecode.pongo.runtime.querying.ArrayQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.BasicDBList;


public class CommitMessageReferringTo extends Pongo {
	
	protected List<String> bugsReferred = null;
	protected List<String> commitsReferred = null;
	
	
	public CommitMessageReferringTo() { 
		super();
		dbObject.put("bugsReferred", new BasicDBList());
		dbObject.put("commitsReferred", new BasicDBList());
		REPOSITORY.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.references.model.CommitMessageReferringTo");
		REVISION.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.references.model.CommitMessageReferringTo");
		BUGSREFERRED.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.references.model.CommitMessageReferringTo");
		COMMITSREFERRED.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.references.model.CommitMessageReferringTo");
	}
	
	public static StringQueryProducer REPOSITORY = new StringQueryProducer("repository"); 
	public static StringQueryProducer REVISION = new StringQueryProducer("revision"); 
	public static ArrayQueryProducer BUGSREFERRED = new ArrayQueryProducer("bugsReferred");
	public static ArrayQueryProducer COMMITSREFERRED = new ArrayQueryProducer("commitsReferred");
	
	
	public String getRepository() {
		return parseString(dbObject.get("repository")+"", "");
	}
	
	public CommitMessageReferringTo setRepository(String repository) {
		dbObject.put("repository", repository);
		notifyChanged();
		return this;
	}
	public String getRevision() {
		return parseString(dbObject.get("revision")+"", "");
	}
	
	public CommitMessageReferringTo setRevision(String revision) {
		dbObject.put("revision", revision);
		notifyChanged();
		return this;
	}
	
	public List<String> getBugsReferred() {
		if (bugsReferred == null) {
			bugsReferred = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("bugsReferred"));
		}
		return bugsReferred;
	}
	public List<String> getCommitsReferred() {
		if (commitsReferred == null) {
			commitsReferred = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("commitsReferred"));
		}
		return commitsReferred;
	}
	
	
	
}