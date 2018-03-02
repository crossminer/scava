/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Juri Di Rocco
 *
 */
@Document(collection="Role")
public class Role implements java.io.Serializable {

	public static final Role ADMIN;
	public static final Role USER;
	static {
		ADMIN = new Role();
		ADMIN.setName("admin");

		USER = new Role();
		USER.setName("user");
		
	}
	private static final long serialVersionUID = 4824076783845229081L;

	private String name = null;

	private String id;

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		name = newName;
	}

	public String getId() {
		return id;
	}

	public void setId(String newId) {
		id = newId;
	}
	@Override

	public String toString() {
		return "Role " + " [name: " + getName() + "]" + " [id: " + getId()
				+ "]";
	}
}
