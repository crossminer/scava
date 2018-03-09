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

public class SchedulingInformationCollection extends PongoCollection<SchedulingInformation> {
	
	public SchedulingInformationCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("workerIdentifier");
	}
	
	public Iterable<SchedulingInformation> findById(String id) {
		return new IteratorIterable<SchedulingInformation>(new PongoCursorIterator<SchedulingInformation>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<SchedulingInformation> findByWorkerIdentifier(String q) {
		return new IteratorIterable<SchedulingInformation>(new PongoCursorIterator<SchedulingInformation>(this, dbCollection.find(new BasicDBObject("workerIdentifier", q + ""))));
	}
	
	public SchedulingInformation findOneByWorkerIdentifier(String q) {
		SchedulingInformation schedulingInformation = (SchedulingInformation) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("workerIdentifier", q + "")));
		if (schedulingInformation != null) {
			schedulingInformation.setPongoCollection(this);
		}
		return schedulingInformation;
	}
	

	public long countByWorkerIdentifier(String q) {
		return dbCollection.count(new BasicDBObject("workerIdentifier", q + ""));
	}
	
	@Override
	public Iterator<SchedulingInformation> iterator() {
		return new PongoCursorIterator<SchedulingInformation>(this, dbCollection.find());
	}
	
	public void add(SchedulingInformation schedulingInformation) {
		super.add(schedulingInformation);
	}
	
	public void remove(SchedulingInformation schedulingInformation) {
		super.remove(schedulingInformation);
	}
	
}
