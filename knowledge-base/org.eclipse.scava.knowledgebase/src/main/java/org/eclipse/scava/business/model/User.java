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

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * @author Juri Di Rocco
 *
 */
@Document(collection="Users")
public class User {


	@Id
	private String id = null;
	private String firstname;
	private String lastname;
	private String email;
	private String username = null;
	private String image = null;
	//@JsonIgnore
	private String password = null;
	private boolean enabled;
	private List<Role> roles = new ArrayList<>();

	public String getUsername() {
		return username;
	}

	
	public void setUsername(String newUsername) {
		username = newUsername;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getPassword() {
		return password;
	}

	
	public void setPassword(String newPassword) {
		password = newPassword;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String newId) {
		id = newId;
	}

	
	@Override
	public String toString() {
		return "User " + " [username: " + getUsername() + "]" + " [password: "
				+ getPassword() + "]" + " [id: " + getId() + "]";
	}

	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	public List<Role> getRoles() {
		return roles;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
