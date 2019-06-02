package org.eclipse.scava.metricprovider.trans.configuration.docker.antipatterns.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class DockerAntipatterns extends PongoDB {
	
	public DockerAntipatterns() {}
	
	public DockerAntipatterns(DB db) {
		setDb(db);
	}
	
	protected DockerAntipatternCollection dockerAntipatterns = null;
	
	
	
	public DockerAntipatternCollection getSmells() {
		return dockerAntipatterns;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		dockerAntipatterns = new DockerAntipatternCollection(db.getCollection("Docker.antipatterns"));
		pongoCollections.add(dockerAntipatterns);
	}

}
