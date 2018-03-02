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

/**
 * @author Juri Di Rocco
 *
 */
public class Stargazers {
	private String login;
	private String datestamp;
	public String getLogin() {
		return login;
	}
	public void setLogin(String user) {
		this.login = user;
	}
	public String getDatestamp() {
		return datestamp;
	}
	public void setDatestamp(String datestamp) {
		this.datestamp = datestamp;
	}
	public Stargazers(){}
	public Stargazers(String user, String datestamp) {
		super();
		this.login = user;
		this.datestamp = datestamp;
	}
	

}
