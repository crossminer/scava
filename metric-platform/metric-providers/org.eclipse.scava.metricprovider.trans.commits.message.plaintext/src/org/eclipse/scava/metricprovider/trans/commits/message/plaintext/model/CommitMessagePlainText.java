package org.eclipse.scava.metricprovider.trans.commits.message.plaintext.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class CommitMessagePlainText extends Pongo {
	
	protected List<String> plainText = null;
	
	
	public CommitMessagePlainText() { 
		super();
		dbObject.put("plainText", new BasicDBList());
		REPOSITORY.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.plaintext.model.CommitMessagePlainText");
		REVISION.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.plaintext.model.CommitMessagePlainText");
		PLAINTEXT.setOwningType("org.eclipse.scava.metricprovider.trans.commits.message.plaintext.model.CommitMessagePlainText");
	}
	
	public static StringQueryProducer REPOSITORY = new StringQueryProducer("repository"); 
	public static StringQueryProducer REVISION = new StringQueryProducer("revision"); 
	public static ArrayQueryProducer PLAINTEXT = new ArrayQueryProducer("plainText");
	
	
	public String getRepository() {
		return parseString(dbObject.get("repository")+"", "");
	}
	
	public CommitMessagePlainText setRepository(String repository) {
		dbObject.put("repository", repository);
		notifyChanged();
		return this;
	}
	public String getRevision() {
		return parseString(dbObject.get("revision")+"", "");
	}
	
	public CommitMessagePlainText setRevision(String revision) {
		dbObject.put("revision", revision);
		notifyChanged();
		return this;
	}
	
	public List<String> getPlainText() {
		if (plainText == null) {
			plainText = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("plainText"));
		}
		return plainText;
	}
	
	
	
}