/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.googlecode;

import com.googlecode.pongo.runtime.querying.*;


public class GoogleUser extends org.eclipse.scava.repository.model.Person {
	
	
	
	public GoogleUser() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.googlecode.Person");
		EMAIL.setOwningType("org.eclipse.scava.repository.model.googlecode.GoogleUser");
	}
	
	public static StringQueryProducer EMAIL = new StringQueryProducer("email"); 
	
	
	public String getEmail() {
		return parseString(dbObject.get("email")+"", "");
	}
	
	public GoogleUser setEmail(String email) {
		dbObject.put("email", email);
		notifyChanged();
		return this;
	}
	
	
	
	
}
