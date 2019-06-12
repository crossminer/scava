package org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationantipatterns.model;

import java.util.Date;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class ImplementationAntipattern extends Pongo {
	
	public ImplementationAntipattern() { 
		super();
		LINE.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.Smell");
		REASON.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.Smell");
		FILENAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.Smell");
		SMELLNAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.Smell");
		COMMIT.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.Smell");
		DATE.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.Smell");
	}
	
	public static StringQueryProducer LINE = new StringQueryProducer("line"); 
	public static StringQueryProducer REASON = new StringQueryProducer("reason");
	public static StringQueryProducer FILENAME = new StringQueryProducer("fileName");
	public static StringQueryProducer SMELLNAME = new StringQueryProducer("smellName");
	public static StringQueryProducer COMMIT = new StringQueryProducer("commit");
	public static StringQueryProducer DATE = new StringQueryProducer("date");
	
	
	
	
	public String getLine() {
		return parseString(dbObject.get("line")+"", "");
	}
	
	public ImplementationAntipattern setLine(String line) {
		dbObject.put("line", line);
		notifyChanged();
		return this;
	}
	
	public String getReason() {
		return parseString(dbObject.get("reason")+"", "");
	}
	
	public ImplementationAntipattern setReason(String reason) {
		dbObject.put("reason", reason);
		notifyChanged();
		return this;
	}
	
	public String getFileName() {
		return parseString(dbObject.get("fileName")+"", "");
	}
	
	public ImplementationAntipattern setFileName(String fileName) {
		dbObject.put("fileName", fileName);
		notifyChanged();
		return this;
	}
	
	public String getSmellName() {
		return parseString(dbObject.get("smellName")+"", "");
	}
	
	public ImplementationAntipattern setSmellName(String smellName) {
		dbObject.put("smellName", smellName);
		notifyChanged();
		return this;
	}
	
	public String getCommit() {
		return parseString(dbObject.get("commit")+"", "");
	}
	
	public ImplementationAntipattern setCommit(String commit) {
		dbObject.put("commit", commit);
		notifyChanged();
		return this;
	}
	
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public ImplementationAntipattern setDate(Date date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}

}
