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

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.analysis.data.model.MetricExecution;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;
import org.eclipse.scava.platform.visualisation.MetricVisualisation;
import org.eclipse.scava.platform.visualisation.MetricVisualisationExtensionPointManager;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectRepository;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.QueryBuilder;

public class MetricVisualisationListByProjectResource extends AbstractApiResource {

	private static final String ANALYSIS_SCHEDULING_DATABASE = "scava-analysis";
	private DB db;
	
	public Representation doRepresent() {
		String projectId = (String) getRequest().getAttributes().get("projectid");

		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();

		Project project = projectRepo.getProjects().findOneByShortName(projectId);
		if (project == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return Util.generateErrorMessageRepresentation(generateRequestJson(projectId),
					"No project was found with the requested name.");
		}

		MetricVisualisationExtensionPointManager manager = MetricVisualisationExtensionPointManager.getInstance();
		
		QueryBuilder builder = QueryBuilder.start();
		BasicDBObject query = (BasicDBObject) builder.get(); 
		
		Map<String, MetricVisualisation> mvs = manager.getRegisteredVisualisations();
		
		this.db = mongo.getDB(ANALYSIS_SCHEDULING_DATABASE);
		ProjectAnalysisResportory repository = new ProjectAnalysisResportory(db);
		Iterable<MetricExecution> providers = repository.getMetricExecutions().findByProjectId(projectId);
		
		List<String> metricExecutions = new ArrayList<>();
		for (MetricExecution metricExecution : providers) {
			metricExecutions.add(metricExecution.getMetricProviderId());
		}
		
		ArrayNode metricVisualisation = mapper.createArrayNode();
		for (MetricVisualisation mv : mvs.values()) {
			if (metricExecutions.contains(mv.getMetricId())) {
				MetricVisualisation vis = manager.findVisualisationById(mv.getVis().path("id").textValue());
				if (vis != null) {
					DB db = platform.getMetricsRepository(project).getDb();
					JsonNode visualisation = vis.getVisualisation(db, query);
					metricVisualisation.add(visualisation);
				}	
			}
		}

		StringRepresentation resp = new StringRepresentation(metricVisualisation.toString());
		resp.setMediaType(MediaType.APPLICATION_JSON);
		return resp;
	}
	
	private JsonNode generateRequestJson(String projectName) {
		ObjectNode r = mapper.createObjectNode();
		
		r.put("project", projectName);
		
		return r;
	}
}
