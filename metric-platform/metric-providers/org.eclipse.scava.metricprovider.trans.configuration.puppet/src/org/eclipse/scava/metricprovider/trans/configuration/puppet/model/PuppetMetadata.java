package org.eclipse.scava.metricprovider.trans.configuration.puppet.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class PuppetMetadata extends PongoDB {

	public PuppetMetadata() {}
	
	public PuppetMetadata(DB db) {
		setDb(db);
	}
	
	protected MetadataCollection smells = null;
	
	
	
	public MetadataCollection getSmells() {
		return smells;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		smells = new MetadataCollection(db.getCollection("Puppet.metadata"));
		pongoCollections.add(smells);
	}
	
}
