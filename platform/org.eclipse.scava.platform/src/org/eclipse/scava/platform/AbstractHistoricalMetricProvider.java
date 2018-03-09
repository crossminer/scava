/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

public abstract class AbstractHistoricalMetricProvider implements IHistoricalMetricProvider {
	
	
	final public String getCollectionName() {
		return this.getIdentifier().replace("org.eclipse.scava.metricprovider.", "");
	}
	
	public List<Pongo> getHistoricalMeasurements(MetricProviderContext context, Project project, Date start, Date end) {
		
		DB db = context.getProjectDB(project);
		DBCollection collection = db.getCollection(this.getCollectionName());
		
		QueryBuilder builder = QueryBuilder.start();
		if (start != null) {
			builder.and("__datetime").greaterThanEquals(start.toJavaDate());
		}
		if (end != null) {
			builder.and("__datetime").lessThanEquals(end.toJavaDate());
		}
		 
		BasicDBObject query = (BasicDBObject) builder.get(); 

		Iterator<DBObject> it = collection.find(query).iterator();
		
		List<Pongo> pongoList = new ArrayList<Pongo>();
		
		while (it.hasNext()) {
			DBObject dbObject = it.next();
			pongoList.add(PongoFactory.getInstance().createPongo(dbObject));
		}
		
		return pongoList;
		
	}
}
