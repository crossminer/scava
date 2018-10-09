package org.eclipse.scava.platform.analysis.data.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DataStorage extends Pongo {
	
	
	
	public DataStorage() { 
		super();
		STORAGE.setOwningType("org.eclipse.scava.platform.analysis.data.model.DataStorage");
	}
	
	public static StringQueryProducer STORAGE = new StringQueryProducer("storage"); 
	
	
	public String getStorage() {
		return parseString(dbObject.get("storage")+"", "");
	}
	
	public DataStorage setStorage(String storage) {
		dbObject.put("storage", storage);
		notifyChanged();
		return this;
	}
	
	
	
	
}