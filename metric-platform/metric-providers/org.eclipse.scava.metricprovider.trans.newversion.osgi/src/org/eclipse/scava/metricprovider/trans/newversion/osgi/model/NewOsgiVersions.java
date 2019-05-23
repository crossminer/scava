package org.eclipse.scava.metricprovider.trans.newversion.osgi.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class NewOsgiVersions extends PongoDB {
	public NewOsgiVersions() {}
	
	public NewOsgiVersions(DB db) {
		setDb(db);
	}
	
	protected NewVersionCollection newVersions = null;
	
	
	
	public NewVersionCollection getNewVersions() {
		return newVersions;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newVersions = new NewVersionCollection(db.getCollection("newVersions.osgi"));
		pongoCollections.add(newVersions);
	}
}
