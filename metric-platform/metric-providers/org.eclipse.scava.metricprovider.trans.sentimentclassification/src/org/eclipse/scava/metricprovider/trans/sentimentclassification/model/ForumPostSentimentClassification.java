package org.eclipse.scava.metricprovider.trans.sentimentclassification.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ForumPostSentimentClassification extends Pongo {
	
	
	
	public ForumPostSentimentClassification() { 
		super();
		FORUMID.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.ForumPostSentimentClassification");
		TOPICID.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.ForumPostSentimentClassification");
		POSTID.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.ForumPostSentimentClassification");
		POLARITY.setOwningType("org.eclipse.scava.metricprovider.trans.sentimentclassification.model.ForumPostSentimentClassification");
	}
	
	public static StringQueryProducer FORUMID = new StringQueryProducer("forumId"); 
	public static StringQueryProducer TOPICID = new StringQueryProducer("topicId"); 
	public static StringQueryProducer POSTID = new StringQueryProducer("postId"); 
	public static StringQueryProducer POLARITY = new StringQueryProducer("polarity"); 
	
	
	public String getForumId() {
		return parseString(dbObject.get("forumId")+"", "");
	}
	
	public ForumPostSentimentClassification setForumId(String forumId) {
		dbObject.put("forumId", forumId);
		notifyChanged();
		return this;
	}
	public String getTopicId() {
		return parseString(dbObject.get("topicId")+"", "");
	}
	
	public ForumPostSentimentClassification setTopicId(String topicId) {
		dbObject.put("topicId", topicId);
		notifyChanged();
		return this;
	}
	public String getPostId() {
		return parseString(dbObject.get("postId")+"", "");
	}
	
	public ForumPostSentimentClassification setPostId(String postId) {
		dbObject.put("postId", postId);
		notifyChanged();
		return this;
	}
	public String getPolarity() {
		return parseString(dbObject.get("polarity")+"", "");
	}
	
	public ForumPostSentimentClassification setPolarity(String polarity) {
		dbObject.put("polarity", polarity);
		notifyChanged();
		return this;
	}
	
	
	
	
}