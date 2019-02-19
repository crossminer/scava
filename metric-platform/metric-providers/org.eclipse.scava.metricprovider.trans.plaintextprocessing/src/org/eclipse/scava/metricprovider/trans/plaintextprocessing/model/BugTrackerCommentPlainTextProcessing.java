package org.eclipse.scava.metricprovider.trans.plaintextprocessing.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugTrackerCommentPlainTextProcessing extends Pongo {
	
	protected List<String> plainText = null;
	
	
	public BugTrackerCommentPlainTextProcessing() { 
		super();
		dbObject.put("plainText", new BasicDBList());
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.BugTrackerCommentPlainTextProcessing");
		BUGID.setOwningType("org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.BugTrackerCommentPlainTextProcessing");
		COMMENTID.setOwningType("org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.BugTrackerCommentPlainTextProcessing");
		PLAINTEXT.setOwningType("org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.BugTrackerCommentPlainTextProcessing");
		HADREPLIES.setOwningType("org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.BugTrackerCommentPlainTextProcessing");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer COMMENTID = new StringQueryProducer("commentId"); 
	public static StringQueryProducer HADREPLIES = new StringQueryProducer("hadReplies"); 
	public static ArrayQueryProducer PLAINTEXT = new ArrayQueryProducer("plainText");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerCommentPlainTextProcessing setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugTrackerCommentPlainTextProcessing setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getCommentId() {
		return parseString(dbObject.get("commentId")+"", "");
	}
	
	public BugTrackerCommentPlainTextProcessing setCommentId(String commentId) {
		dbObject.put("commentId", commentId);
		notifyChanged();
		return this;
	}
	public boolean getHadReplies() {
		return parseBoolean(dbObject.get("hadReplies")+"", false);
	}
	
	public BugTrackerCommentPlainTextProcessing setHadReplies(boolean hadReplies) {
		dbObject.put("hadReplies", hadReplies);
		notifyChanged();
		return this;
	}
	
	public List<String> getPlainText() {
		if (plainText == null) {
			plainText = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("plainText"));
		}
		return plainText;
	}
	
	public BugTrackerCommentPlainTextProcessing setPlainText(List<String> plainText) {
		dbObject.put("plainText", plainText);
		this.plainText = plainText;
		notifyChanged();
		return this;	
	}
	
}