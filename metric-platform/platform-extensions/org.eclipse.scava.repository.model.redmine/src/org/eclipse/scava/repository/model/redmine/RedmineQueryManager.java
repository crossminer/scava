/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.redmine;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class RedmineQueryManager extends org.eclipse.scava.repository.model.CommunicationChannel {
	
	protected List<RedmineQuery> queries = null;
	
	
	public RedmineQueryManager() { 
		super();
		dbObject.put("queries", new BasicDBList());
		super.setSuperTypes("org.eclipse.scava.repository.model.redmine.CommunicationChannel");
	}
	
	
	
	
	
	public List<RedmineQuery> getQueries() {
		if (queries == null) {
			queries = new PongoList<RedmineQuery>(this, "queries", true);
		}
		return queries;
	}
	
	
}
