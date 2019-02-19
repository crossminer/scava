package org.eclipse.scava.metricprovider.historic.forums.emotions.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Dimension extends Pongo {
	
	
	
	public Dimension() { 
		super();
		FORUMID.setOwningType("org.eclipse.scava.metricprovider.historic.forums.emotions.model.Dimension");
		EMOTIONLABEL.setOwningType("org.eclipse.scava.metricprovider.historic.forums.emotions.model.Dimension");
		NUMBEROFPOSTS.setOwningType("org.eclipse.scava.metricprovider.historic.forums.emotions.model.Dimension");
		CUMULATIVENUMBEROFPOSTS.setOwningType("org.eclipse.scava.metricprovider.historic.forums.emotions.model.Dimension");
		PERCENTAGE.setOwningType("org.eclipse.scava.metricprovider.historic.forums.emotions.model.Dimension");
		CUMULATIVEPERCENTAGE.setOwningType("org.eclipse.scava.metricprovider.historic.forums.emotions.model.Dimension");
	}
	
	public static StringQueryProducer FORUMID = new StringQueryProducer("forumId"); 
	public static StringQueryProducer EMOTIONLABEL = new StringQueryProducer("emotionLabel"); 
	public static NumericalQueryProducer NUMBEROFPOSTS = new NumericalQueryProducer("numberOfPosts");
	public static NumericalQueryProducer CUMULATIVENUMBEROFPOSTS = new NumericalQueryProducer("cumulativeNumberOfPosts");
	public static NumericalQueryProducer PERCENTAGE = new NumericalQueryProducer("percentage");
	public static NumericalQueryProducer CUMULATIVEPERCENTAGE = new NumericalQueryProducer("cumulativePercentage");
	
	
	public String getForumId() {
		return parseString(dbObject.get("forumId")+"", "");
	}
	
	public Dimension setForumId(String forumId) {
		dbObject.put("forumId", forumId);
		notifyChanged();
		return this;
	}
	public String getEmotionLabel() {
		return parseString(dbObject.get("emotionLabel")+"", "");
	}
	
	public Dimension setEmotionLabel(String emotionLabel) {
		dbObject.put("emotionLabel", emotionLabel);
		notifyChanged();
		return this;
	}
	public int getNumberOfPosts() {
		return parseInteger(dbObject.get("numberOfPosts")+"", 0);
	}
	
	public Dimension setNumberOfPosts(int numberOfPosts) {
		dbObject.put("numberOfPosts", numberOfPosts);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfPosts() {
		return parseInteger(dbObject.get("cumulativeNumberOfPosts")+"", 0);
	}
	
	public Dimension setCumulativeNumberOfPosts(int cumulativeNumberOfPosts) {
		dbObject.put("cumulativeNumberOfPosts", cumulativeNumberOfPosts);
		notifyChanged();
		return this;
	}
	public float getPercentage() {
		return parseFloat(dbObject.get("percentage")+"", 0.0f);
	}
	
	public Dimension setPercentage(float percentage) {
		dbObject.put("percentage", percentage);
		notifyChanged();
		return this;
	}
	public float getCumulativePercentage() {
		return parseFloat(dbObject.get("cumulativePercentage")+"", 0.0f);
	}
	
	public Dimension setCumulativePercentage(float cumulativePercentage) {
		dbObject.put("cumulativePercentage", cumulativePercentage);
		notifyChanged();
		return this;
	}
	
	
	
	
}