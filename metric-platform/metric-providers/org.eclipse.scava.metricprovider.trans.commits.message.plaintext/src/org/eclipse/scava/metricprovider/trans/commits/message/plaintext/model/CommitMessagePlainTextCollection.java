package org.eclipse.scava.metricprovider.trans.commits.message.plaintext.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class CommitMessagePlainTextCollection extends PongoCollection<CommitMessagePlainText> {
	
	public CommitMessagePlainTextCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("repository");
	}
	
	public Iterable<CommitMessagePlainText> findById(String id) {
		return new IteratorIterable<CommitMessagePlainText>(new PongoCursorIterator<CommitMessagePlainText>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<CommitMessagePlainText> findByRepository(String q) {
		return new IteratorIterable<CommitMessagePlainText>(new PongoCursorIterator<CommitMessagePlainText>(this, dbCollection.find(new BasicDBObject("repository", q + ""))));
	}
	
	public CommitMessagePlainText findOneByRepository(String q) {
		CommitMessagePlainText commitMessagePlainText = (CommitMessagePlainText) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("repository", q + "")));
		if (commitMessagePlainText != null) {
			commitMessagePlainText.setPongoCollection(this);
		}
		return commitMessagePlainText;
	}
	

	public long countByRepository(String q) {
		return dbCollection.count(new BasicDBObject("repository", q + ""));
	}
	
	@Override
	public Iterator<CommitMessagePlainText> iterator() {
		return new PongoCursorIterator<CommitMessagePlainText>(this, dbCollection.find());
	}
	
	public void add(CommitMessagePlainText commitMessagePlainText) {
		super.add(commitMessagePlainText);
	}
	
	public void remove(CommitMessagePlainText commitMessagePlainText) {
		super.remove(commitMessagePlainText);
	}
	
}