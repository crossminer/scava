package com.googlecode.pongo.runtime.querying;

public class StringQueryProducer extends FieldQueryProducer {
	
	public StringQueryProducer(String field) {
		super(field);
	}
	
	public StringQueryProducer regex(String regex) {
		appendQuery("$regex", regex);
		return this;
	}
}
