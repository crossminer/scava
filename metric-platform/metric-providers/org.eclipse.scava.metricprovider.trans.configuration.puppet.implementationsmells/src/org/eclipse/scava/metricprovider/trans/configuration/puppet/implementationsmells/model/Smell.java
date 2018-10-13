package org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class Smell extends Pongo {
	
	public Smell() { 
		super();
		LINE.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.Smell");
		REASON.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.Smell");
		FILENAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.Smell");
	}
	
	public static StringQueryProducer LINE = new StringQueryProducer("line"); 
	public static StringQueryProducer REASON = new StringQueryProducer("reason");
	public static StringQueryProducer FILENAME = new StringQueryProducer("fileName"); 
	
	
	
	
	public String getLine() {
		return parseString(dbObject.get("line")+"", "");
	}
	
	public Smell setLine(String line) {
		dbObject.put("line", line);
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
