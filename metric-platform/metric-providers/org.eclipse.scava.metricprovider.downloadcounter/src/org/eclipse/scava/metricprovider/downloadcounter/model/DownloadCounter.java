/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.downloadcounter.model;

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
