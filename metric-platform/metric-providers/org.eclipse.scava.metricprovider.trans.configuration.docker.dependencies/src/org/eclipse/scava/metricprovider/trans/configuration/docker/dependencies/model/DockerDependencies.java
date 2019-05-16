package org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class DockerDependencies extends PongoDB {
	
	public DockerDependencies() {}
	
	public DockerDependencies(DB db) {
		setDb(db);
	}
	
	protected DockerDependencyCollection dependencies = null;
	
	
	
	public DockerDependencyCollection getDependencies() {
		return dependencies;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		dependencies = new DockerDependencyCollection(db.getCollection("Docker.dependencies"));
		pongoCollections.add(dependencies);
	}

}
