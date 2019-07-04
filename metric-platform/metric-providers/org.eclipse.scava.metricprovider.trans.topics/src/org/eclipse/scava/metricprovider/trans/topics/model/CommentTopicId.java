package org.eclipse.scava.metricprovider.trans.topics.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class CommentTopicId extends Pongo {
	
	
	
	public CommentTopicId() { 
		super();
		BUGID.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.CommentTopicId");
		COMMENTID.setOwningType("org.eclipse.scava.metricprovider.trans.topics.model.CommentTopicId");
	}
	
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer COMMENTID = new StringQueryProducer("commentId"); 
	
	
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public CommentTopicId setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getCommentId() {
		return parseString(dbObject.get("commentId")+"", "");
	}
	
	public CommentTopicId setCommentId(String commentId) {
		dbObject.put("commentId", commentId);
		notifyChanged();
		return this;
	}
	
	
	
	
}