/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.crossmeter.platform;

import org.eclipse.crossmeter.platform.logging.OssmeterLoggerFactory;
import org.eclipse.crossmeter.repository.model.Project;

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
