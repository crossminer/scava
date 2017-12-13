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
package org.eclipse.crossmeter.platform.cache.communicationchannel;
import java.util.concurrent.ConcurrentNavigableMap;

import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.mapdb.DBMaker;
import org.mapdb.Fun;

public class CommunicationChannelDeltaCache implements ICommunicationChannelDeltaCache {
	
	protected ConcurrentNavigableMap<Fun.Tuple2<String, String>, CommunicationChannelDelta> map;
	
	public CommunicationChannelDeltaCache() {
		initialiseDB();
	}
	
	protected void initialiseDB() {
		 map = DBMaker.newTempTreeMap(); // TODO: Do we want to allocate local storage for this?
	}
	
	public CommunicationChannelDelta getCachedDelta(String communicationChannelUrl, Date date){
		return map.get(Fun.t2(communicationChannelUrl, date.toString()));
	}
	
	// TODO: This needs to be bounded
	public void putDelta(String communicationChannelUrl, Date date, CommunicationChannelDelta delta) {
		map.put(Fun.t2(communicationChannelUrl, date.toString()), delta);
	}
	
}
