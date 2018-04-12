package com.googlecode.pongo.runtime.querying;


public class NumericalQueryProducer extends FieldQueryProducer {
	
	public NumericalQueryProducer(String field) {
		super(field);
	}
	
	public NumericalQueryProducer lessThan(Number i) { //FIXME: this should not be INT!
		appendQuery("$lt", i); 
		return this;
	}
	
	public NumericalQueryProducer lessThanOrEqualTo(Number i) { //FIXME: this should not be INT!
		appendQuery("$lte", i); 
		return this;
	}
	
	public NumericalQueryProducer greaterThan(Number i) {
		appendQuery("$gt", i); 
		return this;
	}
	
	public NumericalQueryProducer greaterThanOrEqualTo(Number i) {
		appendQuery("$gte", i); 
		return this;
	}
	
	public NumericalQueryProducer notEqual(Number i) {
		appendQuery("$ne", i); 
		return this;
	}
	
	public NumericalQueryProducer equal(Number i) {
		dbo.append(field, i);
		return this;
	}
}
