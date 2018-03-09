/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
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

public class CurrentDateCollection extends PongoCollection<CurrentDate> {
	
	public CurrentDateCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<CurrentDate> findById(String id) {
		return new IteratorIterable<CurrentDate>(new PongoCursorIterator<CurrentDate>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<CurrentDate> iterator() {
		return new PongoCursorIterator<CurrentDate>(this, dbCollection.find());
	}
	
	public void add(CurrentDate currentDate) {
		super.add(currentDate);
	}
	
	public void remove(CurrentDate currentDate) {
		super.remove(currentDate);
	}
	
}
