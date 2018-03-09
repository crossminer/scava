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

import org.eclipse.scava.platform.logging.OssmeterLoggerFactory;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class MetricHistoryManager {
	
	protected Platform platform;
	
	public MetricHistoryManager(Platform platform) {
		this.platform = platform;
	}
	
	public void store(Project project, Date date, IHistoricalMetricProvider provider) {
		DB db = platform.getMetricsRepository(project).getDb();
		
		DBCollection collection = db.getCollection(provider.getCollectionName());

		MetricProviderContext context = new MetricProviderContext(platform, OssmeterLoggerFactory.getInstance().makeNewLoggerInstance(provider.getIdentifier()));
		context.setDate(date);
		provider.setMetricProviderContext(context);
		Pongo metric = provider.measure(project);
		DBObject dbObject = metric.getDbObject();
		
		dbObject.put("__date", date.toString());
		dbObject.put("__datetime", date.toJavaDate());
		collection.save(dbObject);
	}
}
