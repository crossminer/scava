package model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class TagCollection extends PongoCollection<Tag> {
	
	public TagCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<Tag> findById(String id) {
		return new IteratorIterable<Tag>(new PongoCursorIterator<Tag>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<Tag> iterator() {
		return new PongoCursorIterator<Tag>(this, dbCollection.find());
	}
	
	public void add(Tag tag) {
		super.add(tag);
	}
	
	public void remove(Tag tag) {
		super.remove(tag);
	}
	
}