/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.threads.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ThreadDataCollection extends PongoCollection<ThreadData> {
	
	public ThreadDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("threadId");
	}
	
	public Iterable<ThreadData> findById(String id) {
		return new IteratorIterable<ThreadData>(new PongoCursorIterator<ThreadData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public long countByThreadId(int q) {
		return dbCollection.count(new BasicDBObject("threadId", q));
	}
	
	@Override
	public Iterator<ThreadData> iterator() {
		return new PongoCursorIterator<ThreadData>(this, dbCollection.find());
	}
	
	public void add(ThreadData threadData) {
		super.add(threadData);
	}
	
	public void remove(ThreadData threadData) {
		super.remove(threadData);
	}
	
}