package org.eclipse.scava.metricprovider.trans.newsgroups.threadsrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class ThreadStatistics extends Pongo {
	
	
	
	public ThreadStatistics() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics");
		THREADID.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics");
		FIRSTREQUEST.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics");
		ANSWERED.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics");
		RESPONSEDURATIONSEC.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics");
		RESPONSEDATE.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static StringQueryProducer THREADID = new StringQueryProducer("threadId"); 
	public static StringQueryProducer FIRSTREQUEST = new StringQueryProducer("firstRequest"); 
	public static StringQueryProducer ANSWERED = new StringQueryProducer("answered"); 
	public static NumericalQueryProducer RESPONSEDURATIONSEC = new NumericalQueryProducer("responseDurationSec");
	public static StringQueryProducer RESPONSEDATE = new StringQueryProducer("responseDate"); 
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public ThreadStatistics setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public String getThreadId() {
		return parseString(dbObject.get("threadId")+"", "");
	}
	
	public ThreadStatistics setThreadId(String threadId) {
		dbObject.put("threadId", threadId);
		notifyChanged();
		return this;
	}
	public boolean getFirstRequest() {
		return parseBoolean(dbObject.get("firstRequest")+"", false);
	}
	
	public ThreadStatistics setFirstRequest(boolean firstRequest) {
		dbObject.put("firstRequest", firstRequest);
		notifyChanged();
		return this;
	}
	public boolean getAnswered() {
		return parseBoolean(dbObject.get("answered")+"", false);
	}
	
	public ThreadStatistics setAnswered(boolean answered) {
		dbObject.put("answered", answered);
		notifyChanged();
		return this;
	}
	public long getResponseDurationSec() {
		return parseLong(dbObject.get("responseDurationSec")+"", 0);
	}
	
	public ThreadStatistics setResponseDurationSec(long responseDurationSec) {
		dbObject.put("responseDurationSec", responseDurationSec);
		notifyChanged();
		return this;
	}
	public String getResponseDate() {
		return parseString(dbObject.get("responseDate")+"", "");
	}
	
	public ThreadStatistics setResponseDate(String responseDate) {
		dbObject.put("responseDate", responseDate);
		notifyChanged();
		return this;
	}
	
	
	
	
}