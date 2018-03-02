/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.severityclassification.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugTrackerBugsData extends Pongo {
	
	protected List<Integer> unigrams = null;
	protected List<Integer> bigrams = null;
	protected List<Integer> trigrams = null;
	protected List<Integer> quadgrams = null;
	protected List<Integer> charTrigrams = null;
	protected List<Integer> charQuadgrams = null;
	protected List<Integer> charFivegrams = null;
	
	
	public BugTrackerBugsData() { 
		super();
		dbObject.put("unigrams", new BasicDBList());
		dbObject.put("bigrams", new BasicDBList());
		dbObject.put("trigrams", new BasicDBList());
		dbObject.put("quadgrams", new BasicDBList());
		dbObject.put("charTrigrams", new BasicDBList());
		dbObject.put("charQuadgrams", new BasicDBList());
		dbObject.put("charFivegrams", new BasicDBList());
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.BugTrackerBugsData");
		BUGID.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.BugTrackerBugsData");
		SEVERITY.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.BugTrackerBugsData");
		UNIGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.BugTrackerBugsData");
		BIGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.BugTrackerBugsData");
		TRIGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.BugTrackerBugsData");
		QUADGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.BugTrackerBugsData");
		CHARTRIGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.BugTrackerBugsData");
		CHARQUADGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.BugTrackerBugsData");
		CHARFIVEGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.BugTrackerBugsData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer SEVERITY = new StringQueryProducer("severity"); 
	public static ArrayQueryProducer UNIGRAMS = new ArrayQueryProducer("unigrams");
	public static ArrayQueryProducer BIGRAMS = new ArrayQueryProducer("bigrams");
	public static ArrayQueryProducer TRIGRAMS = new ArrayQueryProducer("trigrams");
	public static ArrayQueryProducer QUADGRAMS = new ArrayQueryProducer("quadgrams");
	public static ArrayQueryProducer CHARTRIGRAMS = new ArrayQueryProducer("charTrigrams");
	public static ArrayQueryProducer CHARQUADGRAMS = new ArrayQueryProducer("charQuadgrams");
	public static ArrayQueryProducer CHARFIVEGRAMS = new ArrayQueryProducer("charFivegrams");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerBugsData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugTrackerBugsData setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getSeverity() {
		return parseString(dbObject.get("severity")+"", "");
	}
	
	public BugTrackerBugsData setSeverity(String severity) {
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
