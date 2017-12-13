/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.crossmeter.platform.cache.bugtrackingsystem;
import java.util.concurrent.ConcurrentNavigableMap;

import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
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
