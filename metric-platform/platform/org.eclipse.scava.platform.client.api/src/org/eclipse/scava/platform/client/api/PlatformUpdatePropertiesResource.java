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

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.repository.model.ProjectRepository;
import org.eclipse.scava.repository.model.Properties;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.Mongo;

public class PlatformUpdatePropertiesResource extends ServerResource {
	
	@Put
	public Representation updatePlatformProperties(Representation entity) {
		Mongo mongo = null;
		Platform platform = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			// WARNING: This is a _DESTRUCTIVE_ read. It took me AGES to realise this and it isn't in the Restlet javadoc. I hate you Restlet.
			String j = entity.getText(); 
			
			JsonNode json = mapper.readTree(j);
			
			// Translate into a Properties object. FIXME
			Properties properties = new Properties();
			String oldKey = (json.get("oldkey").asText());
			properties.setKey(json.get("key").asText());
			properties.setValue(json.get("value").asText());
			
			try {
				mongo = Configuration.getInstance().getMongoConnection();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
				getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
				return Util.generateErrorMessageRepresentation(generateRequestJson(mapper, null), "The API was unable to connect to the database.");
			}
			platform = new Platform(mongo);
			
			// TODO: Check it doesn't already exist - how?
			System.out.println("Update platform properties ...");
			
			ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
			
			Properties oldProperties = projectRepo.getProperties().findOneByKey(oldKey);
			projectRepo.getProperties().remove(oldProperties);			
			projectRepo.getProperties().add(properties);
			platform.getProjectRepositoryManager().getProjectRepository().getProperties().sync();
			
			StringRepresentation rep = new StringRepresentation(properties.getDbObject().toString());
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.SUCCESS_CREATED);
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
