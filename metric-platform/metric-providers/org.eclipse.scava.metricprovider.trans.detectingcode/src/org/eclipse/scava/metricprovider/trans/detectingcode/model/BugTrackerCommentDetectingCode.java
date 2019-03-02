package org.eclipse.scava.metricprovider.trans.detectingcode.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugTrackerCommentDetectingCode extends Pongo {
	
	
	
	public BugTrackerCommentDetectingCode() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.detectingcode.model.BugTrackerCommentDetectingCode");
		BUGID.setOwningType("org.eclipse.scava.metricprovider.trans.detectingcode.model.BugTrackerCommentDetectingCode");
		COMMENTID.setOwningType("org.eclipse.scava.metricprovider.trans.detectingcode.model.BugTrackerCommentDetectingCode");
		NATURALLANGUAGE.setOwningType("org.eclipse.scava.metricprovider.trans.detectingcode.model.BugTrackerCommentDetectingCode");
		CODE.setOwningType("org.eclipse.scava.metricprovider.trans.detectingcode.model.BugTrackerCommentDetectingCode");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer COMMENTID = new StringQueryProducer("commentId"); 
	public static StringQueryProducer NATURALLANGUAGE = new StringQueryProducer("naturalLanguage"); 
	public static StringQueryProducer CODE = new StringQueryProducer("code"); 
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerCommentDetectingCode setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugTrackerCommentDetectingCode setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getCommentId() {
		return parseString(dbObject.get("commentId")+"", "");
	}
	
	public BugTrackerCommentDetectingCode setCommentId(String commentId) {
		dbObject.put("commentId", commentId);
		notifyChanged();
		return this;
	}
	public String getNaturalLanguage() {
		return parseString(dbObject.get("naturalLanguage")+"", "");
	}
	
	public BugTrackerCommentDetectingCode setNaturalLanguage(String naturalLanguage) {
		dbObject.put("naturalLanguage", naturalLanguage);
		notifyChanged();
		return this;
	}
	public String getCode() {
		return parseString(dbObject.get("code")+"", "");
	}
	
	public BugTrackerCommentDetectingCode setCode(String code) {
		dbObject.put("code", code);
		notifyChanged();
		return this;
	}
	
	
	
	
}