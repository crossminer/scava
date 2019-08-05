package org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugData extends Pongo {
	
	protected List<String> resolution = null;
	
	
	public BugData() { 
		super();
		dbObject.put("resolution", new BasicDBList());
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		BUGID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		STATUS.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		RESOLUTION.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		OPERATINGSYSTEM.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		PRIORITY.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		CREATIONTIME.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		LASTCLOSEDTIME.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		COMMENTSUM.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		SENTIMENTSUM.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		FIRSTCOMMENTID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		LASTCOMMENTID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		AVERAGESENTIMENT.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		STARTSENTIMENT.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		ENDSENTIMENT.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer OPERATINGSYSTEM = new StringQueryProducer("operatingSystem"); 
	public static StringQueryProducer PRIORITY = new StringQueryProducer("priority"); 
	public static StringQueryProducer CREATIONTIME = new StringQueryProducer("creationTime"); 
	public static StringQueryProducer LASTCLOSEDTIME = new StringQueryProducer("lastClosedTime"); 
	public static NumericalQueryProducer COMMENTSUM = new NumericalQueryProducer("commentSum");
	public static NumericalQueryProducer SENTIMENTSUM = new NumericalQueryProducer("sentimentSum");
	public static StringQueryProducer FIRSTCOMMENTID = new StringQueryProducer("firstCommentId"); 
	public static StringQueryProducer LASTCOMMENTID = new StringQueryProducer("lastCommentId"); 
	public static NumericalQueryProducer AVERAGESENTIMENT = new NumericalQueryProducer("averageSentiment");
	public static StringQueryProducer STARTSENTIMENT = new StringQueryProducer("startSentiment"); 
	public static StringQueryProducer ENDSENTIMENT = new StringQueryProducer("endSentiment"); 
	public static ArrayQueryProducer RESOLUTION = new ArrayQueryProducer("resolution");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugData setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getStatus() {
		return parseString(dbObject.get("status")+"", "");
	}
	
	public BugData setStatus(String status) {
		dbObject.put("status", status);
		notifyChanged();
		return this;
	}
	public String getOperatingSystem() {
		return parseString(dbObject.get("operatingSystem")+"", "");
	}
	
	public BugData setOperatingSystem(String operatingSystem) {
		dbObject.put("operatingSystem", operatingSystem);
		notifyChanged();
		return this;
	}
	public String getPriority() {
		return parseString(dbObject.get("priority")+"", "");
	}
	
	public BugData setPriority(String priority) {
		dbObject.put("priority", priority);
		notifyChanged();
		return this;
	}
	public Date getCreationTime() {
		return parseDate(dbObject.get("creationTime")+"", null);
	}
	
	public BugData setCreationTime(Date creationTime) {
		dbObject.put("creationTime", creationTime);
		notifyChanged();
		return this;
	}
	public Date getLastClosedTime() {
		return parseDate(dbObject.get("lastClosedTime")+"", null);
	}
	
	public BugData setLastClosedTime(Date lastClosedTime) {
		dbObject.put("lastClosedTime", lastClosedTime);
		notifyChanged();
		return this;
	}
	public int getCommentSum() {
		return parseInteger(dbObject.get("commentSum")+"", 0);
	}
	
	public BugData setCommentSum(int commentSum) {
		dbObject.put("commentSum", commentSum);
		notifyChanged();
		return this;
	}
	public int getSentimentSum() {
		return parseInteger(dbObject.get("sentimentSum")+"", 0);
	}
	
	public BugData setSentimentSum(int sentimentSum) {
		dbObject.put("sentimentSum", sentimentSum);
		notifyChanged();
		return this;
	}
	public String getFirstCommentId() {
		return parseString(dbObject.get("firstCommentId")+"", "");
	}
	
	public BugData setFirstCommentId(String firstCommentId) {
		dbObject.put("firstCommentId", firstCommentId);
		notifyChanged();
		return this;
	}
	public String getLastCommentId() {
		return parseString(dbObject.get("lastCommentId")+"", "");
	}
	
	public BugData setLastCommentId(String lastCommentId) {
		dbObject.put("lastCommentId", lastCommentId);
		notifyChanged();
		return this;
	}
	public float getAverageSentiment() {
		return parseFloat(dbObject.get("averageSentiment")+"", 0.0f);
	}
	
	public BugData setAverageSentiment(float averageSentiment) {
		dbObject.put("averageSentiment", averageSentiment);
		notifyChanged();
		return this;
	}
	public String getStartSentiment() {
		return parseString(dbObject.get("startSentiment")+"", "");
	}
	
	public BugData setStartSentiment(String startSentiment) {
		dbObject.put("startSentiment", startSentiment);
		notifyChanged();
		return this;
	}
	public String getEndSentiment() {
		return parseString(dbObject.get("endSentiment")+"", "");
	}
	
	public BugData setEndSentiment(String endSentiment) {
		dbObject.put("endSentiment", endSentiment);
		notifyChanged();
		return this;
	}
	
	public List<String> getResolution() {
		if (resolution == null) {
			resolution = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("resolution"));
		}
		return resolution;
	}
	
	
	
}