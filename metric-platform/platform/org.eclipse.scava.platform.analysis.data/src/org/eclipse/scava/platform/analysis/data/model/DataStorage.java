/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
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