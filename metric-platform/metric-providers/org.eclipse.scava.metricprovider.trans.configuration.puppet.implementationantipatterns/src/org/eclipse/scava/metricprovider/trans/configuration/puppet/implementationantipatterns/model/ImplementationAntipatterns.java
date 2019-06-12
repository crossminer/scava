package org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationantipatterns.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class ImplementationAntipatterns extends PongoDB {
	
	public ImplementationAntipatterns() {}
	
	public ImplementationAntipatterns(DB db) {
		setDb(db);
	}
	
	protected ImplementationAntipatternCollection antipatterns = null;
	
	protected CustomImplementationAntipatternCollection customAntipatterns = null;
	
	public ImplementationAntipatternCollection getAntipatterns() {
		return antipatterns;
	}
	
	public CustomImplementationAntipatternCollection getCustomAntipatterns() {
		return customAntipatterns;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		antipatterns = new ImplementationAntipatternCollection(db.getCollection("Puppet.implementationAntipatterns"));
		pongoCollections.add(antipatterns);
		customAntipatterns = new CustomImplementationAntipatternCollection(db.getCollection("Puppet.customImplementationAntipatterns"));
		pongoCollections.add(customAntipatterns);
	}

}
