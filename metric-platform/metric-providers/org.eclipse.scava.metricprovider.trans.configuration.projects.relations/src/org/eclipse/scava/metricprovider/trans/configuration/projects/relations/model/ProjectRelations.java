package org.eclipse.scava.metricprovider.trans.configuration.projects.relations.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class ProjectRelations extends PongoDB {
	
	public ProjectRelations() {}
	
	public ProjectRelations(DB db) {
		setDb(db);
	}
	
	protected ProjectRelationCollection relations = null;
	
	
	
	public ProjectRelationCollection getRelations() {
		return relations;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		relations = new ProjectRelationCollection(db.getCollection("Projects.relations"));
		pongoCollections.add(relations);
	}

}
