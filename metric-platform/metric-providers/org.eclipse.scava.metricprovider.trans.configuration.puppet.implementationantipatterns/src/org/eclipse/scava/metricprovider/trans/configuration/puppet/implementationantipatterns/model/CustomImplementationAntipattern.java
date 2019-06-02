package org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationantipatterns.model;

import java.util.Date;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class CustomImplementationAntipattern extends Pongo {
	
	public CustomImplementationAntipattern() { 
		super();
		SMELLNAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.CustomSmell");
		REASON.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.CustomSmell");
		FILENAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.CustomSmell");
		COMMIT.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.CustomSmell");
		DATE.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.CustomSmell");
	}
	
	public static StringQueryProducer SMELLNAME = new StringQueryProducer("smellName"); 
	public static StringQueryProducer REASON = new StringQueryProducer("reason");
	public static StringQueryProducer FILENAME = new StringQueryProducer("fileName");
	public static StringQueryProducer COMMIT = new StringQueryProducer("commit");
	public static StringQueryProducer DATE = new StringQueryProducer("date");
	
	
	
	
	public String getSmellName() {
		return parseString(dbObject.get("smellName")+"", "");
	}
	
	public CustomImplementationAntipattern setSmellName(String smellName) {
		dbObject.put("smellName", smellName);
		notifyChanged();
		return this;
	}
	
	public String getReason() {
		return parseString(dbObject.get("reason")+"", "");
	}
	
	public CustomImplementationAntipattern setReason(String reason) {
		dbObject.put("reason", reason);
		notifyChanged();
		return this;
	}
	
	public String getFileName() {
		return parseString(dbObject.get("fileName")+"", "");
	}
	
	public CustomImplementationAntipattern setFileName(String fileName) {
		dbObject.put("fileName", fileName);
		notifyChanged();
		return this;
	}
	
	public String getCommit() {
		return parseString(dbObject.get("commit")+"", "");
	}
	
	public CustomImplementationAntipattern setCommit(String commit) {
		dbObject.put("commit", commit);
		notifyChanged();
		return this;
	}
	
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public CustomImplementationAntipattern setDate(Date date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}

}
