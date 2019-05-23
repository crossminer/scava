package org.eclipse.scava.metricprovider.trans.newversion.docker.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class NewDockerVersions extends PongoDB {
	public NewDockerVersions() {}
	
	public NewDockerVersions(DB db) {
		setDb(db);
	}
	
	protected NewVersionCollection newVersions = null;
	
	
	
	public NewVersionCollection getNewVersions() {
		return newVersions;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newVersions = new NewVersionCollection(db.getCollection("newVersions.docker"));
		pongoCollections.add(newVersions);
	}
}
