package org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class PuppetDependency extends Pongo {
	
	public PuppetDependency() { 
		super();
		DEPENDENCYNAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model.PuppetDependency");
		DEPENDENCYVERSION.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model.PuppetDependency");
		TYPE.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model.PuppetDependency");
	}
	
	public static StringQueryProducer DEPENDENCYNAME = new StringQueryProducer("dependencyName"); 
	public static StringQueryProducer DEPENDENCYVERSION = new StringQueryProducer("dependencyVersion");
	public static StringQueryProducer TYPE = new StringQueryProducer("type");
	
	
	
	
	public String getDependencyName() {
		return parseString(dbObject.get("dependencyName")+"", "");
	}
	
	public PuppetDependency setDependencyName(String dependencyName) {
		dbObject.put("dependencyName", dependencyName);
		notifyChanged();
		return this;
	}
	
	public String getDependencyVersion() {
		return parseString(dbObject.get("dependencyVersion")+"", "");
	}
	
	public PuppetDependency setDependencyVersion(String dependencyVersion) {
		dbObject.put("dependencyVersion", dependencyVersion);
		notifyChanged();
		return this;
	}
	
	public String getType() {
		return parseString(dbObject.get("type")+"", "");
	}
	
	public PuppetDependency setType(String type) {
		dbObject.put("type", type);
		notifyChanged();
		return this;
	}

}
