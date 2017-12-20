package model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsItemCollection extends PongoCollection<NewsItem> {
	
	public NewsItemCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("user");
	}
	
	public Iterable<NewsItem> findById(String id) {
		return new IteratorIterable<NewsItem>(new PongoCursorIterator<NewsItem>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}

	public NewsItem findOneById(String id) {
		NewsItem NewsItem = (NewsItem) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("_id", id + "")));
		if (NewsItem != null) {
			NewsItem.setPongoCollection(this);
		}
		return NewsItem;
	}
	
	public Iterable<NewsItem> findByNewsType(NewsItemType q) {
		return new IteratorIterable<NewsItem>(new PongoCursorIterator<NewsItem>(this, dbCollection.find(new BasicDBObject("newsType", q.toString() + ""))));
	}
	
	public NewsItem findOneByNewsType(NewsItemType q) {
		NewsItem NewsItem = (NewsItem) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsType", q.toString() + "")));
		if (NewsItem != null) {
			NewsItem.setPongoCollection(this);
		}
		return NewsItem;
	}
	
	public Iterable<NewsItem> findByStatus(NewsItemStatus q) {
		return new IteratorIterable<NewsItem>(new PongoCursorIterator<NewsItem>(this, dbCollection.find(new BasicDBObject("status", q.toString() + ""))));
	}
	
	public NewsItem findOneByStatus(NewsItemStatus q) {
		NewsItem NewsItem = (NewsItem) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("status", q.toString() + "")));
		if (NewsItem != null) {
			NewsItem.setPongoCollection(this);
		}
		return NewsItem;
	}

	public Iterable<NewsItem> findByDate(Date q) {
		return new IteratorIterable<NewsItem>(new PongoCursorIterator<NewsItem>(this, dbCollection.find(new BasicDBObject("newsType", q))));
	}
	
	public NewsItem findOneByDate(Date q) {
		NewsItem NewsItem = (NewsItem) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsType", q)));
		if (NewsItem != null) {
			NewsItem.setPongoCollection(this);
		}
		return NewsItem;
	}

	public long countByNewsType(NewsItemType q) {
		return dbCollection.count(new BasicDBObject("newsType", q + ""));
	}
	
	@Override
	public Iterator<NewsItem> iterator() {
		return new PongoCursorIterator<NewsItem>(this, dbCollection.find());
	}
	
	public void add(NewsItem NewsItem) {
		super.add(NewsItem);
	}
	
	public void remove(NewsItem NewsItem) {
		super.remove(NewsItem);
	}
	
}