package org.eclipse.scava.metricprovider.trans.configuration.docker.smells.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class DockerSmells extends PongoDB {
	
	public DockerSmells() {}
	
	public DockerSmells(DB db) {
		setDb(db);
	}
	
	protected DockerSmellCollection smells = null;
	
	
	
	public DockerSmellCollection getSmells() {
		return smells;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		smells = new DockerSmellCollection(db.getCollection("Docker.smells"));
		pongoCollections.add(smells);
	}

}
