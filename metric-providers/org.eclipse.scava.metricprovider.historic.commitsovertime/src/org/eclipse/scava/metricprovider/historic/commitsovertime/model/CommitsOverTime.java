/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.commitsovertime.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class CommitsOverTime extends Pongo {
	
	protected List<RepositoryData> repositories = null;
	
	
	public CommitsOverTime() { 
		super();
		dbObject.put("repositories", new BasicDBList());
	}
	
	
	
	
	
	public List<RepositoryData> getRepositories() {
		if (repositories == null) {
			repositories = new PongoList<RepositoryData>(this, "repositories", true);
		}
		return repositories;
	}
	
	
}
