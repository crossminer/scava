package org.eclipse.scava.metricprovider.trans.severityclassification.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ForumPostData extends Pongo {
	
	protected List<Integer> unigrams = null;
	protected List<Integer> bigrams = null;
	protected List<Integer> trigrams = null;
	protected List<Integer> quadgrams = null;
	protected List<Integer> charTrigrams = null;
	protected List<Integer> charQuadgrams = null;
	protected List<Integer> charFivegrams = null;
	
	
	public ForumPostData() { 
		super();
		dbObject.put("unigrams", new BasicDBList());
		dbObject.put("bigrams", new BasicDBList());
		dbObject.put("trigrams", new BasicDBList());
		dbObject.put("quadgrams", new BasicDBList());
		dbObject.put("charTrigrams", new BasicDBList());
		dbObject.put("charQuadgrams", new BasicDBList());
		dbObject.put("charFivegrams", new BasicDBList());
		FORUMID.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.ForumPostData");
		TOPICID.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.ForumPostData");
		SEVERITY.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.ForumPostData");
		UNIGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.ForumPostData");
		BIGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.ForumPostData");
		TRIGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.ForumPostData");
		QUADGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.ForumPostData");
		CHARTRIGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.ForumPostData");
		CHARQUADGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.ForumPostData");
		CHARFIVEGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.ForumPostData");
	}
	
	public static StringQueryProducer FORUMID = new StringQueryProducer("forumId"); 
	public static StringQueryProducer TOPICID = new StringQueryProducer("topicId"); 
	public static StringQueryProducer SEVERITY = new StringQueryProducer("severity"); 
	public static ArrayQueryProducer UNIGRAMS = new ArrayQueryProducer("unigrams");
	public static ArrayQueryProducer BIGRAMS = new ArrayQueryProducer("bigrams");
	public static ArrayQueryProducer TRIGRAMS = new ArrayQueryProducer("trigrams");
	public static ArrayQueryProducer QUADGRAMS = new ArrayQueryProducer("quadgrams");
	public static ArrayQueryProducer CHARTRIGRAMS = new ArrayQueryProducer("charTrigrams");
	public static ArrayQueryProducer CHARQUADGRAMS = new ArrayQueryProducer("charQuadgrams");
	public static ArrayQueryProducer CHARFIVEGRAMS = new ArrayQueryProducer("charFivegrams");
	
	
	public String getForumId() {
		return parseString(dbObject.get("forumId")+"", "");
	}
	
	public ForumPostData setForumId(String forumId) {
		dbObject.put("forumId", forumId);
		notifyChanged();
		return this;
	}
	public String getTopicId() {
		return parseString(dbObject.get("topicId")+"", "");
	}
	
	public ForumPostData setTopicId(String topicId) {
		dbObject.put("topicId", topicId);
		notifyChanged();
		return this;
	}
	public String getSeverity() {
		return parseString(dbObject.get("severity")+"", "");
	}
	
	public ForumPostData setSeverity(String severity) {
		dbObject.put("severity", severity);
		notifyChanged();
		return this;
	}
	
	public List<Integer> getUnigrams() {
		if (unigrams == null) {
			unigrams = new PrimitiveList<Integer>(this, (BasicDBList) dbObject.get("unigrams"));
		}
		return unigrams;
	}
	public List<Integer> getBigrams() {
		if (bigrams == null) {
			bigrams = new PrimitiveList<Integer>(this, (BasicDBList) dbObject.get("bigrams"));
		}
		return bigrams;
	}
	public List<Integer> getTrigrams() {
		if (trigrams == null) {
			trigrams = new PrimitiveList<Integer>(this, (BasicDBList) dbObject.get("trigrams"));
		}
		return trigrams;
	}
	public List<Integer> getQuadgrams() {
		if (quadgrams == null) {
			quadgrams = new PrimitiveList<Integer>(this, (BasicDBList) dbObject.get("quadgrams"));
		}
		return quadgrams;
	}
	public List<Integer> getCharTrigrams() {
		if (charTrigrams == null) {
			charTrigrams = new PrimitiveList<Integer>(this, (BasicDBList) dbObject.get("charTrigrams"));
		}
		return charTrigrams;
	}
	public List<Integer> getCharQuadgrams() {
		if (charQuadgrams == null) {
			charQuadgrams = new PrimitiveList<Integer>(this, (BasicDBList) dbObject.get("charQuadgrams"));
		}
		return charQuadgrams;
	}
	public List<Integer> getCharFivegrams() {
		if (charFivegrams == null) {
			charFivegrams = new PrimitiveList<Integer>(this, (BasicDBList) dbObject.get("charFivegrams"));
		}
		return charFivegrams;
	}
	
	
	
}