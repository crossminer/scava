package org.eclipse.scava.metricprovider.trans.configuration.puppet.designsmells.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class DesignSmells extends PongoDB {
	
	public DesignSmells() {}
	
	public DesignSmells(DB db) {
		setDb(db);
	}
	
	protected DesignSmellCollection smells = null;
	
	
	
	public DesignSmellCollection getSmells() {
		return smells;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		smells = new DesignSmellCollection(db.getCollection("Puppet.designSmells"));
		pongoCollections.add(smells);
	}

}
