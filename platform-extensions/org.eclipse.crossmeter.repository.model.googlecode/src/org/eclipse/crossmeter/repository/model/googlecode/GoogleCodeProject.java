/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation,
 *    Juri Di Rocco - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.repository.model.googlecode;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GoogleCodeProject extends org.eclipse.crossmeter.repository.model.Project {
	
	protected List<GoogleDownload> downloads = null;
	protected GoogleWiki wiki = null;
	protected GoogleForum forum = null;
	protected GoogleIssueTracker issueTracker = null;
	
	
	public GoogleCodeProject() { 
		super();
		dbObject.put("downloads", new BasicDBList());
		super.setSuperTypes("org.eclipse.crossmeter.repository.model.googlecode.Project");
		STARS.setOwningType("org.eclipse.crossmeter.repository.model.googlecode.GoogleCodeProject");
	}
	
	public static NumericalQueryProducer STARS = new NumericalQueryProducer("stars");
	
	
	public int getStars() {
		return parseInteger(dbObject.get("stars")+"", 0);
	}
	
	public GoogleCodeProject setStars(int stars) {
		dbObject.put("stars", stars);
		notifyChanged();
		return this;
	}
	
	
	public List<GoogleDownload> getDownloads() {
		if (downloads == null) {
			downloads = new PongoList<GoogleDownload>(this, "downloads", true);
		}
		return downloads;
	}
	
	
	public GoogleWiki getWiki() {
		if (wiki == null && dbObject.containsField("wiki")) {
			wiki = (GoogleWiki) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("wiki"));
		}
		return wiki;
	}
	
	public GoogleCodeProject setWiki(GoogleWiki wiki) {
		if (this.wiki != wiki) {
			if (wiki == null) {
				dbObject.removeField("wiki");
			}
			else {
				dbObject.put("wiki", wiki.getDbObject());
			}
			this.wiki = wiki;
			notifyChanged();
		}
		return this;
	}
	public GoogleForum getForum() {
		if (forum == null && dbObject.containsField("forum")) {
			forum = (GoogleForum) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("forum"));
		}
		return forum;
	}
	
	public GoogleCodeProject setForum(GoogleForum forum) {
		if (this.forum != forum) {
			if (forum == null) {
				dbObject.removeField("forum");
			}
			else {
				dbObject.put("forum", forum.getDbObject());
			}
			this.forum = forum;
			notifyChanged();
		}
		return this;
	}
	public GoogleIssueTracker getIssueTracker() {
		if (issueTracker == null && dbObject.containsField("issueTracker")) {
			issueTracker = (GoogleIssueTracker) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("issueTracker"));
		}
		return issueTracker;
	}
	
	public GoogleCodeProject setIssueTracker(GoogleIssueTracker issueTracker) {
		if (this.issueTracker != issueTracker) {
			if (issueTracker == null) {
				dbObject.removeField("issueTracker");
			}
			else {
				dbObject.put("issueTracker", issueTracker.getDbObject());
			}
			this.issueTracker = issueTracker;
			notifyChanged();
		}
		return this;
	}
}