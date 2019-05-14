package org.eclipse.scava.metricprovider.trans.emotionclassification.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ForumPostEmotionClassification extends Pongo {
	
	protected List<String> emotions = null;
	
	
	public ForumPostEmotionClassification() { 
		super();
		dbObject.put("emotions", new BasicDBList());
		FORUMID.setOwningType("org.eclipse.scava.metricprovider.trans.emotionclassification.model.ForumPostEmotionClassification");
		TOPICID.setOwningType("org.eclipse.scava.metricprovider.trans.emotionclassification.model.ForumPostEmotionClassification");
		POSTID.setOwningType("org.eclipse.scava.metricprovider.trans.emotionclassification.model.ForumPostEmotionClassification");
		EMOTIONS.setOwningType("org.eclipse.scava.metricprovider.trans.emotionclassification.model.ForumPostEmotionClassification");
	}
	
	public static StringQueryProducer FORUMID = new StringQueryProducer("forumId"); 
	public static StringQueryProducer TOPICID = new StringQueryProducer("topicId"); 
	public static StringQueryProducer POSTID = new StringQueryProducer("postId"); 
	public static ArrayQueryProducer EMOTIONS = new ArrayQueryProducer("emotions");
	
	
	public String getForumId() {
		return parseString(dbObject.get("forumId")+"", "");
	}
	
	public ForumPostEmotionClassification setForumId(String forumId) {
		dbObject.put("forumId", forumId);
		notifyChanged();
		return this;
	}
	public String getTopicId() {
		return parseString(dbObject.get("topicId")+"", "");
	}
	
	public ForumPostEmotionClassification setTopicId(String topicId) {
		dbObject.put("topicId", topicId);
		notifyChanged();
		return this;
	}
	public String getPostId() {
		return parseString(dbObject.get("postId")+"", "");
	}
	
	public ForumPostEmotionClassification setPostId(String postId) {
		dbObject.put("postId", postId);
		notifyChanged();
		return this;
	}
	
	public List<String> getEmotions() {
		if (emotions == null) {
			emotions = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("emotions"));
		}
		return emotions;
	}
	
	
	
}