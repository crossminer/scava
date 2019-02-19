package org.eclipse.scava.metricprovider.historic.forums.emotions.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ForumEmotionData extends Pongo {
	
	
	
	public ForumEmotionData() { 
		super();
		FORUMID.setOwningType("org.eclipse.scava.metricprovider.historic.forums.emotions.model.ForumEmotionData");
		NUMBEROFPOSTS.setOwningType("org.eclipse.scava.metricprovider.historic.forums.emotions.model.ForumEmotionData");
		CUMULATIVENUMBEROFPOSTS.setOwningType("org.eclipse.scava.metricprovider.historic.forums.emotions.model.ForumEmotionData");
	}
	
	public static StringQueryProducer FORUMID = new StringQueryProducer("forumId"); 
	public static NumericalQueryProducer NUMBEROFPOSTS = new NumericalQueryProducer("numberOfPosts");
	public static NumericalQueryProducer CUMULATIVENUMBEROFPOSTS = new NumericalQueryProducer("cumulativeNumberOfPosts");
	
	
	public String getForumId() {
		return parseString(dbObject.get("forumId")+"", "");
	}
	
	public ForumEmotionData setForumId(String forumId) {
		dbObject.put("forumId", forumId);
		notifyChanged();
		return this;
	}
	public int getNumberOfPosts() {
		return parseInteger(dbObject.get("numberOfPosts")+"", 0);
	}
	
	public ForumEmotionData setNumberOfPosts(int numberOfPosts) {
		dbObject.put("numberOfPosts", numberOfPosts);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfPosts() {
		return parseInteger(dbObject.get("cumulativeNumberOfPosts")+"", 0);
	}
	
	public ForumEmotionData setCumulativeNumberOfPosts(int cumulativeNumberOfPosts) {
		dbObject.put("cumulativeNumberOfPosts", cumulativeNumberOfPosts);
		notifyChanged();
		return this;
	}
	
	
	
	
}