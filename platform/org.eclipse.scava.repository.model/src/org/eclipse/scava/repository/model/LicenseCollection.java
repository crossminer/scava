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

public class LicenseCollection extends PongoCollection<License> {
	
	public LicenseCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
	}
	
	public Iterable<License> findById(String id) {
		return new IteratorIterable<License>(new PongoCursorIterator<License>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<License> findByName(String q) {
		return new IteratorIterable<License>(new PongoCursorIterator<License>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public License findOneByName(String q) {
		License license = (License) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (license != null) {
			license.setPongoCollection(this);
		}
		return license;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	public Iterable<License> findByUrl(String q) {
		return new IteratorIterable<License>(new PongoCursorIterator<License>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public License findOneByUrl(String q) {
		License license = (License) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (license != null) {
			license.setPongoCollection(this);
		}
		return license;
	}
	

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	
	@Override
	public Iterator<License> iterator() {
		return new PongoCursorIterator<License>(this, dbCollection.find());
	}
	
	public void add(License license) {
		super.add(license);
	}
	
	public void remove(License license) {
		super.remove(license);
	}
	
}
