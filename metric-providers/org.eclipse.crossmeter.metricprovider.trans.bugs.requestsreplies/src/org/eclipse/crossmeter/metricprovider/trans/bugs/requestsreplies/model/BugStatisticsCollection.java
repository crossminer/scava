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
package org.eclipse.crossmeter.metricprovider.trans.bugs.requestsreplies.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BugStatisticsCollection extends PongoCollection<BugStatistics> {
	
	public BugStatisticsCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<BugStatistics> findById(String id) {
		return new IteratorIterable<BugStatistics>(new PongoCursorIterator<BugStatistics>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<BugStatistics> iterator() {
		return new PongoCursorIterator<BugStatistics>(this, dbCollection.find());
	}
	
	public void add(BugStatistics bugStatistics) {
		super.add(bugStatistics);
	}
	
	public void remove(BugStatistics bugStatistics) {
		super.remove(bugStatistics);
	}
	
}