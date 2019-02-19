/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.emotionclassification.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ForumPostDetectingEmotionClassification extends Pongo {
	
	protected List<String> emotions = null;
	
	
	public ForumPostDetectingEmotionClassification() { 
		super();
		dbObject.put("emotions", new BasicDBList());
		FORUMID.setOwningType("org.eclipse.scava.metricprovider.trans.emotionclassification.model.ForumPostDetectingEmotionClassification");
		TOPICID.setOwningType("org.eclipse.scava.metricprovider.trans.emotionclassification.model.ForumPostDetectingEmotionClassification");
		POSTID.setOwningType("org.eclipse.scava.metricprovider.trans.emotionclassification.model.ForumPostDetectingEmotionClassification");
		EMOTIONS.setOwningType("org.eclipse.scava.metricprovider.trans.emotionclassification.model.ForumPostDetectingEmotionClassification");
	}
	
	public static StringQueryProducer FORUMID = new StringQueryProducer("forumId"); 
	public static StringQueryProducer TOPICID = new StringQueryProducer("topicId"); 
	public static StringQueryProducer POSTID = new StringQueryProducer("postId"); 
	public static ArrayQueryProducer EMOTIONS = new ArrayQueryProducer("emotions");
	
	
	public String getForumId() {
		return parseString(dbObject.get("forumId")+"", "");
	}
	
	public ForumPostDetectingEmotionClassification setForumId(String forumId) {
		dbObject.put("forumId", forumId);
		notifyChanged();
		return this;
	}
	public String getTopicId() {
		return parseString(dbObject.get("topicId")+"", "");
	}
	
	public ForumPostDetectingEmotionClassification setTopicId(String topicId) {
		dbObject.put("topicId", topicId);
		notifyChanged();
		return this;
	}
	public String getPostId() {
		return parseString(dbObject.get("postId")+"", "");
	}
	
	public ForumPostDetectingEmotionClassification setPostId(String postId) {
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
	
	public ForumPostDetectingEmotionClassification setEmotions(List<String> emotions) {
		this.emotions=emotions;
		dbObject.put("emotions", emotions);
		notifyChanged();
		return this;
	}
	
}