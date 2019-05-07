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
import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MetricListResource extends AbstractApiResource {

    public Representation doRepresent() {
		ArrayNode metrics = mapper.createArrayNode();
		
		MetricVisualisationExtensionPointManager manager = MetricVisualisationExtensionPointManager.getInstance();
		Map<String, MetricVisualisation> vizs = manager.getRegisteredVisualisations();
		
		for (MetricVisualisation vis : vizs.values()) {
			ObjectNode mv = mapper.createObjectNode();
			mv = (ObjectNode) vis.getVis();
			mv.put("metricId", vis.getMetricId());
			metrics.add(mv);
		}
		
		return Util.createJsonRepresentation(metrics);
	}
}
