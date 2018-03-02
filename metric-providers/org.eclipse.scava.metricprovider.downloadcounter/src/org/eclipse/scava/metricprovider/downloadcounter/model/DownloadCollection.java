/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.downloadcounter.model;

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
