package com.googlecode.pongo.runtime.querying;

import com.mongodb.BasicDBList;

public class ArrayQueryProducer extends FieldQueryProducer {
	
	public ArrayQueryProducer(String field) {
		super(field);
	}
	
	//TODO: Needs testing
	public ArrayQueryProducer all(Object... elements) {
		BasicDBList list = new BasicDBList();
		for (Object o : elements) {
			list.add(o);
		}
		appendQuery("$all", list);
		return this;
	}
	
	// TODO: Needs testing
	public ArrayQueryProducer size(int size) {
		appendQuery("$size", size);
		return this;
	}
}
