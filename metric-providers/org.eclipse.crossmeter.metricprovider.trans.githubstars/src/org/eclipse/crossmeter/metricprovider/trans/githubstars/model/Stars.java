package org.eclipse.crossmeter.metricprovider.trans.githubstars.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class Stars extends PongoDB {
	
	public Stars() {}
	
	public Stars(DB db) {
		setDb(db);
	}
	
	protected StargazersCollection stargazers = null;
	
	
	
	public StargazersCollection getStargazers() {
		return stargazers;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		stargazers = new StargazersCollection(db.getCollection("Stars.stargazers"));
		pongoCollections.add(stargazers);
	}
}