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

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.importer.exception.WrongUrlException;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class ProjectImportResource extends ServerResource {

	@Post("json")
	public Representation importProject(Representation entity) {
		
		Mongo mongo = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode obj = (ObjectNode)mapper.readTree(entity.getText());
			System.out.println(obj);
			String url = obj.get("url").toString();

			url = url.replace("\"", "");
			
			try {
				mongo = Configuration.getInstance().getMongoConnection();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
				getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
				return Util.generateErrorMessageRepresentation(generateRequestJson(mapper, null), "The API was unable to connect to the database.");
			}
			Platform platform = new Platform(mongo);
			
			ProjectImporter importer = new ProjectImporter();
			
			Project p;
			try {
				p = importer.importProject(url, platform);
			} catch (WrongUrlException e) {
				ObjectNode msg = mapper.createObjectNode();
				msg.put("status", "error");
				msg.put("msg", "Invalid URL."); 
				StringRepresentation rep = new StringRepresentation(msg.toString());
				rep.setMediaType(MediaType.APPLICATION_JSON);
				getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				return rep;
			}
			
			if (p == null) {
				ObjectNode msg = mapper.createObjectNode();
				msg.put("status", "error");
				msg.put("msg", "Unable to import project.");
				StringRepresentation rep = new StringRepresentation(msg.toString());
				rep.setMediaType(MediaType.APPLICATION_JSON);
				getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				return rep;
			}
			
			// Clean up the object
			DBObject project = p.getDbObject();
			project.removeField("storage");
			project.removeField("metricProviderData");
			project.removeField("_superTypes");
			project.removeField("_id");
			
			// FIXME: Temporary solution
			project.removeField("licenses");
			project.removeField("persons");
			
			StringRepresentation rep = new StringRepresentation(p.getDbObject().toString());
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.SUCCESS_CREATED);
			
			mongo.close();
			return rep;

		} catch (IOException e) {
			e.printStackTrace(); // TODO
			StringRepresentation rep = new StringRepresentation("");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return rep;
		} finally {
			if (mongo != null) mongo.close();
		}
	}
	
	@JsonFilter("foofilter")
	class Foo {}
	
	protected JsonNode generateRequestJson(ObjectMapper mapper, String projectName) {
		ObjectNode n = mapper.createObjectNode();
		n.put("project", projectName);
		return n;
	}
}
