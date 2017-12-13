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
package org.eclipse.crossmeter.platform.client.api.tests;

import org.junit.Test;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TestProjectImportResource extends TestAbstractResource {

	@Test
	public void testEclipse() {
		Client client = new Client(Protocol.HTTP);
		Request request = new Request(Method.POST, "http://localhost:8182/projects/import");
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode n = mapper.createObjectNode();
		n.put("url", "https://projects.eclipse.org/projects/modeling.epsilon");
		
		request.setEntity(n.toString(), MediaType.APPLICATION_JSON);
		
		Response response = client.handle(request);
		
		validateResponse(response, 201);
		
		System.out.println(response.getEntityAsText());
	}
	
	@Test
	public void testGitHub() {
		Client client = new Client(Protocol.HTTP);
		Request request = new Request(Method.POST, "http://localhost:8182/projects/import");
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode n = mapper.createObjectNode();
		n.put("url", "https://github.com/jrwilliams/gif-hook");
		
		request.setEntity(n.toString(), MediaType.APPLICATION_JSON);
		
		Response response = client.handle(request);
		
		validateResponse(response, 201);
		
		System.out.println(response.getEntityAsText());
	}
	
	@Test
	public void testSourceForge() {
	
	}
}
