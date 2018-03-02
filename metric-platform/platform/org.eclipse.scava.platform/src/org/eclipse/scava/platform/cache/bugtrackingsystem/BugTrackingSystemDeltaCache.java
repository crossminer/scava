/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.cache.bugtrackingsystem;
import java.util.concurrent.ConcurrentNavigableMap;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.mapdb.DBMaker;
import org.mapdb.Fun;

public class BugTrackingSystemDeltaCache implements IBugTrackingSystemDeltaCache {
	
	protected ConcurrentNavigableMap<Fun.Tuple2<String, String>, BugTrackingSystemDelta> map;
	
	public BugTrackingSystemDeltaCache() {
		initialiseDB();
	}
	
	protected void initialiseDB() {
		 map = DBMaker.newTempTreeMap(); // TODO: Do we want to allocate local storage for this?
	}
	
	public BugTrackingSystemDelta getCachedDelta(String bugTrackingSystemUrl, Date date){
		return map.get(Fun.t2(bugTrackingSystemUrl, date.toString()));
	}
	
	// TODO: This needs to be bounded
	public void putDelta(String bugTrackingSystemUrl, Date date, BugTrackingSystemDelta delta) {
		map.put(Fun.t2(bugTrackingSystemUrl, date.toString()), delta);
	}
	
}
