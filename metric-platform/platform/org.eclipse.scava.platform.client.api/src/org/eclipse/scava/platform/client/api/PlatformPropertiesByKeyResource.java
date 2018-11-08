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

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.repository.model.ProjectRepository;
import org.eclipse.scava.repository.model.Properties;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.Mongo;

public class PlatformPropertiesByKeyResource extends AbstractApiResource {

	@Override
	public Representation doRepresent(){
		Mongo mongo = null;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
			platform = new Platform(mongo);
			
			String key = (String) getRequest().getAttributes().get("key");
			
			System.out.println("Get platform properties by key ...");
			
			Properties properties = new Properties();
			ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
			for (Properties prop : projectRepo.getProperties()) {
				if (prop.getKey().equals(key))
					properties = prop;
			}
			
			StringRepresentation rep = new StringRepresentation(properties.getDbObject().toString());
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
