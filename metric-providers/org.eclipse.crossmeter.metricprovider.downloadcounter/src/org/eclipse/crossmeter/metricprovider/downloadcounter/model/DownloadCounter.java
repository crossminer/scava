/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation,
 *    Juri Di Rocco - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.downloadcounter.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class DownloadCounter extends PongoDB {
	
	public DownloadCounter() {}
	
	public DownloadCounter(DB db) {
		setDb(db);
	}
	
	protected DownloadCollection downloads = null;
	
	
	
	public DownloadCollection getDownloads() {
		return downloads;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		downloads = new DownloadCollection(db.getCollection("downloads"));
		pongoCollections.add(downloads);
	}
}