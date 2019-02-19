package org.eclipse.scava.metricprovider.trans.severityclassification.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupArticleData extends Pongo {
	
	protected List<Integer> unigrams = null;
	protected List<Integer> bigrams = null;
	protected List<Integer> trigrams = null;
	protected List<Integer> quadgrams = null;
	protected List<Integer> charTrigrams = null;
	protected List<Integer> charQuadgrams = null;
	protected List<Integer> charFivegrams = null;
	
	
	public NewsgroupArticleData() { 
		super();
		dbObject.put("unigrams", new BasicDBList());
		dbObject.put("bigrams", new BasicDBList());
		dbObject.put("trigrams", new BasicDBList());
		dbObject.put("quadgrams", new BasicDBList());
		dbObject.put("charTrigrams", new BasicDBList());
		dbObject.put("charQuadgrams", new BasicDBList());
		dbObject.put("charFivegrams", new BasicDBList());
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.NewsgroupArticleData");
		ARTICLENUMBER.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.NewsgroupArticleData");
		UNIGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.NewsgroupArticleData");
		BIGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.NewsgroupArticleData");
		TRIGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.NewsgroupArticleData");
		QUADGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.NewsgroupArticleData");
		CHARTRIGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.NewsgroupArticleData");
		CHARQUADGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.NewsgroupArticleData");
		CHARFIVEGRAMS.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.NewsgroupArticleData");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("NewsGroupName"); 
	public static NumericalQueryProducer ARTICLENUMBER = new NumericalQueryProducer("articleNumber");
	public static ArrayQueryProducer UNIGRAMS = new ArrayQueryProducer("unigrams");
	public static ArrayQueryProducer BIGRAMS = new ArrayQueryProducer("bigrams");
	public static ArrayQueryProducer TRIGRAMS = new ArrayQueryProducer("trigrams");
	public static ArrayQueryProducer QUADGRAMS = new ArrayQueryProducer("quadgrams");
	public static ArrayQueryProducer CHARTRIGRAMS = new ArrayQueryProducer("charTrigrams");
	public static ArrayQueryProducer CHARQUADGRAMS = new ArrayQueryProducer("charQuadgrams");
	public static ArrayQueryProducer CHARFIVEGRAMS = new ArrayQueryProducer("charFivegrams");
	
	
	public String getNewsGroupName() {
		return parseString(dbObject.get("NewsGroupName")+"", "");
	}
	
	public NewsgroupArticleData setNewsGroupName(String NewsGroupName) {
		dbObject.put("NewsGroupName", NewsGroupName);
		notifyChanged();
		return this;
	}
	public int getArticleNumber() {
		return parseInteger(dbObject.get("articleNumber")+"", 0);
	}
	
	public NewsgroupArticleData setArticleNumber(int articleNumber) {
		dbObject.put("articleNumber", articleNumber);
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