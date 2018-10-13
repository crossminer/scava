package model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class LogCollection extends PongoCollection<Log> {
	
	public LogCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("user");
	}
	
	public Iterable<Log> findById(String id) {
		return new IteratorIterable<Log>(new PongoCursorIterator<Log>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Log> findByUser(String q) {
		return new IteratorIterable<Log>(new PongoCursorIterator<Log>(this, dbCollection.find(new BasicDBObject("user", q + ""))));
	}
	
	public Log findOneByUser(String q) {
		Log log = (Log) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("user", q + "")));
		if (log != null) {
			log.setPongoCollection(this);
		}
		return log;
	}
	

	public long countByUser(String q) {
		return dbCollection.count(new BasicDBObject("user", q + ""));
	}
	
	@Override
	public Iterator<Log> iterator() {
		return new PongoCursorIterator<Log>(this, dbCollection.find());
	}
	
	public void add(Log log) {
		super.add(log);
	}
	
	public void remove(Log log) {
		super.remove(log);
	}
	
}