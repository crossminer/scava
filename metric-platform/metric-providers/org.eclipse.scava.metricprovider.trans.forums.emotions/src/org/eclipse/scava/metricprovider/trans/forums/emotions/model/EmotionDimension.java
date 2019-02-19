package org.eclipse.scava.metricprovider.trans.forums.emotions.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class EmotionDimension extends Pongo {
	
	
	
	public EmotionDimension() { 
		super();
		FORUMID.setOwningType("org.eclipse.scava.metricprovider.trans.forums.emotions.model.EmotionDimension");
		TOPICID.setOwningType("org.eclipse.scava.metricprovider.trans.forums.emotions.model.EmotionDimension");
		EMOTIONLABEL.setOwningType("org.eclipse.scava.metricprovider.trans.forums.emotions.model.EmotionDimension");
		NUMBEROFPOSTS.setOwningType("org.eclipse.scava.metricprovider.trans.forums.emotions.model.EmotionDimension");
		CUMULATIVENUMBEROFPOSTS.setOwningType("org.eclipse.scava.metricprovider.trans.forums.emotions.model.EmotionDimension");
		PERCENTAGE.setOwningType("org.eclipse.scava.metricprovider.trans.forums.emotions.model.EmotionDimension");
		CUMULATIVEPERCENTAGE.setOwningType("org.eclipse.scava.metricprovider.trans.forums.emotions.model.EmotionDimension");
	}
	
	public static StringQueryProducer FORUMID = new StringQueryProducer("forumId"); 
	public static StringQueryProducer TOPICID = new StringQueryProducer("topicId"); 
	public static StringQueryProducer EMOTIONLABEL = new StringQueryProducer("emotionLabel"); 
	public static NumericalQueryProducer NUMBEROFPOSTS = new NumericalQueryProducer("numberOfPosts");
	public static NumericalQueryProducer CUMULATIVENUMBEROFPOSTS = new NumericalQueryProducer("cumulativeNumberOfPosts");
	public static NumericalQueryProducer PERCENTAGE = new NumericalQueryProducer("percentage");
	public static NumericalQueryProducer CUMULATIVEPERCENTAGE = new NumericalQueryProducer("cumulativePercentage");
	
	
	public String getForumId() {
		return parseString(dbObject.get("forumId")+"", "");
	}
	
	public EmotionDimension setForumId(String forumId) {
		dbObject.put("forumId", forumId);
		notifyChanged();
		return this;
	}
	public String getTopicId() {
		return parseString(dbObject.get("topicId")+"", "");
	}
	
	public EmotionDimension setTopicId(String topicId) {
		dbObject.put("topicId", topicId);
		notifyChanged();
		return this;
	}
	public String getEmotionLabel() {
		return parseString(dbObject.get("emotionLabel")+"", "");
	}
	
	public EmotionDimension setEmotionLabel(String emotionLabel) {
		dbObject.put("emotionLabel", emotionLabel);
		notifyChanged();
		return this;
	}
	public int getNumberOfPosts() {
		return parseInteger(dbObject.get("numberOfPosts")+"", 0);
	}
	
	public EmotionDimension setNumberOfPosts(int numberOfPosts) {
		dbObject.put("numberOfPosts", numberOfPosts);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfPosts() {
		return parseInteger(dbObject.get("cumulativeNumberOfPosts")+"", 0);
	}
	
	public EmotionDimension setCumulativeNumberOfPosts(int cumulativeNumberOfPosts) {
		dbObject.put("cumulativeNumberOfPosts", cumulativeNumberOfPosts);
		notifyChanged();
		return this;
	}
	public float getPercentage() {
		return parseFloat(dbObject.get("percentage")+"", 0.0f);
	}
	
	public EmotionDimension setPercentage(float percentage) {
		dbObject.put("percentage", percentage);
		notifyChanged();
		return this;
	}
	public float getCumulativePercentage() {
		return parseFloat(dbObject.get("cumulativePercentage")+"", 0.0f);
	}
	
	public EmotionDimension setCumulativePercentage(float cumulativePercentage) {
		dbObject.put("cumulativePercentage", cumulativePercentage);
		notifyChanged();
		return this;
	}
	
	
	
	
}