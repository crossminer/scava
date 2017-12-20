package com.googlecode.pongo.runtime.querying;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public abstract class QueryProducer {

	protected String owningType = null;
	protected BasicDBObject dbo;
	
	public QueryProducer() {
		dbo = new BasicDBObject();
	}
	
	/**
	 * Destructive read.
	 * @return
	 */
	public BasicDBObject getDBObject(){
		if (owningType != null) {
			BasicDBObject or1 = new BasicDBObject("_type", owningType);
			BasicDBObject or2 = new BasicDBObject("_superTypes", owningType);
			
			BasicDBList ors = new BasicDBList();
			ors.add(or1);
			ors.add(or2);

			dbo.append("$or", ors);
		}
//		System.out.println(dbo); // DEBUG
		
		BasicDBObject ret = dbo;
		dbo = new BasicDBObject();
		return ret;
	}

	public void setOwningType(String owningType) {
		this.owningType = owningType;
	}
	
}
