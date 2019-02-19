/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.emotions.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class EmotionDimensionCollection extends PongoCollection<EmotionDimension> {
	
	public EmotionDimensionCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<EmotionDimension> findById(String id) {
		return new IteratorIterable<EmotionDimension>(new PongoCursorIterator<EmotionDimension>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<EmotionDimension> iterator() {
		return new PongoCursorIterator<EmotionDimension>(this, dbCollection.find());
	}
	
	public void add(EmotionDimension emotionDimension) {
		super.add(emotionDimension);
	}
	
	public void remove(EmotionDimension emotionDimension) {
		super.remove(emotionDimension);
	}
	
}
