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
import org.eclipse.crossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

public class CommunicationChannelContentsCache implements ICommunicationChannelContentsCache {
	
	protected HTreeMap<CommunicationChannelArticle, String> map;
	
	public CommunicationChannelContentsCache() {
		initialiseDB();
	}
	
	protected void initialiseDB() {
		 map = DBMaker.newTempHashMap(); // TODO: Do we want to allocate local storage for this?
	}
	
	@Override
	public String getCachedContents(CommunicationChannelArticle article){
		return map.get(article);
	}
	
	// TODO: This needs to be bounded
	@Override
	public void putContents(CommunicationChannelArticle article, String contents) {
		map.put(article, contents);
	}
}
