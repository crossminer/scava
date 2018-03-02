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

import java.util.Iterator;

import org.eclipse.scava.platform.IMetricProvider;
import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RawMetricListResource extends AbstractApiResource {
	
    public Representation doRepresent() {
		ObjectNode res = mapper.createObjectNode();

		ArrayNode metrics = mapper.createArrayNode();
		res.put("metrics", metrics);
		
		Iterator<IMetricProvider> it = platform.getMetricProviderManager().getMetricProviders().iterator();
		
		// TODO: do we want to return the list of all metrics, or the list of metrics that can be visualised?
		while (it.hasNext()) {
			ObjectNode metric = mapper.createObjectNode();
			metrics.add(metric);
			
			IMetricProvider ip =  it.next();
			
			metric.put("name", ip.getFriendlyName());
			metric.put("type", ip.getClass().getName());
			metric.put("description", ip.getSummaryInformation());
		}

		return Util.createJsonRepresentation(res);
	}

	
}
