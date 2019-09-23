package org.eclipse.scava.metricprovider.trans.newsgroups.migrationissuesmaracas.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class NewsgroupMigrationIssueMaracasCollection extends PongoCollection<NewsgroupMigrationIssueMaracas> {
	
	public NewsgroupMigrationIssueMaracasCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsgroupName");
	}
	
	public Iterable<NewsgroupMigrationIssueMaracas> findById(String id) {
		return new IteratorIterable<NewsgroupMigrationIssueMaracas>(new PongoCursorIterator<NewsgroupMigrationIssueMaracas>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupMigrationIssueMaracas> findByNewsgroupName(String q) {
		return new IteratorIterable<NewsgroupMigrationIssueMaracas>(new PongoCursorIterator<NewsgroupMigrationIssueMaracas>(this, dbCollection.find(new BasicDBObject("newsgroupName", q + ""))));
	}
	
	public NewsgroupMigrationIssueMaracas findOneByNewsgroupName(String q) {
		NewsgroupMigrationIssueMaracas newsgroupMigrationIssueMaracas = (NewsgroupMigrationIssueMaracas) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsgroupName", q + "")));
		if (newsgroupMigrationIssueMaracas != null) {
			newsgroupMigrationIssueMaracas.setPongoCollection(this);
		}
		return newsgroupMigrationIssueMaracas;
	}
	

	public long countByNewsgroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsgroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupMigrationIssueMaracas> iterator() {
		return new PongoCursorIterator<NewsgroupMigrationIssueMaracas>(this, dbCollection.find());
	}
	
	public void add(NewsgroupMigrationIssueMaracas newsgroupMigrationIssueMaracas) {
		super.add(newsgroupMigrationIssueMaracas);
	}
	
	public void remove(NewsgroupMigrationIssueMaracas newsgroupMigrationIssueMaracas) {
		super.remove(newsgroupMigrationIssueMaracas);
	}
	
}