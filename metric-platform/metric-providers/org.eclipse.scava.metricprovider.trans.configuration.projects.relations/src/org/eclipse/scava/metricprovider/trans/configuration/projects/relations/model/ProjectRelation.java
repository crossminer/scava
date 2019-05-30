package org.eclipse.scava.metricprovider.trans.configuration.projects.relations.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class ProjectRelation extends Pongo {
	
	public ProjectRelation() { 
		super();
		RELATIONNAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.projects.relations.model.ProjectRelation");
		DEPENDENCYTYPE.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.projects.relations.model.ProjectRelation");
	}
	
	public static StringQueryProducer RELATIONNAME = new StringQueryProducer("relationName"); 
	public static StringQueryProducer DEPENDENCYTYPE = new StringQueryProducer("dependencyType");
	
	
	
	
	public String getRelationName() {
		return parseString(dbObject.get("relationName")+"", "");
	}
	
	public ProjectRelation setRelationName(String relationName) {
		dbObject.put("relationName", relationName);
		notifyChanged();
		return this;
	}
	
	public String getDependencyType() {
		return parseString(dbObject.get("dependencyType")+"", "");
	}
	
	public ProjectRelation setDependencyType(String dependencyType) {
		dbObject.put("dependencyType", dependencyType);
		notifyChanged();
		return this;
	}

}
