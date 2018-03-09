/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.severityclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupThreadDataCollection extends PongoCollection<NewsgroupThreadData> {
	
	public NewsgroupThreadDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsgroupName");
	}
	
	public Iterable<NewsgroupThreadData> findById(String id) {
		return new IteratorIterable<NewsgroupThreadData>(new PongoCursorIterator<NewsgroupThreadData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupThreadData> findByNewsgroupName(String q) {
		return new IteratorIterable<NewsgroupThreadData>(new PongoCursorIterator<NewsgroupThreadData>(this, dbCollection.find(new BasicDBObject("newsgroupName", q + ""))));
	}
	
	public NewsgroupThreadData findOneByNewsgroupName(String q) {
		NewsgroupThreadData newsgroupThreadData = (NewsgroupThreadData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsgroupName", q + "")));
		if (newsgroupThreadData != null) {
			newsgroupThreadData.setPongoCollection(this);
		}
		return newsgroupThreadData;
	}
	

	public long countByNewsgroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsgroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupThreadData> iterator() {
		return new PongoCursorIterator<NewsgroupThreadData>(this, dbCollection.find());
	}
	
	public void add(NewsgroupThreadData newsgroupThreadData) {
		super.add(newsgroupThreadData);
	}
	
	public void remove(NewsgroupThreadData newsgroupThreadData) {
		super.remove(newsgroupThreadData);
	}
	
}
