package org.eclipse.scava.platform.indexing;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class Indexing extends PongoDB {
	
	public Indexing() {}
	
	public Indexing(DB db) {
		setDb(db);
	}
	
	protected IndexCollection indexs = null;
	
	
	public IndexCollection getIndexs() {
		return indexs;
	}
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		indexs = new IndexCollection(db.getCollection("indexs"));
		pongoCollections.add(indexs);
	}
}