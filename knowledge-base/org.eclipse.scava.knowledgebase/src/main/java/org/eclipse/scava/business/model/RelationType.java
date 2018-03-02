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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Juri Di Rocco
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationType {

	private String id;
	
	public RelationType(){
		
	}
	public RelationType(String name){
		this.name = name;
	}
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
