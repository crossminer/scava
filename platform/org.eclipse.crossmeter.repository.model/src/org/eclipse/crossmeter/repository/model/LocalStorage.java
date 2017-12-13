package org.eclipse.crossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;

// protected region custom-imports on begin
// protected region custom-imports end

public class LocalStorage extends Pongo {
	
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	public LocalStorage() { 
		super();
		PATH.setOwningType("org.eclipse.crossmeter.repository.model.LocalStorage");
	}
	
	public static StringQueryProducer PATH = new StringQueryProducer("path"); 
	
	
	public String getPath() {
		return parseString(dbObject.get("path")+"", "");
	}
	
	public LocalStorage setPath(String path) {
		dbObject.put("path", path);
		notifyChanged();
		return this;
	}
	
	
	
	
}