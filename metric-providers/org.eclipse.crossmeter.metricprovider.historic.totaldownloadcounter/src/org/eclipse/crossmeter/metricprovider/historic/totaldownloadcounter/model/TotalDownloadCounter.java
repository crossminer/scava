/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * James Williams
 *  - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.historic.totaldownloadcounter.model;

import com.googlecode.pongo.runtime.Pongo;


public class TotalDownloadCounter extends Pongo {
	
	
	
	public TotalDownloadCounter() { 
		super();
	}
	
	public int getDownloads() {
		return parseInteger(dbObject.get("downloads")+"", 0);
	}
	
	public TotalDownloadCounter setDownloads(int downloads) {
		dbObject.put("downloads", downloads + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}