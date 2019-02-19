package org.eclipse.scava.metricprovider.trans.severityclassification.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupThreadData extends Pongo {
	
	
	
	public NewsgroupThreadData() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.NewsgroupThreadData");
		THREADID.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.NewsgroupThreadData");
		SEVERITY.setOwningType("org.eclipse.scava.metricprovider.trans.severityclassification.model.NewsgroupThreadData");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer THREADID = new NumericalQueryProducer("threadId");
	public static StringQueryProducer SEVERITY = new StringQueryProducer("severity"); 
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgroupThreadData setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public int getThreadId() {
		return parseInteger(dbObject.get("threadId")+"", 0);
	}
	
	public NewsgroupThreadData setThreadId(int threadId) {
		dbObject.put("threadId", threadId);
		notifyChanged();
		return this;
	}
	public String getSeverity() {
		return parseString(dbObject.get("severity")+"", "");
	}
	
	public NewsgroupThreadData setSeverity(String severity) {
		dbObject.put("severity", severity);
		notifyChanged();
		return this;
	}
	
	
	
	
}