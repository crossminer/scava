package org.eclipse.scava.metricprovider.trans.configuration.projects.relations.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class ProjectRelationCollection extends PongoCollection<ProjectRelation> {
	
	public ProjectRelationCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ProjectRelation> findById(String id) {
		return new IteratorIterable<ProjectRelation>(new PongoCursorIterator<ProjectRelation>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ProjectRelation> findByProjectRelationsName(String q) {
		return new IteratorIterable<ProjectRelation>(new PongoCursorIterator<ProjectRelation>(this, dbCollection.find(new BasicDBObject("projectName", q + ""))));
	}
	
	public ProjectRelation findOneByProjectRelationsName(String q) {
		ProjectRelation projectRelation = (ProjectRelation) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("projectName", q + "")));
		if (projectRelation != null) {
			projectRelation.setPongoCollection(this);
		}
		return projectRelation;
	}
	
	
	@Override
	public Iterator<ProjectRelation> iterator() {
		return new PongoCursorIterator<ProjectRelation>(this, dbCollection.find());
	}
	
	public void add(ProjectRelation projectRelations) {
		super.add(projectRelations);
	}
	
	public void remove(ProjectRelation projectRelations) {
		super.remove(projectRelations);
	}

}
