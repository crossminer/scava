/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.nntp.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class NntpDataManager extends PongoDB {
	
	public NntpDataManager() {}
	
	public NntpDataManager(DB db) {
		setDb(db);
	}
	
	protected NntpDatumCollection nntpData = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public NntpDatumCollection getNntpData() {
		return nntpData;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		nntpData = new NntpDatumCollection(db.getCollection("NntpDataManager.nntpData"));
		pongoCollections.add(nntpData);
	}
}