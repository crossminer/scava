package org.eclipse.scava.metricprovider.trans.bugs.requestsreplies.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BugStatisticsCollection extends PongoCollection<BugStatistics> {
	
	public BugStatisticsCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<BugStatistics> findById(String id) {
		return new IteratorIterable<BugStatistics>(new PongoCursorIterator<BugStatistics>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<BugStatistics> iterator() {
		return new PongoCursorIterator<BugStatistics>(this, dbCollection.find());
	}
	
	public void add(BugStatistics bugStatistics) {
		super.add(bugStatistics);
	}
	
	public void remove(BugStatistics bugStatistics) {
		super.remove(bugStatistics);
	}
	
}