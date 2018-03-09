/*******************************************************************************
 * Copyright (c) 2017 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.rascal.trans.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class MeasurementCollection extends PongoCollection<Measurement> {
	public MeasurementCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("uri");
	}
	
	public Iterable<Measurement> findById(String id) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Measurement> findByUri(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("uri", q + ""))));
	}
	
	public Measurement findOneByUri(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("uri", q + "")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	
	
	public Iterable<Measurement> findIntegerMeasurementsByUri(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("uri", q + "").append("_type", "org.eclipse.scava.metricprovider.rascal.trans.model.IntegerMeasurement"))));
	}
	
	public Measurement findOneIntegerMeasurementByUri(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("uri", q + "").append("_type", "org.eclipse.scava.metricprovider.rascal.trans.model.IntegerMeasurement")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	
	
	public Iterable<Measurement> findRealMeasurementsByUri(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("uri", q + "").append("_type", "org.eclipse.scava.metricprovider.rascal.trans.model.RealMeasurement"))));
	}
	
	public Measurement findOneRealMeasurementByUri(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("uri", q + "").append("_type", "org.eclipse.scava.metricprovider.rascal.trans.model.RealMeasurement")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	
	
	public Iterable<Measurement> findBooleanMeasurementsByUri(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("uri", q + "").append("_type", "org.eclipse.scava.metricprovider.rascal.trans.model.BooleanMeasurement"))));
	}
	
	public Measurement findOneBooleanMeasurementByUri(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("uri", q + "").append("_type", "org.eclipse.scava.metricprovider.rascal.trans.model.BooleanMeasurement")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	
	
	public Iterable<Measurement> findStringMeasurementsByUri(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("uri", q + "").append("_type", "org.eclipse.scava.metricprovider.rascal.trans.model.StringMeasurement"))));
	}
	
	public Measurement findOneStringMeasurementByUri(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("uri", q + "").append("_type", "org.eclipse.scava.metricprovider.rascal.trans.model.StringMeasurement")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	
	
	public Iterable<Measurement> findListMeasurementsByUri(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("uri", q + "").append("_type", "org.eclipse.scava.metricprovider.rascal.trans.model.ListMeasurement"))));
	}
	
	public Measurement findOneListMeasurementByUri(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("uri", q + "").append("_type", "org.eclipse.scava.metricprovider.rascal.trans.model.ListMeasurement")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	
	
	public Iterable<Measurement> findSetMeasurementsByUri(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("uri", q + "").append("_type", "org.eclipse.scava.metricprovider.rascal.trans.model.SetMeasurement"))));
	}
	
	public Measurement findOneSetMeasurementByUri(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("uri", q + "").append("_type", "org.eclipse.scava.metricprovider.rascal.trans.model.SetMeasurement")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	
	
	public Iterable<Measurement> findURIMeasurementsByUri(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("uri", q + "").append("_type", "org.eclipse.scava.metricprovider.rascal.trans.model.URIMeasurement"))));
	}
	
	public Measurement findOneURIMeasurementByUri(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("uri", q + "").append("_type", "org.eclipse.scava.metricprovider.rascal.trans.model.URIMeasurement")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	

	public long countByUri(String q) {
		return dbCollection.count(new BasicDBObject("uri", q + ""));
	}
	
	@Override
	public Iterator<Measurement> iterator() {
		return new PongoCursorIterator<Measurement>(this, dbCollection.find());
	}
	
	public void add(Measurement measurement) {
		super.add(measurement);
	}
	
	public void remove(Measurement measurement) {
		super.remove(measurement);
	}
	
}
