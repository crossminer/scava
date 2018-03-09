/*******************************************************************************
 * Copyright (c) 2017 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.rascal.trans.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;
// protected region custom-imports on begin
// protected region custom-imports end

public class RascalMetrics extends PongoDB {
	private final String collectionName;
	
	public RascalMetrics(DB db, String collectionName) {
		this.collectionName = collectionName;
		setDb(db);
	}
	
	protected MeasurementCollection measurements = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public MeasurementCollection getMeasurements() {
		return measurements;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		measurements = new MeasurementCollection(db.getCollection(collectionName));
		pongoCollections.add(measurements);
	}
}
