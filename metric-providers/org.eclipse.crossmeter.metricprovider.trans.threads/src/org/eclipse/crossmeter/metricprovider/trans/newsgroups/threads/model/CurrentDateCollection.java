/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.newsgroups.threads.model;

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