package org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class PuppetDependencies extends PongoDB {
	
	public PuppetDependencies() {}
	
	public PuppetDependencies(DB db) {
		setDb(db);
	}
	
	protected PuppetDependencyCollection dependencies = null;
	
	
	
	public PuppetDependencyCollection getDependencies() {
		return dependencies;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		dependencies = new PuppetDependencyCollection(db.getCollection("Puppet.dependencies"));
		pongoCollections.add(dependencies);
	}

}
