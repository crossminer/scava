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

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.repository.model.ProjectRepository;
import org.eclipse.scava.repository.model.Properties;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PlatformDeletePropertiesResource extends ServerResource {
	
	@Delete
	public Representation deletePlatformProperties() {
		Platform platform = Platform.getInstance();
		
		String key = (String) getRequest().getAttributes().get("key");
		
		System.out.println("Delete platform properties ...");
		
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		Properties properties = projectRepo.getProperties().findOneByKey(key);
		if (properties != null) {
			projectRepo.getProperties().remove(properties);
			platform.getProjectRepositoryManager().getProjectRepository().getProperties().sync();
		}
		
		StringRepresentation rep = new StringRepresentation(properties.getDbObject().toString());
		rep.setMediaType(MediaType.APPLICATION_JSON);
		getResponse().setStatus(Status.SUCCESS_OK);
		return rep;
	}
	
	@JsonFilter("foofilter")
	class Foo {}
	
	protected JsonNode generateRequestJson(ObjectMapper mapper, String key) {
		ObjectNode n = mapper.createObjectNode();
		n.put("key", key);
		return n;
	}
}
