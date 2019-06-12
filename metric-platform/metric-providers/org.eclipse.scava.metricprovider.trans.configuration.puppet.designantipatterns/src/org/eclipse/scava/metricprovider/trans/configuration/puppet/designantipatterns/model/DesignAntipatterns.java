package org.eclipse.scava.metricprovider.trans.configuration.puppet.designantipatterns.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class DesignAntipatterns extends PongoDB {
	
	public DesignAntipatterns() {}
	
	public DesignAntipatterns(DB db) {
		setDb(db);
	}
	
	protected DesignAntipatternCollection designAntipatterns = null;
	
	
	
	public DesignAntipatternCollection getDesignAntipatterns() {
		return designAntipatterns;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		designAntipatterns = new DesignAntipatternCollection(db.getCollection("Puppet.designAntipatterns"));
		pongoCollections.add(designAntipatterns);
	}

}
