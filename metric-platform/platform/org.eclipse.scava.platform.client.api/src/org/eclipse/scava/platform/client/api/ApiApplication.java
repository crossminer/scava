/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.client.api;

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
		router.attach("/analysis/task/create", AnalysisCreationTaskResource.class);
		router.attach("/analysis/task/update", AnalysisUpdateTaskResource.class);
		router.attach("/analysis/task/start", AnalysisStartTaskResource.class);
		router.attach("/analysis/task/stop", AnalysisStopTaskResource.class);
		router.attach("/analysis/task/reset", AnalysisResetTaskResource.class);
		router.attach("/analysis/task/promote", AnalysisPromoteTaskResource.class);
		router.attach("/analysis/task/demote", AnalysisDemoteTaskResource.class);
		router.attach("/analysis/task/pushOnWorker", AnalysisTaskPushOnWorkerResource.class);
		router.attach("/analysis/task/delete", AnalysisDeleteTaskResource.class);
		router.attach("/analysis/tasks/project/{projectid}", AnalysisTasksByProjectResource.class);
		router.attach("/analysis/tasks/status/project/{projectid}", AnalysisTasksStatusByProjectResource.class);
		router.attach("/analysis/task", AnalysisTaskByAnalysisTaskResource.class);
		router.attach("/analysis/tasks", AnalysisTasksResource.class);
		router.attach("/analysis/metricproviders", AnalysisMetricProvidersResource.class);
		router.attach("/analysis/workers", AnalysisWorkerResource.class);
		router.attach("/analysis/stacktraces", AnalysisStackTracesResource.class);
		router.attach("/platform/properties/create", PlatformCreationPropertiesResource.class);
		router.attach("/platform/properties/update", PlatformUpdatePropertiesResource.class);
		router.attach("/platform/properties/delete/{key}", PlatformDeletePropertiesResource.class);
		router.attach("/platform/properties/{key}", PlatformPropertiesByKeyResource.class);
		router.attach("/platform/properties", PlatformListPropertiesResource.class);
		router.attach("/projects/import", ProjectImportResource.class);
		router.attach("/projects/create", ProjectCreationResource.class);
		router.attach("/projects/edit", ProjectEditionResource.class);
		router.attach("/projects/delete/{projectid}", ProjectDeleteResource.class);
		router.attach("/projects/p/{projectid}", ProjectResource.class);
		router.attach("/projects/p/{projectid}/", ProjectResource.class);
		router.attach("/projects/p/{projectid}/m/{metricid}", MetricVisualisationResource.class);
		router.attach("/projects/p/{projectid}/m/{metricid}/", MetricVisualisationResource.class);
		router.attach("/metrics/p/{projectid}", MetricVisualisationListByProjectResource.class);
		router.attach("/projects/p/{projectid}/f", FactoidResource.class);
		router.attach("/projects/p/{projectid}/f/", FactoidResource.class);
		router.attach("/projects/p/{projectid}/f/{factoidid}", FactoidResource.class);
		router.attach("/projects/p/{projectid}/f/{factoidid}/", FactoidResource.class);
		router.attach("/projects/p/{projectid}/s/{metricid}", SparkResource.class);
		router.attach("/projects/p/{projectid}/s/{metricid}/", SparkResource.class);
		router.attach("/spark/{sparkid}", SparkImageResource.class);
		
		router.attach("/raw/metrics", RawMetricListResource.class);
		router.attach("/raw/metrics/", RawMetricListResource.class);
		router.attach("/raw/metrics/p/{projectid}", RawMetricListByProjectResource.class);
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
