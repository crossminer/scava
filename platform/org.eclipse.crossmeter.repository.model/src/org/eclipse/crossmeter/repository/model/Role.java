package org.eclipse.crossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Role extends NamedElement {
	
	
	
	public Role() { 
		super();
		super.setSuperTypes("org.eclipse.crossmeter.repository.model.NamedElement");
		NAME.setOwningType("org.eclipse.crossmeter.repository.model.Role");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	
	
	
	
}