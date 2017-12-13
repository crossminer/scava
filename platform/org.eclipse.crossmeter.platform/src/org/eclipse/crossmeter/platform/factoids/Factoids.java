/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.crossmeter.platform.factoids;

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