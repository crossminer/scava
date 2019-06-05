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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.analysis.data.model.MetricExecution;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;
import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DB;

public class RawMetricListByProjectResource extends AbstractApiResource {
	
	private static final String ANALYSIS_SCHEDULING_DATABASE = "scava-analysis";
	private DB db;
	
    public Representation doRepresent() {
    	String projectId = (String) getRequest().getAttributes().get("projectid");
    	
		ObjectNode res = mapper.createObjectNode();

		ArrayNode metrics = mapper.createArrayNode();
		res.put("metrics", metrics);
		
		Iterator<IMetricProvider> it = platform.getMetricProviderManager().getMetricProviders().iterator();
		
		this.db = mongo.getDB(ANALYSIS_SCHEDULING_DATABASE);
		ProjectAnalysisResportory repository = new ProjectAnalysisResportory(this.db);
		Iterable<MetricExecution> providers = repository.getMetricExecutions().findByProjectId(projectId);
		
		List<String> metricExecutions = new ArrayList<>();
		for (MetricExecution metricExecution : providers) {
			metricExecutions.add(metricExecution.getMetricProviderId());
		}

		while (it.hasNext()) {
			IMetricProvider iprovider = it.next();
			if (metricExecutions.contains(iprovider.getIdentifier())) {
				ObjectNode metric = mapper.createObjectNode();
				metric.put("metricProviderId", iprovider.getIdentifier());
				metric.put("label", iprovider.getFriendlyName());
				metric.put("description", iprovider.getSummaryInformation());
				metrics.add(metric);
			}
		}

		return Util.createJsonRepresentation(res);
	}

	
}
