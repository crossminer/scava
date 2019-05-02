package org.eclipse.scava.metricprovider.trans.bugs.references.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugReferringTo extends Pongo {
	
	protected List<String> bugsReferred = null;
	protected List<String> commitsReferred = null;
	
	
	public BugReferringTo() { 
		super();
		dbObject.put("bugsReferred", new BasicDBList());
		dbObject.put("commitsReferred", new BasicDBList());
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.references.model.BugReferringTo");
		BUGID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.references.model.BugReferringTo");
		COMMENTID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.references.model.BugReferringTo");
		BUGSREFERRED.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.references.model.BugReferringTo");
		COMMITSREFERRED.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.references.model.BugReferringTo");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer COMMENTID = new StringQueryProducer("commentId"); 
	public static ArrayQueryProducer BUGSREFERRED = new ArrayQueryProducer("bugsReferred");
	public static ArrayQueryProducer COMMITSREFERRED = new ArrayQueryProducer("commitsReferred");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugReferringTo setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugReferringTo setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getCommentId() {
		return parseString(dbObject.get("commentId")+"", "");
	}
	
	public BugReferringTo setCommentId(String commentId) {
		dbObject.put("commentId", commentId);
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