/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation,
 *    Juri Di Rocco - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.downloadcounter.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class DownloadCollection extends PongoCollection<Download> {
	
	public DownloadCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("date");
	}
	
	public Iterable<Download> findById(String id) {
		return new IteratorIterable<Download>(new PongoCursorIterator<Download>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Download> findByDate(String q) {
		return new IteratorIterable<Download>(new PongoCursorIterator<Download>(this, dbCollection.find(new BasicDBObject("date", q + ""))));
	}
	
	public Download findOneByDate(String q) {
		Download download = (Download) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("date", q + "")));
		if (download != null) {
			download.setPongoCollection(this);
		}
		return download;
	}

	public long countByDate(String q) {
		return dbCollection.count(new BasicDBObject("date", q + ""));
	}
	
	@Override
	public Iterator<Download> iterator() {
		return new PongoCursorIterator<Download>(this, dbCollection.find());
	}
	
	public void add(Download download) {
		super.add(download);
	}
	
	public void remove(Download download) {
		super.remove(download);
	}
	
}