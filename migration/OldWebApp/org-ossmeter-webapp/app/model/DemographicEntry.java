package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DemographicEntry extends Pongo {
	
	
	
	public DemographicEntry() { 
		super();
		NAME.setOwningType("model.DemographicEntry");
		VALUE.setOwningType("model.DemographicEntry");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer VALUE = new NumericalQueryProducer("value");
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public DemographicEntry setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public double getValue() {
		return parseDouble(dbObject.get("value")+"", 0.0d);
	}
	
	public DemographicEntry setValue(double value) {
		dbObject.put("value", value);
		notifyChanged();
		return this;
	}
	
	
	
	
}