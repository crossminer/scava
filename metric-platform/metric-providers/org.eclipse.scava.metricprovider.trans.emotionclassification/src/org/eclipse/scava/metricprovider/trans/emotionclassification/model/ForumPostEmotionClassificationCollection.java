/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.emotionclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ForumPostEmotionClassificationCollection extends PongoCollection<ForumPostEmotionClassification> {
	
	public ForumPostEmotionClassificationCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ForumPostEmotionClassification> findById(String id) {
		return new IteratorIterable<ForumPostEmotionClassification>(new PongoCursorIterator<ForumPostEmotionClassification>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<ForumPostEmotionClassification> iterator() {
		return new PongoCursorIterator<ForumPostEmotionClassification>(this, dbCollection.find());
	}
	
	public void add(ForumPostEmotionClassification forumPostEmotionClassification) {
		super.add(forumPostEmotionClassification);
	}
	
	public void remove(ForumPostEmotionClassification forumPostEmotionClassification) {
		super.remove(forumPostEmotionClassification);
	}
	
}