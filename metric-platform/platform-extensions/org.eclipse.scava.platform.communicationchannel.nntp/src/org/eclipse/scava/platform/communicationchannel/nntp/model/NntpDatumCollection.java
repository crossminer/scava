/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.nntp.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NntpDatumCollection extends PongoCollection<NntpDatum> {
	
	public NntpDatumCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("ossmerterId");
	}
	
	public Iterable<NntpDatum> findById(String id) {
		return new IteratorIterable<NntpDatum>(new PongoCursorIterator<NntpDatum>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NntpDatum> findByOssmerterId(String q) {
		return new IteratorIterable<NntpDatum>(new PongoCursorIterator<NntpDatum>(this, dbCollection.find(new BasicDBObject("ossmerterId", q + ""))));
	}
	
	public NntpDatum findOneByOssmerterId(String q) {
		NntpDatum nntpDatum = (NntpDatum) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("ossmerterId", q + "")));
		if (nntpDatum != null) {
			nntpDatum.setPongoCollection(this);
		}
		return nntpDatum;
	}
	

	public long countByOssmerterId(String q) {
		return dbCollection.count(new BasicDBObject("ossmerterId", q + ""));
	}
	
	@Override
	public Iterator<NntpDatum> iterator() {
		return new PongoCursorIterator<NntpDatum>(this, dbCollection.find());
	}
	
	public void add(NntpDatum nntpDatum) {
		super.add(nntpDatum);
	}
	
	public void remove(NntpDatum nntpDatum) {
		super.remove(nntpDatum);
	}
	
}