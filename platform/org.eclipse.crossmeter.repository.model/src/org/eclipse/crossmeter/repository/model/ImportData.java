package org.eclipse.crossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ImportData extends Pongo {
	
	
	
	public ImportData() { 
		super();
		FORGE.setOwningType("org.eclipse.crossmeter.repository.model.ImportData");
		LASTIMPORTEDPROJECT.setOwningType("org.eclipse.crossmeter.repository.model.ImportData");
	}
	
	public static StringQueryProducer FORGE = new StringQueryProducer("forge"); 
	public static StringQueryProducer LASTIMPORTEDPROJECT = new StringQueryProducer("lastImportedProject"); 
	
	
	public String getForge() {
		return parseString(dbObject.get("forge")+"", "");
	}
	
	public ImportData setForge(String forge) {
		dbObject.put("forge", forge);
		notifyChanged();
		return this;
	}
	public String getLastImportedProject() {
		return parseString(dbObject.get("lastImportedProject")+"", "");
	}
	
	public ImportData setLastImportedProject(String lastImportedProject) {
		dbObject.put("lastImportedProject", lastImportedProject);
		notifyChanged();
		return this;
	}
	
	
	
	
}