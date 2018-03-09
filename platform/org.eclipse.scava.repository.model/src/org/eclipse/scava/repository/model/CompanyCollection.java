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

public class CompanyCollection extends PongoCollection<Company> {
	
	public CompanyCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
	}
	
	public Iterable<Company> findById(String id) {
		return new IteratorIterable<Company>(new PongoCursorIterator<Company>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Company> findByName(String q) {
		return new IteratorIterable<Company>(new PongoCursorIterator<Company>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public Company findOneByName(String q) {
		Company company = (Company) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (company != null) {
			company.setPongoCollection(this);
		}
		return company;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	public Iterable<Company> findByUrl(String q) {
		return new IteratorIterable<Company>(new PongoCursorIterator<Company>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public Company findOneByUrl(String q) {
		Company company = (Company) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (company != null) {
			company.setPongoCollection(this);
		}
		return company;
	}
	

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	
	@Override
	public Iterator<Company> iterator() {
		return new PongoCursorIterator<Company>(this, dbCollection.find());
	}
	
	public void add(Company company) {
		super.add(company);
	}
	
	public void remove(Company company) {
		super.remove(company);
	}
	
}
