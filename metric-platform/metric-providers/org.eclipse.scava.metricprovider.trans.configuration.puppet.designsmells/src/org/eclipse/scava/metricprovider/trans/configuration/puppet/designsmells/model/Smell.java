package org.eclipse.scava.metricprovider.trans.configuration.puppet.designsmells.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class Smell extends Pongo {
	
	public Smell() { 
		super();
		SMELLNAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.designsmells.model.Smell");
		REASON.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.designsmells.model.Smell");
		FILENAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.designsmells.model.Smell");
	}
	
	public static StringQueryProducer SMELLNAME = new StringQueryProducer("smellName"); 
	public static StringQueryProducer REASON = new StringQueryProducer("reason");
	public static StringQueryProducer FILENAME = new StringQueryProducer("fileName"); 
	
	
	
	
	public String getSmellName() {
		return parseString(dbObject.get("smellName")+"", "");
	}
	
	public Smell setSmellName(String smellName) {
		dbObject.put("smellName", smellName);
		notifyChanged();
		return this;
	}
	
	public String getReason() {
		return parseString(dbObject.get("reason")+"", "");
	}
	
	public Smell setReason(String reason) {
		dbObject.put("reason", reason);
		notifyChanged();
		return this;
	}
	
	public String getFileName() {
		return parseString(dbObject.get("fileName")+"", "");
	}
	
	public Smell setFileName(String fileName) {
		dbObject.put("fileName", fileName);
		notifyChanged();
		return this;
	}

}
