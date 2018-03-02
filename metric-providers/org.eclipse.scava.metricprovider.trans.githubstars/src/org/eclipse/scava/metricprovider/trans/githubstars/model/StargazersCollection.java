/*******************************************************************************
 * Copyright (c) 2018 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.githubstars.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class StargazersCollection extends PongoCollection<Stargazers> {
	
	public StargazersCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<Stargazers> findById(String id) {
		return new IteratorIterable<Stargazers>(new PongoCursorIterator<Stargazers>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<Stargazers> iterator() {
		return new PongoCursorIterator<Stargazers>(this, dbCollection.find());
	}
	
	public void add(Stargazers stargazers) {
		super.add(stargazers);
	}
	
	public void remove(Stargazers stargazers) {
		super.remove(stargazers);
	}
	
}
