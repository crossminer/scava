/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.cache.communicationchannel;
import java.util.concurrent.ConcurrentNavigableMap;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
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
