package org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PrimitiveList;
import com.googlecode.pongo.runtime.querying.ArrayQueryProducer;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.BasicDBList;


public class NewsgroupsMigrationIssue extends Pongo {
	
	protected List<String> changes = null;
	
	
	public NewsgroupsMigrationIssue() { 
		super();
		dbObject.put("changes", new BasicDBList());
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssue");
		THREADID.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssue");
		SOFTWARE.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssue");
		CHANGES.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssue");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer THREADID = new NumericalQueryProducer("threadId");
	public static StringQueryProducer SOFTWARE = new StringQueryProducer("software"); 
	public static ArrayQueryProducer CHANGES = new ArrayQueryProducer("changes");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgroupsMigrationIssue setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public int getThreadId() {
		return parseInteger(dbObject.get("threadId")+"", 0);
	}
	
	public NewsgroupsMigrationIssue setThreadId(int threadId) {
		dbObject.put("threadId", threadId);
		notifyChanged();
		return this;
	}
	public String getSoftware() {
		return parseString(dbObject.get("software")+"", "");
	}
	
	public NewsgroupsMigrationIssue setSoftware(String software) {
		dbObject.put("software", software);
		notifyChanged();
		return this;
	}
	
	public List<String> getChanges() {
		if (changes == null) {
			changes = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("changes"));
		}
		return changes;
	}
	
	
	
}