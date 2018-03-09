/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.factoids;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class Factoids extends PongoDB {
	
	public Factoids() {}
	
	public Factoids(DB db) {
		setDb(db);
	}
	
	protected FactoidCollection factoids = null;
	
	
	
	public FactoidCollection getFactoids() {
		return factoids;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		factoids = new FactoidCollection(db.getCollection("factoids"));
		pongoCollections.add(factoids);
	}
}
