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

public class ProjectErrorCollection extends PongoCollection<ProjectError> {
	
	public ProjectErrorCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ProjectError> findById(String id) {
		return new IteratorIterable<ProjectError>(new PongoCursorIterator<ProjectError>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<ProjectError> iterator() {
		return new PongoCursorIterator<ProjectError>(this, dbCollection.find());
	}
	
	public void add(ProjectError projectError) {
		super.add(projectError);
	}
	
	public void remove(ProjectError projectError) {
		super.remove(projectError);
	}
	
}
