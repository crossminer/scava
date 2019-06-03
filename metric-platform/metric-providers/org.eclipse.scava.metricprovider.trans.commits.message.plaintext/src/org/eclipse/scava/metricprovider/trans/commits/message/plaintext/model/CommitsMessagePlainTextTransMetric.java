package org.eclipse.scava.metricprovider.trans.commits.message.plaintext.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class CommitsMessagePlainTextTransMetric extends PongoDB {
	
	public CommitsMessagePlainTextTransMetric() {}
	
	public CommitsMessagePlainTextTransMetric(DB db) {
		setDb(db);
	}
	
	protected CommitMessagePlainTextCollection commitsMessagesPlainText = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public CommitMessagePlainTextCollection getCommitsMessagesPlainText() {
		return commitsMessagesPlainText;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		commitsMessagesPlainText = new CommitMessagePlainTextCollection(db.getCollection("CommitsMessagePlainTextTransMetric.commitsMessagesPlainText"));
		pongoCollections.add(commitsMessagesPlainText);
	}
}