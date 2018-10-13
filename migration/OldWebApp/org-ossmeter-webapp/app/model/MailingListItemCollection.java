package model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class MailingListItemCollection extends PongoCollection<MailingListItem> {
	
	public MailingListItemCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("email");
	}
	
	public Iterable<MailingListItem> findById(String id) {
		return new IteratorIterable<MailingListItem>(new PongoCursorIterator<MailingListItem>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<MailingListItem> findByEmail(String q) {
		return new IteratorIterable<MailingListItem>(new PongoCursorIterator<MailingListItem>(this, dbCollection.find(new BasicDBObject("email", q + ""))));
	}
	
	public MailingListItem findOneByEmail(String q) {
		MailingListItem MailingListItem = (MailingListItem) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("email", q + "")));
		if (MailingListItem != null) {
			MailingListItem.setPongoCollection(this);
		}
		return MailingListItem;
	}
	

	public long countByEmail(String q) {
		return dbCollection.count(new BasicDBObject("email", q + ""));
	}
	
	@Override
	public Iterator<MailingListItem> iterator() {
		return new PongoCursorIterator<MailingListItem>(this, dbCollection.find());
	}
	
	public void add(MailingListItem MailingListItem) {
		super.add(MailingListItem);
	}
	
	public void remove(MailingListItem MailingListItem) {
		super.remove(MailingListItem);
	}
	
}