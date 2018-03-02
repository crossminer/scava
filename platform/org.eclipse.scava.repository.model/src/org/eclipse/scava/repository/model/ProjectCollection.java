/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ProjectCollection extends PongoCollection<Project> {
	
	public ProjectCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("shortName");
	}
	
	public Iterable<Project> findById(String id) {
		return new IteratorIterable<Project>(new PongoCursorIterator<Project>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Project> findByName(String q) {
		return new IteratorIterable<Project>(new PongoCursorIterator<Project>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public Project findOneByName(String q) {
		Project project = (Project) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (project != null) {
			project.setPongoCollection(this);
		}
		return project;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	public Iterable<Project> findByShortName(String q) {
		return new IteratorIterable<Project>(new PongoCursorIterator<Project>(this, dbCollection.find(new BasicDBObject("shortName", q + ""))));
	}
	
	public Project findOneByShortName(String q) {
		Project project = (Project) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("shortName", q + "")));
		if (project != null) {
			project.setPongoCollection(this);
		}
		return project;
	}
	

	public long countByShortName(String q) {
		return dbCollection.count(new BasicDBObject("shortName", q + ""));
	}
	
	@Override
	public Iterator<Project> iterator() {
		return new PongoCursorIterator<Project>(this, dbCollection.find());
	}
	
	public void add(Project project) {
		super.add(project);
	}
	
	public void remove(Project project) {
		super.remove(project);
	}
	
}
