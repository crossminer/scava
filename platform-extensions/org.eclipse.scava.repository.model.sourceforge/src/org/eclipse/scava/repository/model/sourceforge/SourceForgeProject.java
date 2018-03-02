/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.sourceforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class SourceForgeProject extends org.eclipse.scava.repository.model.Project {
	
	protected List<OS> os = null;
	protected List<Topic> topics = null;
	protected List<ProgrammingLanguage> programminLanguages = null;
	protected List<Audience> audiences = null;
	protected List<Environment> environments = null;
	protected List<Category> categories = null;
	protected List<FeatureRequest> featureRequests = null;
	protected List<SupportRequest> supportRequests = null;
	protected List<Patch> patches = null;
	protected List<SourceForgeBugTrackingSystem> bts = null;
	protected List<Discussion> discussion = null;
	protected List<Bug> bugs = null;
	protected MailingList mailingList = null;
	protected Donation donation = null;
	
	
	public SourceForgeProject() { 
		super();
		dbObject.put("os", new BasicDBList());
		dbObject.put("topics", new BasicDBList());
		dbObject.put("programminLanguages", new BasicDBList());
		dbObject.put("audiences", new BasicDBList());
		dbObject.put("environments", new BasicDBList());
		dbObject.put("categories", new BasicDBList());
		dbObject.put("featureRequests", new BasicDBList());
		dbObject.put("supportRequests", new BasicDBList());
		dbObject.put("patches", new BasicDBList());
		dbObject.put("bts", new BasicDBList());
		dbObject.put("discussion", new BasicDBList());
		dbObject.put("bugs", new BasicDBList());
		super.setSuperTypes("org.eclipse.scava.repository.model.sourceforge.Project");
		CREATED.setOwningType("org.eclipse.scava.repository.model.sourceforge.SourceForgeProject");
		PROJECTID.setOwningType("org.eclipse.scava.repository.model.sourceforge.SourceForgeProject");
		_PRIVATE.setOwningType("org.eclipse.scava.repository.model.sourceforge.SourceForgeProject");
		SHORTDESC.setOwningType("org.eclipse.scava.repository.model.sourceforge.SourceForgeProject");
		PERCENTILE.setOwningType("org.eclipse.scava.repository.model.sourceforge.SourceForgeProject");
		RANKING.setOwningType("org.eclipse.scava.repository.model.sourceforge.SourceForgeProject");
		DOWNLOADPAGE.setOwningType("org.eclipse.scava.repository.model.sourceforge.SourceForgeProject");
		SUPPORTPAGE.setOwningType("org.eclipse.scava.repository.model.sourceforge.SourceForgeProject");
		SUMMARY.setOwningType("org.eclipse.scava.repository.model.sourceforge.SourceForgeProject");
		DOWNLOADCOUNT.setOwningType("org.eclipse.scava.repository.model.sourceforge.SourceForgeProject");
	}
	
	public static StringQueryProducer CREATED = new StringQueryProducer("created"); 
	public static NumericalQueryProducer PROJECTID = new NumericalQueryProducer("projectId");
	public static NumericalQueryProducer _PRIVATE = new NumericalQueryProducer("_private");
	public static StringQueryProducer SHORTDESC = new StringQueryProducer("shortDesc"); 
	public static NumericalQueryProducer PERCENTILE = new NumericalQueryProducer("percentile");
	public static NumericalQueryProducer RANKING = new NumericalQueryProducer("ranking");
	public static StringQueryProducer DOWNLOADPAGE = new StringQueryProducer("downloadPage"); 
	public static StringQueryProducer SUPPORTPAGE = new StringQueryProducer("supportPage"); 
	public static StringQueryProducer SUMMARY = new StringQueryProducer("summary"); 
	public static StringQueryProducer DOWNLOADCOUNT = new StringQueryProducer("downloadCount"); 
	
	
	public String getCreated() {
		return parseString(dbObject.get("created")+"", "");
	}
	
	public SourceForgeProject setCreated(String created) {
		dbObject.put("created", created);
		notifyChanged();
		return this;
	}
	public int getProjectId() {
		return parseInteger(dbObject.get("projectId")+"", 0);
	}
	
	public SourceForgeProject setProjectId(int projectId) {
		dbObject.put("projectId", projectId);
		notifyChanged();
		return this;
	}
	public int get_private() {
		return parseInteger(dbObject.get("_private")+"", 0);
	}
	
	public SourceForgeProject set_private(int _private) {
		dbObject.put("_private", _private);
		notifyChanged();
		return this;
	}
	public String getShortDesc() {
		return parseString(dbObject.get("shortDesc")+"", "");
	}
	
	public SourceForgeProject setShortDesc(String shortDesc) {
		dbObject.put("shortDesc", shortDesc);
		notifyChanged();
		return this;
	}
	public float getPercentile() {
		return parseFloat(dbObject.get("percentile")+"", 0.0f);
	}
	
	public SourceForgeProject setPercentile(float percentile) {
		dbObject.put("percentile", percentile);
		notifyChanged();
		return this;
	}
	public int getRanking() {
		return parseInteger(dbObject.get("ranking")+"", 0);
	}
	
	public SourceForgeProject setRanking(int ranking) {
		dbObject.put("ranking", ranking);
		notifyChanged();
		return this;
	}
	public String getDownloadPage() {
		return parseString(dbObject.get("downloadPage")+"", "");
	}
	
	public SourceForgeProject setDownloadPage(String downloadPage) {
		dbObject.put("downloadPage", downloadPage);
		notifyChanged();
		return this;
	}
	public String getSupportPage() {
		return parseString(dbObject.get("supportPage")+"", "");
	}
	
	public SourceForgeProject setSupportPage(String supportPage) {
		dbObject.put("supportPage", supportPage);
		notifyChanged();
		return this;
	}
	public String getSummary() {
		return parseString(dbObject.get("summary")+"", "");
	}
	
	public SourceForgeProject setSummary(String summary) {
		dbObject.put("summary", summary);
		notifyChanged();
		return this;
	}
	public String getDownloadCount() {
		return parseString(dbObject.get("downloadCount")+"", "");
	}
	
	public SourceForgeProject setDownloadCount(String downloadCount) {
		dbObject.put("downloadCount", downloadCount);
		notifyChanged();
		return this;
	}
	
	
	public List<OS> getOs() {
		if (os == null) {
			os = new PongoList<OS>(this, "os", true);
		}
		return os;
	}
	public List<Topic> getTopics() {
		if (topics == null) {
			topics = new PongoList<Topic>(this, "topics", true);
		}
		return topics;
	}
	public List<ProgrammingLanguage> getProgramminLanguages() {
		if (programminLanguages == null) {
			programminLanguages = new PongoList<ProgrammingLanguage>(this, "programminLanguages", true);
		}
		return programminLanguages;
	}
	public List<Audience> getAudiences() {
		if (audiences == null) {
			audiences = new PongoList<Audience>(this, "audiences", true);
		}
		return audiences;
	}
	public List<Environment> getEnvironments() {
		if (environments == null) {
			environments = new PongoList<Environment>(this, "environments", true);
		}
		return environments;
	}
	public List<Category> getCategories() {
		if (categories == null) {
			categories = new PongoList<Category>(this, "categories", true);
		}
		return categories;
	}
	public List<FeatureRequest> getFeatureRequests() {
		if (featureRequests == null) {
			featureRequests = new PongoList<FeatureRequest>(this, "featureRequests", true);
		}
		return featureRequests;
	}
	public List<SupportRequest> getSupportRequests() {
		if (supportRequests == null) {
			supportRequests = new PongoList<SupportRequest>(this, "supportRequests", true);
		}
		return supportRequests;
	}
	public List<Patch> getPatches() {
		if (patches == null) {
			patches = new PongoList<Patch>(this, "patches", true);
		}
		return patches;
	}
	public List<SourceForgeBugTrackingSystem> getBts() {
		if (bts == null) {
			bts = new PongoList<SourceForgeBugTrackingSystem>(this, "bts", true);
		}
		return bts;
	}
	public List<Discussion> getDiscussion() {
		if (discussion == null) {
			discussion = new PongoList<Discussion>(this, "discussion", true);
		}
		return discussion;
	}
	public List<Bug> getBugs() {
		if (bugs == null) {
			bugs = new PongoList<Bug>(this, "bugs", true);
		}
		return bugs;
	}
	
	
	public MailingList getMailingList() {
		if (mailingList == null && dbObject.containsField("mailingList")) {
			mailingList = (MailingList) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("mailingList"));
		}
		return mailingList;
	}
	
	public SourceForgeProject setMailingList(MailingList mailingList) {
		if (this.mailingList != mailingList) {
			if (mailingList == null) {
				dbObject.removeField("mailingList");
			}
			else {
				dbObject.put("mailingList", mailingList.getDbObject());
			}
			this.mailingList = mailingList;
			notifyChanged();
		}
		return this;
	}
	public Donation getDonation() {
		if (donation == null && dbObject.containsField("donation")) {
			donation = (Donation) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("donation"));
		}
		return donation;
	}
	
	public SourceForgeProject setDonation(Donation donation) {
		if (this.donation != donation) {
			if (donation == null) {
				dbObject.removeField("donation");
			}
			else {
				dbObject.put("donation", donation.getDbObject());
			}
			this.donation = donation;
			notifyChanged();
		}
		return this;
	}
}
