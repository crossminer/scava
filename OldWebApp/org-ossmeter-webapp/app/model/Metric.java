package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Metric extends Pongo {
	
	
	
	public Metric() { 
		super();
		ID.setOwningType("model.Metric");
		NAME.setOwningType("model.Metric");
	}
	
	public static StringQueryProducer ID = new StringQueryProducer("id"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	public String getId() {
		return parseString(dbObject.get("id")+"", "");
	}
	
	public Metric setId(String id) {
		dbObject.put("id", id);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Metric setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	
	
	
	
}