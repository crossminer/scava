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

import org.eclipse.crossmeter.platform.AbstractFactoidMetricProvider;
import org.eclipse.crossmeter.platform.IMetricProvider;
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
