package org.eclipse.scava.metricprovider.trans.plaintextprocessing.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupArticlePlainTextProcessing extends Pongo {
	
	protected List<String> plainText = null;
	
	
	public NewsgroupArticlePlainTextProcessing() { 
		super();
		dbObject.put("plainText", new BasicDBList());
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.NewsgroupArticlePlainTextProcessing");
		ARTICLENUMBER.setOwningType("org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.NewsgroupArticlePlainTextProcessing");
		PLAINTEXT.setOwningType("org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.NewsgroupArticlePlainTextProcessing");
		HADREPLIES.setOwningType("org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.NewsgroupArticlePlainTextProcessing");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsGroupName"); 
	public static NumericalQueryProducer ARTICLENUMBER = new NumericalQueryProducer("articleNumber");
	public static StringQueryProducer HADREPLIES = new StringQueryProducer("hadReplies"); 
	public static ArrayQueryProducer PLAINTEXT = new ArrayQueryProducer("plainText");
	
	
	public String getNewsGroupName() {
		return parseString(dbObject.get("newsGroupName")+"", "");
	}
	
	public NewsgroupArticlePlainTextProcessing setNewsGroupName(String newsGroupName) {
		dbObject.put("newsGroupName", newsGroupName);
		notifyChanged();
		return this;
	}
	public int getArticleNumber() {
		return parseInteger(dbObject.get("articleNumber")+"", 0);
	}
	
	public NewsgroupArticlePlainTextProcessing setArticleNumber(int articleNumber) {
		dbObject.put("articleNumber", articleNumber);
		notifyChanged();
		return this;
	}
	public boolean getHadReplies() {
		return parseBoolean(dbObject.get("hadReplies")+"", false);
	}
	
	public NewsgroupArticlePlainTextProcessing setHadReplies(boolean hadReplies) {
		dbObject.put("hadReplies", hadReplies);
		notifyChanged();
		return this;
	}
	
	public List<String> getPlainText() {
		if (plainText == null) {
			plainText = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("plainText"));
		}
		return plainText;
	}
	
	public NewsgroupArticlePlainTextProcessing setPlainText(List<String> plainText) {
		dbObject.put("plainText", plainText);
		this.plainText = plainText;
		notifyChanged();
		return this;	
	}
}