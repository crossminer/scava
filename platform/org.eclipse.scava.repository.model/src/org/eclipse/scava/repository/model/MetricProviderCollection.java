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

public class MetricProviderCollection extends PongoCollection<MetricProvider> {
	
	public MetricProviderCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("metricProviderId");
		createIndex("type");
		createIndex("category");
	}
	
	public Iterable<MetricProvider> findById(String id) {
		return new IteratorIterable<MetricProvider>(new PongoCursorIterator<MetricProvider>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<MetricProvider> findByName(String q) {
		return new IteratorIterable<MetricProvider>(new PongoCursorIterator<MetricProvider>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public MetricProvider findOneByName(String q) {
		MetricProvider metricProvider = (MetricProvider) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (metricProvider != null) {
			metricProvider.setPongoCollection(this);
		}
		return metricProvider;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	public Iterable<MetricProvider> findByMetricProviderId(String q) {
		return new IteratorIterable<MetricProvider>(new PongoCursorIterator<MetricProvider>(this, dbCollection.find(new BasicDBObject("metricProviderId", q + ""))));
	}
	
	public MetricProvider findOneByMetricProviderId(String q) {
		MetricProvider metricProvider = (MetricProvider) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("metricProviderId", q + "")));
		if (metricProvider != null) {
			metricProvider.setPongoCollection(this);
		}
		return metricProvider;
	}
	

	public long countByMetricProviderId(String q) {
		return dbCollection.count(new BasicDBObject("metricProviderId", q + ""));
	}
	public Iterable<MetricProvider> findByType(MetricProviderType q) {
		return new IteratorIterable<MetricProvider>(new PongoCursorIterator<MetricProvider>(this, dbCollection.find(new BasicDBObject("type", q + ""))));
	}
	
	public MetricProvider findOneByType(MetricProviderType q) {
		MetricProvider metricProvider = (MetricProvider) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("type", q + "")));
		if (metricProvider != null) {
			metricProvider.setPongoCollection(this);
		}
		return metricProvider;
	}
	

	public long countByType(MetricProviderType q) {
		return dbCollection.count(new BasicDBObject("type", q + ""));
	}
	public Iterable<MetricProvider> findByCategory(MetricProviderCategory q) {
		return new IteratorIterable<MetricProvider>(new PongoCursorIterator<MetricProvider>(this, dbCollection.find(new BasicDBObject("category", q + ""))));
	}
	
	public MetricProvider findOneByCategory(MetricProviderCategory q) {
		MetricProvider metricProvider = (MetricProvider) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("category", q + "")));
		if (metricProvider != null) {
			metricProvider.setPongoCollection(this);
		}
		return metricProvider;
	}
	

	public long countByCategory(MetricProviderCategory q) {
		return dbCollection.count(new BasicDBObject("category", q + ""));
	}
	
	@Override
	public Iterator<MetricProvider> iterator() {
		return new PongoCursorIterator<MetricProvider>(this, dbCollection.find());
	}
	
	public void add(MetricProvider metricProvider) {
		super.add(metricProvider);
	}
	
	public void remove(MetricProvider metricProvider) {
		super.remove(metricProvider);
	}
	
}
