package org.eclipse.scava.metricprovider.trans.commits.message.references.model;

import com.googlecode.pongo.runtime.PongoDB;
// protected region custom-imports on begin
// protected region custom-imports end
import com.mongodb.DB;

public class CommitsMessageReferenceTransMetric extends PongoDB {
	
	public CommitsMessageReferenceTransMetric() {}
	
	public CommitsMessageReferenceTransMetric(DB db) {
		setDb(db);
	}
	
	protected CommitMessageReferringToCollection commitsMessagesReferringTo = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public CommitMessageReferringToCollection getCommitsMessagesReferringTo() {
		return commitsMessagesReferringTo;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		commitsMessagesReferringTo = new CommitMessageReferringToCollection(db.getCollection("CommitsMessageReferenceTransMetric.commitsMessagesReferringTo"));
		pongoCollections.add(commitsMessagesReferringTo);
	}
}