package org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class NewsgroupsMigrationIssueCollection extends PongoCollection<NewsgroupsMigrationIssue> {
	
	public NewsgroupsMigrationIssueCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsgroupName");
	}
	
	public Iterable<NewsgroupsMigrationIssue> findById(String id) {
		return new IteratorIterable<NewsgroupsMigrationIssue>(new PongoCursorIterator<NewsgroupsMigrationIssue>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupsMigrationIssue> findByNewsgroupName(String q) {
		return new IteratorIterable<NewsgroupsMigrationIssue>(new PongoCursorIterator<NewsgroupsMigrationIssue>(this, dbCollection.find(new BasicDBObject("newsgroupName", q + ""))));
	}
	
	public NewsgroupsMigrationIssue findOneByNewsgroupName(String q) {
		NewsgroupsMigrationIssue newsgroupsMigrationIssue = (NewsgroupsMigrationIssue) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsgroupName", q + "")));
		if (newsgroupsMigrationIssue != null) {
			newsgroupsMigrationIssue.setPongoCollection(this);
		}
		return newsgroupsMigrationIssue;
	}
	

	public long countByNewsgroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsgroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupsMigrationIssue> iterator() {
		return new PongoCursorIterator<NewsgroupsMigrationIssue>(this, dbCollection.find());
	}
	
	public void add(NewsgroupsMigrationIssue newsgroupsMigrationIssue) {
		super.add(newsgroupsMigrationIssue);
	}
	
	public void remove(NewsgroupsMigrationIssue newsgroupsMigrationIssue) {
		super.remove(newsgroupsMigrationIssue);
	}
	
}