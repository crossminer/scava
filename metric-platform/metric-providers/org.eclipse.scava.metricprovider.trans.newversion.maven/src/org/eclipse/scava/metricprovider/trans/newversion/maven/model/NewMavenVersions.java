package org.eclipse.scava.metricprovider.trans.newversion.maven.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class NewMavenVersions extends PongoDB {
	public NewMavenVersions() {}
	
	public NewMavenVersions(DB db) {
		setDb(db);
	}
	
	protected NewVersionCollection newVersions = null;
	
	
	
	public NewVersionCollection getNewVersions() {
		return newVersions;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newVersions = new NewVersionCollection(db.getCollection("newVersions.maven"));
		pongoCollections.add(newVersions);
	}
}
