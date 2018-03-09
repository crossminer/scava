/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ImportData extends Pongo {
	
	
	
	public ImportData() { 
		super();
		FORGE.setOwningType("org.eclipse.scava.repository.model.ImportData");
		LASTIMPORTEDPROJECT.setOwningType("org.eclipse.scava.repository.model.ImportData");
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
