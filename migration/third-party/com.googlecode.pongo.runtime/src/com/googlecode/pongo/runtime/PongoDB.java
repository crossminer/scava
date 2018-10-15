package com.googlecode.pongo.runtime;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;

public class PongoDB {
	
	protected DB db;
	protected List<PongoCollection> pongoCollections = new ArrayList<PongoCollection>();
	protected boolean clearPongoCacheOnSync = false;
	
	public PongoDB() {}
	
	public PongoDB(DB db) {
		setDb(db);
	}
	
	public void setDb(DB db) {
		this.db = db;
	}
	
	public List<PongoCollection> getPongoCollections() {
		return pongoCollections;
	}
	
	public void sync(boolean clearPongoCache) {
		for (PongoCollection c : pongoCollections) {
			c.sync();
		}
		if (clearPongoCache) {
			PongoFactory.getInstance().clear();
		}
	}
	
	public void setClearPongoCacheOnSync(boolean clearPongoCacheOnSync) {
		this.clearPongoCacheOnSync = clearPongoCacheOnSync;
	}
	
	public boolean isClearPongoCacheOnSync() {
		return clearPongoCacheOnSync;
	}
	
	public void sync() {
		sync(clearPongoCacheOnSync);
	}
	
}
