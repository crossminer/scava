/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.totaldownloadcounter.model;

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
