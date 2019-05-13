package org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class DockerDependency extends Pongo {
	
	public DockerDependency() { 
		super();
		DEPENDENCYNAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependency");
		DEPENDENCYVERSION.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependency");
		TYPE.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependency");
		SUBTYPE.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependency");
	}
	
	public static StringQueryProducer DEPENDENCYNAME = new StringQueryProducer("dependencyName"); 
	public static StringQueryProducer DEPENDENCYVERSION = new StringQueryProducer("dependencyVersion");
	public static StringQueryProducer TYPE = new StringQueryProducer("type");
	public static StringQueryProducer SUBTYPE = new StringQueryProducer("subType");
	
	
	
	
	public String getDependencyName() {
		return parseString(dbObject.get("dependencyName")+"", "");
	}
	
	public DockerDependency setDependencyName(String dependencyName) {
		dbObject.put("dependencyName", dependencyName);
		notifyChanged();
		return this;
	}
	
	public String getDependencyVersion() {
		return parseString(dbObject.get("dependencyVersion")+"", "");
	}
	
	public DockerDependency setDependencyVersion(String dependencyVersion) {
		dbObject.put("dependencyVersion", dependencyVersion);
		notifyChanged();
		return this;
	}
	
	public String getType() {
		return parseString(dbObject.get("type")+"", "");
	}
	
	public DockerDependency setType(String type) {
		dbObject.put("type", type);
		notifyChanged();
		return this;
	}
	
	public String getSubType() {
		return parseString(dbObject.get("subType")+"", "");
	}
	
	public DockerDependency setSubType(String subType) {
		dbObject.put("subType", subType);
		notifyChanged();
		return this;
	}

}
