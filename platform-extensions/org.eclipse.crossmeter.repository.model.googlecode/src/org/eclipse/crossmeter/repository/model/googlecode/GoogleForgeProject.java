/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.repository.model.googlecode;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class GoogleForgeProject extends org.eclipse.crossmeter.repository.model.Project {
	
	protected List<GoogleUser> owners = null;
	protected List<GoogleUser> committers = null;
	protected List<GoogleUser> contributors = null;
	protected List<GoogleDownload> downloads = null;
	protected GoogleWiki wiki = null;
	protected GoogleIssueTracker issueTracker = null;
	
	
	public GoogleForgeProject() { 
		super();
		dbObject.put("owners", new BasicDBList());
		dbObject.put("committers", new BasicDBList());
		dbObject.put("contributors", new BasicDBList());
		dbObject.put("downloads", new BasicDBList());
	}
	
	public int getStars() {
		return parseInteger(dbObject.get("stars")+"", 0);
	}
	
	public GoogleForgeProject setStars(int stars) {
		dbObject.put("stars", stars + "");
		notifyChanged();
		return this;
	}
	
	
	public List<GoogleUser> getOwners() {
		if (owners == null) {
			owners = new PongoList<GoogleUser>(this, "owners", true);
		}
		return owners;
	}
	public List<GoogleUser> getCommitters() {
		if (committers == null) {
			committers = new PongoList<GoogleUser>(this, "committers", true);
		}
		return committers;
	}
	public List<GoogleUser> getContributors() {
		if (contributors == null) {
			contributors = new PongoList<GoogleUser>(this, "contributors", true);
		}
		return contributors;
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
	
	public GoogleForgeProject setWiki(GoogleWiki wiki) {
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
	public GoogleIssueTracker getIssueTracker() {
		if (issueTracker == null && dbObject.containsField("issueTracker")) {
			issueTracker = (GoogleIssueTracker) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("issueTracker"));
		}
		return issueTracker;
	}
	
	public GoogleForgeProject setIssueTracker(GoogleIssueTracker issueTracker) {
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
