package org.eclipse.scava.metricprovider.trans.migrationissuesmaracas.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class MaracasMeasurementCollection extends PongoCollection<MaracasMeasurement> {
	
	public MaracasMeasurementCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("regex");
	}
	
	public Iterable<MaracasMeasurement> findById(String id) {
		return new IteratorIterable<MaracasMeasurement>(new PongoCursorIterator<MaracasMeasurement>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<MaracasMeasurement> findByRegex(String q) {
		return new IteratorIterable<MaracasMeasurement>(new PongoCursorIterator<MaracasMeasurement>(this, dbCollection.find(new BasicDBObject("regex", q + ""))));
	}
	
	public MaracasMeasurement findOneByRegex(String q) {
		MaracasMeasurement maracasMeasurement = (MaracasMeasurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("regex", q + "")));
		if (maracasMeasurement != null) {
			maracasMeasurement.setPongoCollection(this);
		}
		return maracasMeasurement;
	}
	

	public long countByRegex(String q) {
		return dbCollection.count(new BasicDBObject("regex", q + ""));
	}
	
	@Override
	public Iterator<MaracasMeasurement> iterator() {
		return new PongoCursorIterator<MaracasMeasurement>(this, dbCollection.find());
	}
	
	public void add(MaracasMeasurement maracasMeasurement) {
		super.add(maracasMeasurement);
	}
	
	public void remove(MaracasMeasurement maracasMeasurement) {
		super.remove(maracasMeasurement);
	}
	
}