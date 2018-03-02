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

public class RoleCollection extends PongoCollection<Role> {
	
	public RoleCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<Role> findById(String id) {
		return new IteratorIterable<Role>(new PongoCursorIterator<Role>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Role> findByName(String q) {
		return new IteratorIterable<Role>(new PongoCursorIterator<Role>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public Role findOneByName(String q) {
		Role role = (Role) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (role != null) {
			role.setPongoCollection(this);
		}
		return role;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	
	@Override
	public Iterator<Role> iterator() {
		return new PongoCursorIterator<Role>(this, dbCollection.find());
	}
	
	public void add(Role role) {
		super.add(role);
	}
	
	public void remove(Role role) {
		super.remove(role);
	}
	
}
