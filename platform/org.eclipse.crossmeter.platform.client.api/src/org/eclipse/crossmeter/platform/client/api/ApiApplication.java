/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.client.api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class ApiApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		
		router.attach("/", PingResource.class); 	
		router.attach("/search", SearchProjectResource.class);
		router.attach("/search/", SearchProjectResource.class);
		router.attach("/metrics", MetricListResource.class);
		router.attach("/metrics/", MetricListResource.class);
		router.attach("/factoids", FactoidListResource.class);
		router.attach("/factoids/", FactoidListResource.class);
		router.attach("/projects", ProjectListResource.class);
		router.attach("/projects/", ProjectListResource.class);
		router.attach("/projects/import", ProjectImportResource.class);
		router.attach("/projects/create", ProjectCreationResource.class);
		router.attach("/projects/p/{projectid}", ProjectResource.class);
		router.attach("/projects/p/{projectid}/", ProjectResource.class);
		router.attach("/projects/p/{projectid}/m/{metricid}", MetricVisualisationResource.class);
		router.attach("/projects/p/{projectid}/m/{metricid}/", MetricVisualisationResource.class);
		router.attach("/projects/p/{projectid}/f", FactoidResource.class);
		router.attach("/projects/p/{projectid}/f/", FactoidResource.class);
		router.attach("/projects/p/{projectid}/f/{factoidid}", FactoidResource.class);
		router.attach("/projects/p/{projectid}/f/{factoidid}/", FactoidResource.class);
		router.attach("/projects/p/{projectid}/s/{metricid}", SparkResource.class);
		router.attach("/projects/p/{projectid}/s/{metricid}/", SparkResource.class);
		router.attach("/spark/{sparkid}", SparkImageResource.class);
		
		router.attach("/raw/metrics", RawMetricListResource.class);
		router.attach("/raw/metrics/", RawMetricListResource.class);
		router.attach("/raw/projects", ProjectListResource.class);
		router.attach("/raw/projects/", ProjectListResource.class);
		router.attach("/raw/projects/{page}", ProjectListResource.class);
		router.attach("/raw/projects/{page}/", ProjectListResource.class);
		router.attach("/raw/projects/p/{projectid}", ProjectResource.class);
		router.attach("/raw/projects/p/{projectid}/", ProjectResource.class);
		router.attach("/raw/projects/p/{projectid}/m/{metricid}", RawMetricResource.class);
		router.attach("/raw/projects/p/{projectid}/m/{metricid}/", RawMetricResource.class);
		
		return router;
	}
}
