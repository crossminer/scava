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

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.analysis.data.model.MetricExecution;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;
import org.eclipse.scava.platform.analysis.data.model.dto.MetricProviderDTO;
import org.eclipse.scava.platform.analysis.data.types.MetricProviderKind;
import org.eclipse.scava.platform.logging.OssmeterLoggerFactory;
import org.eclipse.scava.platform.visualisation.MetricVisualisation;
import org.eclipse.scava.platform.visualisation.MetricVisualisationExtensionPointManager;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectRepository;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.QueryBuilder;
import com.mongodb.util.JSON;

public class RawMetricResource extends AbstractApiResource {

	private static final String ANALYSIS_SCHEDULING_DATABASE = "scava-analysis";
	private DB db;
	
	public Representation doRepresent() {
		
		/**
		 * Fetch data metrics for both HistoricalMetricProvider & TransientMetricProvider
		 */
		String projectId = (String) getRequest().getAttributes().get("projectid");
		String metricId = (String) getRequest().getAttributes().get("metricid");
		
		String start = getQueryValue("startDate");
		String end = getQueryValue("endDate");
		
		QueryBuilder builder = QueryBuilder.start();
		try {
			if (start != null && start != "") {
				builder.and("__datetime").greaterThanEquals(new Date(start).toJavaDate());
			}
			if (end != null && end != "") {
				builder.and("__datetime").lessThanEquals(new Date(end).toJavaDate());
			}
		} catch (ParseException e) {
			e.getStackTrace();
		}
		
		BasicDBObject query = (BasicDBObject) builder.get(); 
		
		ArrayNode results = mapper.createArrayNode();
				
		if (projectId != null && metricId != null) {
		
			Mongo mongo;
			try {
				mongo = Configuration.getInstance().getMongoConnection();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
				getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
				return Util.generateErrorMessageRepresentation(generateRequestJson(mapper, null), "The API was unable to connect to the database.");
			}
			
			this.db = mongo.getDB(ANALYSIS_SCHEDULING_DATABASE);
			ProjectAnalysisResportory repository = new ProjectAnalysisResportory(this.db);
			Iterable<MetricExecution> listMetricExecutions = repository.getMetricExecutions().findByProjectId(projectId);
			
			List<String> metricExecutions = new ArrayList<>();
			for (MetricExecution metricExecution : listMetricExecutions) {
				metricExecutions.add(metricExecution.getMetricProviderId());
			}
	
			if (metricExecutions.contains(metricId)) {
				List<IMetricProvider> platformProvider = this.platform.getMetricProviderManager().getMetricProviders();
				for (IMetricProvider iMetricProvider : platformProvider) {
					if (iMetricProvider.getIdentifier().equals(metricId)) {
						Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(projectId);
						if(iMetricProvider instanceof IHistoricalMetricProvider) {
							results.addAll(getHistoricDocuments(platform.getMetricsRepository(project).getDb().getCollection(((IHistoricalMetricProvider) iMetricProvider).getCollectionName()), query));
						} else if (iMetricProvider instanceof ITransientMetricProvider) {
							results.addAll(getTransientDocuments(((ITransientMetricProvider) iMetricProvider).adapt(platform.getMetricsRepository(project).getDb()).getPongoCollections()));
						}
						break;
					}
				}
	
			}
		
		}
		
		return Util.createJsonRepresentation(results);
	}
	
	private ArrayNode getTransientDocuments(List<PongoCollection> list) {
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode nodeArray = mapper.createArrayNode();
		for (PongoCollection pongoCollection : list) {
			for (DBObject dbObject : pongoCollection.getDbCollection().find()) {
				JsonNode jsonObj;
				try {
					jsonObj = mapper.readTree(dbObject.toString());
					nodeArray.add(jsonObj);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return nodeArray;
	}
	
	private ArrayNode getHistoricDocuments(DBCollection dbCollection, DBObject query) {
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode nodeArray = mapper.createArrayNode();		
		DBCursor cursor = dbCollection.find(query);
		while(cursor.hasNext()) {
			DBObject obj = cursor.next();
			JsonNode json;
			try {
				json = mapper.readTree(obj.toString());
				nodeArray.add(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return nodeArray;
	}
	
	private JsonNode generateRequestJson(String projectName, String metricId) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode r = mapper.createObjectNode();
		
		r.put("project", projectName);
		r.put("metricId", metricId);
		
		return r;
	}
}
