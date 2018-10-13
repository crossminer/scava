package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;

// protected region custom-imports on begin
public class Role extends Pongo implements be.objectify.deadbolt.core.models.Role {
// protected region custom-imports end	
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	public Role() { 
		super();
		NAME.setOwningType("model.Role");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Role setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	
	
	
	
}