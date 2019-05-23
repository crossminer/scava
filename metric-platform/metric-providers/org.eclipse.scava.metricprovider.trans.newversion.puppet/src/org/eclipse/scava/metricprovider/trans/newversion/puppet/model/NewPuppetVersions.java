package org.eclipse.scava.metricprovider.trans.newversion.puppet.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class NewPuppetVersions extends PongoDB {
	public NewPuppetVersions() {}
	
	public NewPuppetVersions(DB db) {
		setDb(db);
	}
	
	protected NewVersionCollection newVersions = null;
	
	
	
	public NewVersionCollection getNewVersions() {
		return newVersions;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newVersions = new NewVersionCollection(db.getCollection("newVersions.puppet"));
		pongoCollections.add(newVersions);
	}
}
