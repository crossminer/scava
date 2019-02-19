package org.eclipse.scava.metricprovider.trans.forums.emotions.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ForumEmotionsData extends Pongo {
	
	
	
	public ForumEmotionsData() { 
		super();
		FORUMID.setOwningType("org.eclipse.scava.metricprovider.trans.forums.emotions.model.ForumEmotionsData");
		TOPICID.setOwningType("org.eclipse.scava.metricprovider.trans.forums.emotions.model.ForumEmotionsData");
		NUMBEROFPOSTS.setOwningType("org.eclipse.scava.metricprovider.trans.forums.emotions.model.ForumEmotionsData");
		CUMULATIVENUMBEROFPOSTS.setOwningType("org.eclipse.scava.metricprovider.trans.forums.emotions.model.ForumEmotionsData");
	}
	
	public static StringQueryProducer FORUMID = new StringQueryProducer("forumId"); 
	public static StringQueryProducer TOPICID = new StringQueryProducer("topicId"); 
	public static NumericalQueryProducer NUMBEROFPOSTS = new NumericalQueryProducer("numberOfPosts");
	public static NumericalQueryProducer CUMULATIVENUMBEROFPOSTS = new NumericalQueryProducer("cumulativeNumberOfPosts");
	
	
	public String getForumId() {
		return parseString(dbObject.get("forumId")+"", "");
	}
	
	public ForumEmotionsData setForumId(String forumId) {
		dbObject.put("forumId", forumId);
		notifyChanged();
		return this;
	}
	public String getTopicId() {
		return parseString(dbObject.get("topicId")+"", "");
	}
	
	public ForumEmotionsData setTopicId(String topicId) {
		dbObject.put("topicId", topicId);
		notifyChanged();
		return this;
	}
	public int getNumberOfPosts() {
		return parseInteger(dbObject.get("numberOfPosts")+"", 0);
	}
	
	public ForumEmotionsData setNumberOfPosts(int numberOfPosts) {
		dbObject.put("numberOfPosts", numberOfPosts);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfPosts() {
		return parseInteger(dbObject.get("cumulativeNumberOfPosts")+"", 0);
	}
	
	public ForumEmotionsData setCumulativeNumberOfPosts(int cumulativeNumberOfPosts) {
		dbObject.put("cumulativeNumberOfPosts", cumulativeNumberOfPosts);
		notifyChanged();
		return this;
	}
	
	
	
	
}