package org.eclipse.crossmeter.repository.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class PersonCollection extends PongoCollection<Person> {
	
	public PersonCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<Person> findById(String id) {
		return new IteratorIterable<Person>(new PongoCursorIterator<Person>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Person> findByName(String q) {
		return new IteratorIterable<Person>(new PongoCursorIterator<Person>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public Person findOneByName(String q) {
		Person person = (Person) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (person != null) {
			person.setPongoCollection(this);
		}
		return person;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	
	@Override
	public Iterator<Person> iterator() {
		return new PongoCursorIterator<Person>(this, dbCollection.find());
	}
	
	public void add(Person person) {
		super.add(person);
	}
	
	public void remove(Person person) {
		super.remove(person);
	}
	
}