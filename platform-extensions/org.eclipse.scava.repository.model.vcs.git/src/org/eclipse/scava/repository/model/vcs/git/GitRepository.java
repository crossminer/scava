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
package org.eclipse.scava.repository.model.vcs.git;

import com.googlecode.pongo.runtime.querying.*;


public class GitRepository extends org.eclipse.scava.repository.model.VcsRepository {
	
	
	
	public GitRepository() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.vcs.git.VcsRepository");
		USERNAME.setOwningType("org.eclipse.scava.repository.model.vcs.git.GitRepository");
		PASSWORD.setOwningType("org.eclipse.scava.repository.model.vcs.git.GitRepository");
	}
	
	public static StringQueryProducer USERNAME = new StringQueryProducer("username"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	
	
	public String getUsername() {
		return parseString(dbObject.get("username")+"", "");
	}
	
	public GitRepository setUsername(String username) {
		dbObject.put("username", username);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public GitRepository setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	
	
	
	
}