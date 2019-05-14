/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.plaintextprocessing.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ForumPostPlainTextProcessing extends Pongo {
	
	protected List<String> plainText = null;
	
	
	public ForumPostPlainTextProcessing() { 
		super();
		dbObject.put("plainText", new BasicDBList());
		FORUMID.setOwningType("org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.ForumPostPlainTextProcessing");
		TOPICID.setOwningType("org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.ForumPostPlainTextProcessing");
		POSTID.setOwningType("org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.ForumPostPlainTextProcessing");
		PLAINTEXT.setOwningType("org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.ForumPostPlainTextProcessing");
		HADREPLIES.setOwningType("org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.ForumPostPlainTextProcessing");
	}
	
	public static StringQueryProducer FORUMID = new StringQueryProducer("forumId"); 
	public static StringQueryProducer TOPICID = new StringQueryProducer("topicId"); 
	public static StringQueryProducer POSTID = new StringQueryProducer("postId"); 
	public static StringQueryProducer HADREPLIES = new StringQueryProducer("hadReplies"); 
	public static ArrayQueryProducer PLAINTEXT = new ArrayQueryProducer("plainText");
	
	
	public String getForumId() {
		return parseString(dbObject.get("forumId")+"", "");
	}
	
	public ForumPostPlainTextProcessing setForumId(String forumId) {
		dbObject.put("forumId", forumId);
		notifyChanged();
		return this;
	}
	public String getTopicId() {
		return parseString(dbObject.get("topicId")+"", "");
	}
	
	public ForumPostPlainTextProcessing setTopicId(String topicId) {
		dbObject.put("topicId", topicId);
		notifyChanged();
		return this;
	}
	public String getPostId() {
		return parseString(dbObject.get("postId")+"", "");
	}
	
	public ForumPostPlainTextProcessing setPostId(String postId) {
		dbObject.put("postId", postId);
		notifyChanged();
		return this;
	}
	public boolean getHadReplies() {
		return parseBoolean(dbObject.get("hadReplies")+"", false);
	}
	
	public ForumPostPlainTextProcessing setHadReplies(boolean hadReplies) {
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
	
	
	
}