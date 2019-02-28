package org.eclipse.scava.metricprovider.trans.detectingcode.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ForumPostDetectingCode extends Pongo {
	
	
	
	public ForumPostDetectingCode() { 
		super();
		FORUMID.setOwningType("org.eclipse.scava.metricprovider.trans.detectingcode.model.ForumPostDetectingCode");
		TOPICID.setOwningType("org.eclipse.scava.metricprovider.trans.detectingcode.model.ForumPostDetectingCode");
		POSTID.setOwningType("org.eclipse.scava.metricprovider.trans.detectingcode.model.ForumPostDetectingCode");
		NATURALLANGUAGE.setOwningType("org.eclipse.scava.metricprovider.trans.detectingcode.model.ForumPostDetectingCode");
		CODE.setOwningType("org.eclipse.scava.metricprovider.trans.detectingcode.model.ForumPostDetectingCode");
	}
	
	public static StringQueryProducer FORUMID = new StringQueryProducer("forumId"); 
	public static StringQueryProducer TOPICID = new StringQueryProducer("topicId"); 
	public static StringQueryProducer POSTID = new StringQueryProducer("postId"); 
	public static StringQueryProducer NATURALLANGUAGE = new StringQueryProducer("naturalLanguage"); 
	public static StringQueryProducer CODE = new StringQueryProducer("code"); 
	
	
	public String getForumId() {
		return parseString(dbObject.get("forumId")+"", "");
	}
	
	public ForumPostDetectingCode setForumId(String forumId) {
		dbObject.put("forumId", forumId);
		notifyChanged();
		return this;
	}
	public String getTopicId() {
		return parseString(dbObject.get("topicId")+"", "");
	}
	
	public ForumPostDetectingCode setTopicId(String topicId) {
		dbObject.put("topicId", topicId);
		notifyChanged();
		return this;
	}
	public String getPostId() {
		return parseString(dbObject.get("postId")+"", "");
	}
	
	public ForumPostDetectingCode setPostId(String postId) {
		dbObject.put("postId", postId);
		notifyChanged();
		return this;
	}
	public String getNaturalLanguage() {
		return parseString(dbObject.get("naturalLanguage")+"", "");
	}
	
	public ForumPostDetectingCode setNaturalLanguage(String naturalLanguage) {
		dbObject.put("naturalLanguage", naturalLanguage);
		notifyChanged();
		return this;
	}
	public String getCode() {
		return parseString(dbObject.get("code")+"", "");
	}
	
	public ForumPostDetectingCode setCode(String code) {
		dbObject.put("code", code);
		notifyChanged();
		return this;
	}
	
	
	
	
}