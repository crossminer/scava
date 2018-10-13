package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;

// protected region custom-imports on begin
public class Permission extends Pongo implements be.objectify.deadbolt.core.models.Permission {
// protected region custom-imports end	
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	public Permission() { 
		super();
		VALUE.setOwningType("model.Permission");
	}
	
	public static StringQueryProducer VALUE = new StringQueryProducer("value"); 
	
	
	public String getValue() {
		return parseString(dbObject.get("value")+"", "");
	}
	
	public Permission setValue(String value) {
		dbObject.put("value", value);
		notifyChanged();
		return this;
	}
	
	
	
	
}