package org.eclipse.scava.metricprovider.trans.newsgroups.threads.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class CurrentDate extends Pongo {
	
	
	
	public CurrentDate() { 
		super();
		DATE.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.CurrentDate");
	}
	
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public CurrentDate setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}