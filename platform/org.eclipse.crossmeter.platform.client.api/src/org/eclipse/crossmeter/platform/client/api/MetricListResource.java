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

import java.util.Map;

import org.eclipse.crossmeter.platform.visualisation.MetricVisualisation;
import org.eclipse.crossmeter.platform.visualisation.MetricVisualisationExtensionPointManager;
import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class MetricListResource extends AbstractApiResource {

    public Representation doRepresent() {
		ArrayNode metrics = mapper.createArrayNode();
		
		MetricVisualisationExtensionPointManager manager = MetricVisualisationExtensionPointManager.getInstance();
		Map<String, MetricVisualisation> vizs = manager.getRegisteredVisualisations();
		
		for (MetricVisualisation vis : vizs.values()) {
			metrics.add(vis.getVis());
		}
		
		return Util.createJsonRepresentation(metrics);
	}
}
