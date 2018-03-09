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

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.restlet.data.Header;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public abstract class AbstractApiResource extends ServerResource {
	
	protected Platform platform;
	protected ObjectMapper mapper;
	
	abstract public Representation doRepresent();
	
	@Get("json")
	public final Representation represent() {		
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET"));
		
		mapper = new ObjectMapper();
		
		Mongo mongo;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
			return Util.generateErrorMessageRepresentation(generateRequestJson(mapper, null), "The API was unable to connect to the database.");
		}
		platform = new Platform(mongo);

		// Delegate to resource
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor()); //FIXME: This _cannot_ stay here. A new one will get added on EVERY API call.
		Representation rep = doRepresent();
		
		mongo.close();
		return rep;
	}
	
	protected JsonNode generateRequestJson(ObjectMapper mapper, String projectName) {
		ObjectNode n = mapper.createObjectNode();
		n.put("project", projectName);
		return n;
	}
}
