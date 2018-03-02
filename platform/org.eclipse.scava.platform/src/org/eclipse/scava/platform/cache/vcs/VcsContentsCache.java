/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.cache.vcs;
import org.eclipse.scava.platform.delta.vcs.VcsCommitItem;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

public class VcsContentsCache implements IVcsContentsCache {
	
	protected HTreeMap<VcsCommitItem, String> map;
	
	public VcsContentsCache() {
		initialiseDB();
	}
	
	protected void initialiseDB() {
		 map = DBMaker.newTempHashMap(); // TODO: Do we want to allocate local storage for this?
	}
	
	@Override
	public String getCachedContents(VcsCommitItem item){
		return map.get(item);
	}
	
	// TODO: This needs to be bounded
	@Override
	public void putContents(VcsCommitItem item, String contents) {
		map.put(item, contents);
	}
}
