/*******************************************************************************
 * Copyright (c) 2014 SCAVA Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation,
 *    Juri Di Rocco - Implementation.
 *******************************************************************************/
package org.eclipse.scava.repository.model.vcs.cvs;

import com.googlecode.pongo.runtime.querying.*;


public class CvsRepository extends org.eclipse.scava.repository.model.VcsRepository {
	
	
	
	public CvsRepository() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.vcs.cvs.VcsRepository");
		BROWSE.setOwningType("org.eclipse.scava.repository.model.vcs.cvs.CvsRepository");
		USERNAME.setOwningType("org.eclipse.scava.repository.model.vcs.cvs.CvsRepository");
		PASSWORD.setOwningType("org.eclipse.scava.repository.model.vcs.cvs.CvsRepository");
		PATH.setOwningType("org.eclipse.scava.repository.model.vcs.cvs.CvsRepository");
	}
	
	public static StringQueryProducer BROWSE = new StringQueryProducer("browse"); 
	public static StringQueryProducer USERNAME = new StringQueryProducer("username"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	public static StringQueryProducer PATH = new StringQueryProducer("path"); 
	
	
	public String getBrowse() {
		return parseString(dbObject.get("browse")+"", "");
	}
	
	public CvsRepository setBrowse(String browse) {
		dbObject.put("browse", browse);
		notifyChanged();
		return this;
	}
	public String getUsername() {
		return parseString(dbObject.get("username")+"", "");
	}
	
	public CvsRepository setUsername(String username) {
		dbObject.put("username", username);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public CvsRepository setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	public String getPath() {
		return parseString(dbObject.get("path")+"", "");
	}
	
	public CvsRepository setPath(String path) {
		dbObject.put("path", path);
		notifyChanged();
		return this;
	}
	
	
	
	
}