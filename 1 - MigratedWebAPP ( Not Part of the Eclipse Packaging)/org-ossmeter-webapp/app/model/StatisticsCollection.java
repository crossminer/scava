package model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class StatisticsCollection extends PongoCollection<Statistics> {
	
	public StatisticsCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<Statistics> findById(String id) {
		return new IteratorIterable<Statistics>(new PongoCursorIterator<Statistics>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<Statistics> iterator() {
		return new PongoCursorIterator<Statistics>(this, dbCollection.find());
	}
	
	public void add(Statistics statistics) {
		super.add(statistics);
	}
	
	public void remove(Statistics statistics) {
		super.remove(statistics);
	}
	
}