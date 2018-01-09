/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform;

import java.util.Iterator;

import com.googlecode.pongo.runtime.PongoCollection;
import com.mongodb.DBCollection;

public class MetricCollection<T extends Object>  extends PongoCollection{

	public MetricCollection(DBCollection dbCollection) {
		super(dbCollection);
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
