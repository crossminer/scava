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

public class ManagerAnalysisCollection extends PongoCollection<ManagerAnalysis> {
	
	public ManagerAnalysisCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ManagerAnalysis> findById(String id) {
		return new IteratorIterable<ManagerAnalysis>(new PongoCursorIterator<ManagerAnalysis>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<ManagerAnalysis> iterator() {
		return new PongoCursorIterator<ManagerAnalysis>(this, dbCollection.find());
	}
	
	public void add(ManagerAnalysis managerAnalysis) {
		super.add(managerAnalysis);
	}
	
	public void remove(ManagerAnalysis managerAnalysis) {
		super.remove(managerAnalysis);
	}
	
}
