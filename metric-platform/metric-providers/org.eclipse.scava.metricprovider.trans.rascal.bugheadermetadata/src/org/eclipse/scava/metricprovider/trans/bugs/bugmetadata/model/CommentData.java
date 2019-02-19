/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class CommentData extends Pongo {
	
	
	
	public CommentData() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.CommentData");
		BUGID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.CommentData");
		COMMENTID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.CommentData");
		CREATIONTIME.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.CommentData");
		CREATOR.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.CommentData");
		CONTENTCLASS.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.CommentData");
		REQUESTREPLYPREDICTION.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.CommentData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer COMMENTID = new StringQueryProducer("commentId"); 
	public static StringQueryProducer CREATIONTIME = new StringQueryProducer("creationTime"); 
	public static StringQueryProducer CREATOR = new StringQueryProducer("creator"); 
	public static StringQueryProducer CONTENTCLASS = new StringQueryProducer("contentClass"); 
	public static StringQueryProducer REQUESTREPLYPREDICTION = new StringQueryProducer("requestReplyPrediction"); 
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public CommentData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public CommentData setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getCommentId() {
		return parseString(dbObject.get("commentId")+"", "");
	}
	
	public CommentData setCommentId(String commentId) {
		dbObject.put("commentId", commentId);
		notifyChanged();
		return this;
	}
	public String getCreationTime() {
		return parseString(dbObject.get("creationTime")+"", "");
	}
	
	public CommentData setCreationTime(String creationTime) {
		dbObject.put("creationTime", creationTime);
		notifyChanged();
		return this;
	}
	public String getCreator() {
		return parseString(dbObject.get("creator")+"", "");
	}
	
	public CommentData setCreator(String creator) {
		dbObject.put("creator", creator);
		notifyChanged();
		return this;
	}
	public String getContentClass() {
		return parseString(dbObject.get("contentClass")+"", "");
	}
	
	public CommentData setContentClass(String contentClass) {
		dbObject.put("contentClass", contentClass);
		notifyChanged();
		return this;
	}
	public String getRequestReplyPrediction() {
		return parseString(dbObject.get("requestReplyPrediction")+"", "");
	}
	
	public CommentData setRequestReplyPrediction(String requestReplyPrediction) {
		dbObject.put("requestReplyPrediction", requestReplyPrediction);
		notifyChanged();
		return this;
	}
	
	
	
	
}
