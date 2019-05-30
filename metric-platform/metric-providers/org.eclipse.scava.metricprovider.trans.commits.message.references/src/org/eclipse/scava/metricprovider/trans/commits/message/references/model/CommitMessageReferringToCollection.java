/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.commits.message.references.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class CommitMessageReferringToCollection extends PongoCollection<CommitMessageReferringTo> {
	
	public CommitMessageReferringToCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("repository");
	}
	
	public Iterable<CommitMessageReferringTo> findById(String id) {
		return new IteratorIterable<CommitMessageReferringTo>(new PongoCursorIterator<CommitMessageReferringTo>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<CommitMessageReferringTo> findByRepository(String q) {
		return new IteratorIterable<CommitMessageReferringTo>(new PongoCursorIterator<CommitMessageReferringTo>(this, dbCollection.find(new BasicDBObject("repository", q + ""))));
	}
	
	public CommitMessageReferringTo findOneByRepository(String q) {
		CommitMessageReferringTo commitMessageReferringTo = (CommitMessageReferringTo) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("repository", q + "")));
		if (commitMessageReferringTo != null) {
			commitMessageReferringTo.setPongoCollection(this);
		}
		return commitMessageReferringTo;
	}
	

	public long countByRepository(String q) {
		return dbCollection.count(new BasicDBObject("repository", q + ""));
	}
	
	@Override
	public Iterator<CommitMessageReferringTo> iterator() {
		return new PongoCursorIterator<CommitMessageReferringTo>(this, dbCollection.find());
	}
	
	public void add(CommitMessageReferringTo commitMessageReferringTo) {
		super.add(commitMessageReferringTo);
	}
	
	public void remove(CommitMessageReferringTo commitMessageReferringTo) {
		super.remove(commitMessageReferringTo);
	}
	
}