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

import java.io.IOException;
import java.text.ParseException;

import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.platform.visualisation.MetricVisualisation;
import org.eclipse.crossmeter.platform.visualisation.MetricVisualisationExtensionPointManager;
import org.eclipse.crossmeter.repository.model.Project;
import org.eclipse.crossmeter.repository.model.ProjectRepository;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.QueryBuilder;

public class RawMetricResource extends AbstractApiResource {

	/**
	 * TODO: Incomplete. [12th Sept, 2013]
	 * @return
	 */
	public Representation doRepresent() {
		String projectId = (String) getRequest().getAttributes().get("projectid");
		String metricId = (String) getRequest().getAttributes().get("metricid");
		
		// FIXME: This and MetricVisualisationResource.java are EXACTLY the same.
		String agg = getQueryValue("agg");
		String start = getQueryValue("startDate");
		String end = getQueryValue("endDate");
		
		QueryBuilder builder = QueryBuilder.start();
		if (agg != null && agg != "") {
//			builder.... // TODO
		}
		try {
			if (start != null && start != "") {
				builder.and("__datetime").greaterThanEquals(new Date(start).toJavaDate());
			}
			if (end != null && end != "") {
				builder.and("__datetime").lessThanEquals(new Date(end).toJavaDate());
			}
		} catch (ParseException e) {
			return Util.generateErrorMessageRepresentation(generateRequestJson(projectId, metricId), "Invalid date. Format must be YYYYMMDD.");
		}
		
		BasicDBObject query = (BasicDBObject) builder.get(); 
		
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		Project project = projectRepo.getProjects().findOneByShortName(projectId);
		if (project == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return Util.generateErrorMessageRepresentation(generateRequestJson(projectId, metricId), "No project was found with the requested name.");
		}

		// Get collection from DB
		DB projectDB = platform.getMetricsRepository(project).getDb();
		
		MetricVisualisationExtensionPointManager manager = MetricVisualisationExtensionPointManager.getInstance();
		manager.getRegisteredVisualisations();
		MetricVisualisation vis = manager.findVisualisationById(metricId);
		
		if (vis == null) {
			return Util.generateErrorMessageRepresentation(generateRequestJson(projectId, metricId), "No visualiser found with specified ID.");
		}
		
		// TODO: okay, so we only allow people to get raw HISTORIC metrics? How would we
		// 		 return multiple collections???
		// TODO: Can we stream it? Page it? Filter and agg?
		
		DBCursor cursor = projectDB.getCollection(vis.getMetricId()).find(query);
		ArrayNode results = mapper.createArrayNode();
		
		while (cursor.hasNext()) {
			try {
				results.add(mapper.readTree(cursor.next().toString()));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		cursor.close();
		
		return Util.createJsonRepresentation(results);
	}
	
	
	private JsonNode generateRequestJson(String projectName, String metricId) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode r = mapper.createObjectNode();
		
		r.put("project", projectName);
		r.put("metricId", metricId);
		
		return r;
	}
}
