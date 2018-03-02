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

import java.util.Date;
/**
 * @author Juri Di Rocco
 *
 */
public class Clusterization {
	private Date clusterizationDate;
	private String similarityMethod;
	private String id;
	
	public Date getClusterizationDate() {
		return clusterizationDate;
	}

	public void setClusterizationDate(Date clusterizationDate) {
		this.clusterizationDate = clusterizationDate;
	}

	public String getSimilarityMethod() {
		return similarityMethod;
	}

	public void setSimilarityMethod(String similarityMethod) {
		this.similarityMethod = similarityMethod;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
