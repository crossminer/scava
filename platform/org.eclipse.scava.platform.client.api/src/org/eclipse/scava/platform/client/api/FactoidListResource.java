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

import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FactoidListResource extends AbstractApiResource {

    public Representation doRepresent() {
		ArrayNode node = mapper.createArrayNode();
		
		for (IMetricProvider imp : platform.getMetricProviderManager().getMetricProviders()) {
			if (imp instanceof AbstractFactoidMetricProvider) {
				ObjectNode factoid = mapper.createObjectNode();
				factoid.put("id", imp.getIdentifier());
				factoid.put("name", imp.getFriendlyName());
				factoid.put("summary", imp.getSummaryInformation());
				ArrayNode uses = mapper.createArrayNode();
				if (imp.getIdentifiersOfUses() != null && imp.getIdentifiersOfUses().size() > 0) {
					for (String use : imp.getIdentifiersOfUses()) {
						uses.add(use);
					}
				}
				factoid.put("dependencies", uses);
				
				node.add(factoid);
			}
		}
		
		getResponse().setStatus(Status.SUCCESS_OK);
		StringRepresentation resp = new StringRepresentation(node.toString());
		resp.setMediaType(MediaType.APPLICATION_JSON);
		return resp;
	}
}
