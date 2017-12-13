package org.eclipse.crossmeter.repository.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ProjectErrorCollection extends PongoCollection<ProjectError> {
	
	public ProjectErrorCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ProjectError> findById(String id) {
		return new IteratorIterable<ProjectError>(new PongoCursorIterator<ProjectError>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<ProjectError> iterator() {
		return new PongoCursorIterator<ProjectError>(this, dbCollection.find());
	}
	
	public void add(ProjectError projectError) {
		super.add(projectError);
	}
	
	public void remove(ProjectError projectError) {
		super.remove(projectError);
	}
	
}