package org.eclipse.scava.metricprovider.trans.configuration.puppet.designantipatterns.model;

import java.util.Date;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class DesignAntipattern extends Pongo {
	
	public DesignAntipattern() { 
		super();
		SMELLNAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.designantipatterns.model.DesignAntipattern");
		REASON.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.designantipatterns.model.DesignAntipattern");
		FILENAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.designantipatterns.model.DesignAntipattern");
		COMMIT.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.designantipatterns.model.DesignAntipattern");
		DATE.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.designantipatterns.model.DesignAntipattern");
	}
	
	public static StringQueryProducer SMELLNAME = new StringQueryProducer("smellName"); 
	public static StringQueryProducer REASON = new StringQueryProducer("reason");
	public static StringQueryProducer FILENAME = new StringQueryProducer("fileName");
	public static StringQueryProducer COMMIT = new StringQueryProducer("commit");
	public static StringQueryProducer DATE = new StringQueryProducer("date");
	
	
	
	
	public String getSmellName() {
		return parseString(dbObject.get("smellName")+"", "");
	}
	
	public DesignAntipattern setSmellName(String smellName) {
		dbObject.put("smellName", smellName);
		notifyChanged();
		return this;
	}
	
	public String getReason() {
		return parseString(dbObject.get("reason")+"", "");
	}
	
	public DesignAntipattern setReason(String reason) {
		dbObject.put("reason", reason);
		notifyChanged();
		return this;
	}
	
	public String getFileName() {
		return parseString(dbObject.get("fileName")+"", "");
	}
	
	public DesignAntipattern setFileName(String fileName) {
		dbObject.put("fileName", fileName);
		notifyChanged();
		return this;
	}
	
	public String getCommit() {
		return parseString(dbObject.get("commit")+"", "");
	}
	
	public DesignAntipattern setCommit(String commit) {
		dbObject.put("commit", commit);
		notifyChanged();
		return this;
	}
	
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public DesignAntipattern setDate(Date date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}

}
