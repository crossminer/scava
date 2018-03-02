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

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * @author Juri Di Rocco
 *
 */
@Document(collection="GithubUsers")
public class GithubUser {
	@Id
	private String id;
	private String login;
	private Set<String> starredRepo = new HashSet<>();
	private Set<String> commitsToRepo = new HashSet<>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Set<String> getStarredRepo() {
		return starredRepo;
	}
	public void setStarredRepo(Set<String> starredRepo) {
		this.starredRepo = starredRepo;
	}
	public Set<String> getCommitsToRepo() {
		return commitsToRepo;
	}
	public void setCommitsToRepo(Set<String> commitsToRepo) {
		this.commitsToRepo = commitsToRepo;
	}
}
