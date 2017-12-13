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
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

public class BugTrackingSystemContentsCache implements IBugTrackingSystemContentsCache {
	
	protected HTreeMap<BugTrackingSystemBug, String> bugMap;
	protected HTreeMap<BugTrackingSystemComment, String> commentMap;
	
	public BugTrackingSystemContentsCache() {
		initialiseDB();
	}
	
	protected void initialiseDB() {
		 bugMap =     DBMaker.newTempHashMap(); // TODO: Do we want to allocate local storage for this?
		 commentMap = DBMaker.newTempHashMap(); // TODO: Do we want to allocate local storage for this?
	}
	
	@Override
	public String getCachedContents(BugTrackingSystemBug bug){
		return bugMap.get(bug);
	}
	
	// TODO: This needs to be bounded
	@Override
	public void putContents(BugTrackingSystemBug bug, String contents) {
		bugMap.put(bug, contents);
	}

	@Override
	public String getCachedContents(BugTrackingSystemComment comment){
		return commentMap.get(comment);
	}
	
	// TODO: This needs to be bounded
	@Override
	public void putContents(BugTrackingSystemComment comment, String contents) {
		commentMap.put(comment, contents);
	}
}
