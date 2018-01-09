/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform.cache.vcs;
import java.util.concurrent.ConcurrentNavigableMap;

import org.eclipse.crossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.crossmeter.repository.model.VcsRepository;
import org.mapdb.DBMaker;
import org.mapdb.Fun;

public class VcsDeltaCache implements IVcsDeltaCache {
	
	protected ConcurrentNavigableMap<Fun.Tuple3<String, String, String>, VcsRepositoryDelta> map;
	
	public VcsDeltaCache() {
		initialiseDB();
	}
	
	protected void initialiseDB() {
		 map = DBMaker.newTempTreeMap(); // TODO: Do we want to allocate local storage for this?
	}
	
	public VcsRepositoryDelta getCachedDelta(VcsRepository repository, String startRevision, String endRevision){
		return map.get(Fun.t3(repository.getUrl(),startRevision,endRevision));
	}
	
	// TODO: This needs to be bounded
	public void putDelta(VcsRepository repository, String startRevision, String endRevision, VcsRepositoryDelta delta) {
		map.put(Fun.t3(repository.getUrl(),startRevision,endRevision), delta);
	}
	
}
