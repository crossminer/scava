package org.eclipse.crossmeter.repository.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class CompanyCollection extends PongoCollection<Company> {
	
	public CompanyCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
	}
	
	public Iterable<Company> findById(String id) {
		return new IteratorIterable<Company>(new PongoCursorIterator<Company>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Company> findByName(String q) {
		return new IteratorIterable<Company>(new PongoCursorIterator<Company>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public Company findOneByName(String q) {
		Company company = (Company) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (company != null) {
			company.setPongoCollection(this);
		}
		return company;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	public Iterable<Company> findByUrl(String q) {
		return new IteratorIterable<Company>(new PongoCursorIterator<Company>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public Company findOneByUrl(String q) {
		Company company = (Company) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (company != null) {
			company.setPongoCollection(this);
		}
		return company;
	}
	

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	
	@Override
	public Iterator<Company> iterator() {
		return new PongoCursorIterator<Company>(this, dbCollection.find());
	}
	
	public void add(Company company) {
		super.add(company);
	}
	
	public void remove(Company company) {
		super.remove(company);
	}
	
}