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

public class ImportDataCollection extends PongoCollection<ImportData> {
	
	public ImportDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("forge");
	}
	
	public Iterable<ImportData> findById(String id) {
		return new IteratorIterable<ImportData>(new PongoCursorIterator<ImportData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ImportData> findByForge(String q) {
		return new IteratorIterable<ImportData>(new PongoCursorIterator<ImportData>(this, dbCollection.find(new BasicDBObject("forge", q + ""))));
	}
	
	public ImportData findOneByForge(String q) {
		ImportData importData = (ImportData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("forge", q + "")));
		if (importData != null) {
			importData.setPongoCollection(this);
		}
		return importData;
	}
	

	public long countByForge(String q) {
		return dbCollection.count(new BasicDBObject("forge", q + ""));
	}
	
	@Override
	public Iterator<ImportData> iterator() {
		return new PongoCursorIterator<ImportData>(this, dbCollection.find());
	}
	
	public void add(ImportData importData) {
		super.add(importData);
	}
	
	public void remove(ImportData importData) {
		super.remove(importData);
	}
	
}
