package org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class CustomSmell extends Pongo {
	
	public CustomSmell() { 
		super();
		SMELLNAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.CustomSmell");
		REASON.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.CustomSmell");
		FILENAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.CustomSmell");
	}
	
	public static StringQueryProducer SMELLNAME = new StringQueryProducer("smellName"); 
	public static StringQueryProducer REASON = new StringQueryProducer("reason");
	public static StringQueryProducer FILENAME = new StringQueryProducer("fileName"); 
	
	
	
	
	public String getSmellName() {
		return parseString(dbObject.get("smellName")+"", "");
	}
	
	public CustomSmell setSmellName(String smellName) {
		dbObject.put("smellName", smellName);
		notifyChanged();
		return this;
	}
	
	public String getReason() {
		return parseString(dbObject.get("reason")+"", "");
	}
	
	public CustomSmell setReason(String reason) {
		dbObject.put("reason", reason);
		notifyChanged();
		return this;
	}
	
	public String getFileName() {
		return parseString(dbObject.get("fileName")+"", "");
	}
	
	public CustomSmell setFileName(String fileName) {
		dbObject.put("fileName", fileName);
		notifyChanged();
		return this;
	}

}
