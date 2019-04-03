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

import java.util.Map;

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
import com.mongodb.QueryBuilder;

public class MetricVisualisationListByProjectResource extends AbstractApiResource {

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
		
		ArrayNode metricVisualisation = mapper.createArrayNode();
		for (MetricVisualisation mv : mvs.values()) {
			MetricVisualisation vis = manager.findVisualisationById(mv.getVis().path("id").textValue());
			if (vis != null) {
				DB db = platform.getMetricsRepository(project).getDb();
				JsonNode visualisation = vis.visualiseMetric(db, query);
				metricVisualisation.add(visualisation);
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
