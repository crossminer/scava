/*******************************************************************************
 * Copyright (c) 2018 Softeam
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.repository.model.ProjectRepository;
import org.eclipse.scava.repository.model.Properties;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.pongo.runtime.PongoCollection;
import com.mongodb.Mongo;

public class PlatformListPropertiesResource extends AbstractApiResource {

	@Override
	public Representation doRepresent(){
		Mongo mongo = null;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
			platform = new Platform(mongo);
			
			System.out.println("Get platform properties ...");
			
			List<Properties> allProperties = new ArrayList<>();
			ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
			for (Properties property : projectRepo.getProperties()) {
				allProperties.add(property);
			}
			
			ArrayNode listProperties = mapper.createArrayNode();
			for (Properties properties : allProperties) {
				properties.getDbObject().removeField("_id");
				properties.getDbObject().removeField("_type");
				properties.getDbObject().removeField("_superTypes");
				listProperties.add(mapper.readTree(properties.getDbObject().toString()));
			}
			
			StringRepresentation rep = new StringRepresentation(listProperties.toString());
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.SUCCESS_OK);
			return rep;

		} catch (IOException e) {
			e.printStackTrace(); // TODO
			StringRepresentation rep = new StringRepresentation("{\"status\":\"error\", \"message\" : \""+e.getMessage()+"\"}");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return rep;
		} finally {
			if (mongo != null) mongo.close();
			platform = null;
		}
	}
	
	@JsonFilter("foofilter")
	class Foo {}
	
	protected JsonNode generateRequestJson(ObjectMapper mapper, String key) {
		ObjectNode n = mapper.createObjectNode();
		n.put("key", key);
		return n;
	}
}
