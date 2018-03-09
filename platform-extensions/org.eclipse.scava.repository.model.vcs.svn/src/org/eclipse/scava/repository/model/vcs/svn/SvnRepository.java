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
package org.eclipse.scava.repository.model.vcs.svn;

import com.googlecode.pongo.runtime.querying.*;


public class SvnRepository extends org.eclipse.scava.repository.model.VcsRepository {
	
	
	
	public SvnRepository() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.vcs.svn.VcsRepository");
		BROWSE.setOwningType("org.eclipse.scava.repository.model.vcs.svn.SvnRepository");
		USERNAME.setOwningType("org.eclipse.scava.repository.model.vcs.svn.SvnRepository");
		PASSWORD.setOwningType("org.eclipse.scava.repository.model.vcs.svn.SvnRepository");
	}
	
	public static StringQueryProducer BROWSE = new StringQueryProducer("browse"); 
	public static StringQueryProducer USERNAME = new StringQueryProducer("username"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	
	
	public String getBrowse() {
		return parseString(dbObject.get("browse")+"", "");
	}
	
	public SvnRepository setBrowse(String browse) {
		dbObject.put("browse", browse);
		notifyChanged();
		return this;
	}
	public String getUsername() {
		return parseString(dbObject.get("username")+"", "");
	}
	
	public SvnRepository setUsername(String username) {
		dbObject.put("username", username);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public SvnRepository setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	
	
	
	
}