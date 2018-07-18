package org.eclipse.scava.metricprovider.trans.configuration.puppet.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class PuppetSmells extends PongoDB {

	public PuppetSmells() {}
	
	public PuppetSmells(DB db) {
		setDb(db);
	}
	
	protected SmellCollection smells = null;
	
	
	
	public SmellCollection getSmells() {
		return smells;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		smells = new SmellCollection(db.getCollection("PuppetSmells.smells"));
		pongoCollections.add(smells);
	}
	
}
