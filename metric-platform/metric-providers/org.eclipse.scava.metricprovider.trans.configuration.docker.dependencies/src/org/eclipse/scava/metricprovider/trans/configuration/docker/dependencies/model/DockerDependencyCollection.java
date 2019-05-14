package org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class DockerDependencyCollection extends PongoCollection<DockerDependency> {
	
	public DockerDependencyCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<DockerDependency> findById(String id) {
		return new IteratorIterable<DockerDependency>(new PongoCursorIterator<DockerDependency>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<DockerDependency> findByDockerDependenciesName(String q) {
		return new IteratorIterable<DockerDependency>(new PongoCursorIterator<DockerDependency>(this, dbCollection.find(new BasicDBObject("dependencyName", q + ""))));
	}
	
	public DockerDependency findOneByDockerDependenciesName(String q) {
		DockerDependency dockerDependency = (DockerDependency) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("dependencyName", q + "")));
		if (dockerDependency != null) {
			dockerDependency.setPongoCollection(this);
		}
		return dockerDependency;
	}
	
	
	@Override
	public Iterator<DockerDependency> iterator() {
		return new PongoCursorIterator<DockerDependency>(this, dbCollection.find());
	}
	
	public void add(DockerDependency dockerDependencies) {
		super.add(dockerDependencies);
	}
	
	public void remove(DockerDependency dockerDependencies) {
		super.remove(dockerDependencies);
	}

}
