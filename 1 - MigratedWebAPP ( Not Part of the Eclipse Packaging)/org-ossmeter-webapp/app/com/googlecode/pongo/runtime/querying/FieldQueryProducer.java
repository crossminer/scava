package com.googlecode.pongo.runtime.querying;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;


public abstract class FieldQueryProducer extends QueryProducer {
	
	protected final String field;
	
	public FieldQueryProducer(String field) {
		this.field = field;
	}

	public FieldQueryProducer eq(Object eq) {
		dbo.append(field, eq);
		return this;
	}
	
	public FieldQueryProducer in(Object... elements) {
		appendQuery("$in", elements);
		return this;
	}
	
	public FieldQueryProducer nin(Object... elements) {
		appendQuery("$nin", elements);
		return this;
	}
	
	protected void appendQuery(String comparison, Object to) {
		// field is a KEY
		if (dbo.containsKey(field)){
			BasicDBObject value = (BasicDBObject)dbo.get(field);
			value.append(comparison, to);
		} else {
			dbo.append(field, new BasicDBObject(comparison, to));
		}
	}
}
