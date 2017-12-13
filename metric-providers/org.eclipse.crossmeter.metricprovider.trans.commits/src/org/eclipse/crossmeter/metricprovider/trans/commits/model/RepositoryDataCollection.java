/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.commits.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class RepositoryDataCollection extends PongoCollection<RepositoryData> {
	
	public RepositoryDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
	}
	
	public Iterable<RepositoryData> findById(String id) {
		return new IteratorIterable<RepositoryData>(new PongoCursorIterator<RepositoryData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<RepositoryData> findByUrl(String q) {
		return new IteratorIterable<RepositoryData>(new PongoCursorIterator<RepositoryData>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public RepositoryData findOneByUrl(String q) {
		RepositoryData repositoryData = (RepositoryData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (repositoryData != null) {
			repositoryData.setPongoCollection(this);
		}
		return repositoryData;
	}
	

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	
	@Override
	public Iterator<RepositoryData> iterator() {
		return new PongoCursorIterator<RepositoryData>(this, dbCollection.find());
	}
	
	public void add(RepositoryData repositoryData) {
		super.add(repositoryData);
	}
	
	public void remove(RepositoryData repositoryData) {
		super.remove(repositoryData);
	}
	
}