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

// protected region custom-imports on begin
// protected region custom-imports end

public class LocalStorage extends Pongo {
	
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	public LocalStorage() { 
		super();
		PATH.setOwningType("org.eclipse.scava.repository.model.LocalStorage");
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
