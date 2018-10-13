package org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class ImplementationSmells extends PongoDB {
	
	public ImplementationSmells() {}
	
	public ImplementationSmells(DB db) {
		setDb(db);
	}
	
	protected ImplementationSmellCollection smells = null;
	
	protected ImplementationCustomSmellCollection customSmells = null;
	
	public ImplementationSmellCollection getSmells() {
		return smells;
	}
	
	public ImplementationCustomSmellCollection getCustomSmells() {
		return customSmells;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		smells = new ImplementationSmellCollection(db.getCollection("Puppet.implementationSmells"));
		pongoCollections.add(smells);
		customSmells = new ImplementationCustomSmellCollection(db.getCollection("Puppet.customImplementationSmells"));
		pongoCollections.add(customSmells);
	}

}
