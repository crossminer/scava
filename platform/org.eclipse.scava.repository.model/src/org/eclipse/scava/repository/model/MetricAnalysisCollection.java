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

public class MetricAnalysisCollection extends PongoCollection<MetricAnalysis> {
	
	public MetricAnalysisCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<MetricAnalysis> findById(String id) {
		return new IteratorIterable<MetricAnalysis>(new PongoCursorIterator<MetricAnalysis>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<MetricAnalysis> iterator() {
		return new PongoCursorIterator<MetricAnalysis>(this, dbCollection.find());
	}
	
	public void add(MetricAnalysis metricAnalysis) {
		super.add(metricAnalysis);
	}
	
	public void remove(MetricAnalysis metricAnalysis) {
		super.remove(metricAnalysis);
	}
	
}
