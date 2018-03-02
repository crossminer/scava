/*******************************************************************************
 * Copyright (c) 2018 aabherve
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.admin;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

public class AdminApplication extends Application {
	
	public static final String ROOT_URI = "file:///Users/nikos/git/scava/ossmeter_code/platform/org.eclipse.scava.platform.admin/static/";
	
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		Directory directory =  new Directory(getContext(), ROOT_URI);

		router.attach("/", AdminIndex.class);
		router.attach("/status/{what}", Status.class);
		router.attach("/projects/{view}", Projects.class);
		
		router.attach("/performance/projects", ProjectListAnalysis.class);
		router.attach("/performance/metrics", MetricListAnalysis.class);
		router.attach("/performance/projects/{projectId}/m/{metricId}", ProjectMetricAnalysis.class);
		router.attach("/performance/metrics/{metricId}", FullMetricAnalysis.class);
		//router.attach("/logger", LoggingInformation.class);
		router.attach("/home", directory); 
		return router;
	}
}
