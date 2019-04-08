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
import java.util.Iterator;
import java.util.List;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.analysis.data.model.MetricExecution;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class RawMetricListByProjectResource extends AbstractApiResource {
	
	private static final String ANALYSIS_SCHEDULING_DATABASE = "scava-analysis";
	private DB db;
	
    public Representation doRepresent() {
    	String projectId = (String) getRequest().getAttributes().get("projectid");
    	
		ObjectNode res = mapper.createObjectNode();

		ArrayNode metrics = mapper.createArrayNode();
		res.put("metrics", metrics);
		
		Iterator<IMetricProvider> it = platform.getMetricProviderManager().getMetricProviders().iterator();
		
		Mongo mongo;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
			return Util.generateErrorMessageRepresentation(generateRequestJson(mapper, null), "The API was unable to connect to the database.");
		}
		
		this.db = mongo.getDB(ANALYSIS_SCHEDULING_DATABASE);
		ProjectAnalysisResportory repository = new ProjectAnalysisResportory(db);
		Iterable<MetricExecution> providers = repository.getMetricExecutions().findByProjectId(projectId);
		
		List<String> metricExecutions = new ArrayList<>();
		for (MetricExecution metricExecution : providers) {
			metricExecutions.add(metricExecution.getMetricProviderId());
		}

		// TODO: do we want to return the list of all metrics, or the list of metrics that can be visualised?
		while (it.hasNext()) {
			IMetricProvider iprovider = it.next();
			if (metricExecutions.contains(iprovider.getIdentifier())) {
				ObjectNode metric = mapper.createObjectNode();
				metric.put("name", iprovider.getFriendlyName());
				metric.put("type", iprovider.getIdentifier());
				metric.put("description", iprovider.getSummaryInformation());
				metrics.add(metric);
			}
		}

		return Util.createJsonRepresentation(res);
	}

	
}
