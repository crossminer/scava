/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.bts.bugzilla;

import com.googlecode.pongo.runtime.querying.StringQueryProducer;

// protected region custom-imports on begin
// protected region custom-imports end

public class Bugzilla extends org.eclipse.scava.repository.model.BugTrackingSystem {
	
	
	// protected region custom-fields-and-methods on begin
    @Override
    public String getBugTrackerType() {
        return "bugzilla";
    }

    @Override
    public String getInstanceId() {
        return getUrl() + ':' + getProduct() + ':' + getComponent();
    }
    
	// protected region custom-fields-and-methods end
	
	public Bugzilla() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.bts.bugzilla.BugTrackingSystem");
		USERNAME.setOwningType("org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla");
		PASSWORD.setOwningType("org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla");
		PRODUCT.setOwningType("org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla");
		COMPONENT.setOwningType("org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla");
		CGIQUERYPROGRAM.setOwningType("org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla");
	}
	
	public static StringQueryProducer USERNAME = new StringQueryProducer("username"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	public static StringQueryProducer PRODUCT = new StringQueryProducer("product"); 
	public static StringQueryProducer COMPONENT = new StringQueryProducer("component"); 
	public static StringQueryProducer CGIQUERYPROGRAM = new StringQueryProducer("cgiQueryProgram"); 
	
	
	public String getUsername() {
		return parseString(dbObject.get("username")+"", "");
	}
	
	public Bugzilla setUsername(String username) {
		dbObject.put("username", username);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public Bugzilla setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	public String getProduct() {
		return parseString(dbObject.get("product")+"", "");
	}
	
	public Bugzilla setProduct(String product) {
		dbObject.put("product", product);
		notifyChanged();
		return this;
	}
	public String getComponent() {
		return parseString(dbObject.get("component")+"", "");
	}
	
	public Bugzilla setComponent(String component) {
		dbObject.put("component", component);
		notifyChanged();
		return this;
	}
	public String getCgiQueryProgram() {
		return parseString(dbObject.get("cgiQueryProgram")+"", "");
	}
	
	public Bugzilla setCgiQueryProgram(String cgiQueryProgram) {
		dbObject.put("cgiQueryProgram", cgiQueryProgram);
		notifyChanged();
		return this;
	}
	
	
	
	
}
