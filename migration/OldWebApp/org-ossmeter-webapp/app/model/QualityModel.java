package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class QualityModel extends QualityAspect {
	
	
	
	public QualityModel() { 
		super();
		super.setSuperTypes("model.QualityAspect","model.QualityElement");
		IDENTIFIER.setOwningType("model.QualityModel");
		NAME.setOwningType("model.QualityModel");
		JSON.setOwningType("model.QualityModel");
	}
	
	public static StringQueryProducer IDENTIFIER = new StringQueryProducer("identifier"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer JSON = new StringQueryProducer("json"); 
	
	
	public String getJson() {
		return parseString(dbObject.get("json")+"", "");
	}
	
	public QualityModel setJson(String json) {
		dbObject.put("json", json);
		notifyChanged();
		return this;
	}
	
	
	
	
}