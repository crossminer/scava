/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.client.api.tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class TestProjectResource extends TestAbstractResource {
	
	static Mongo mongo;
	
	@BeforeClass
	public static void setup() throws Exception {
		mongo = new Mongo();
	}
	
	@AfterClass
	public static void shutdown() {
		mongo.close();
	}
	
	@Test
	public void testGet() {
		Client client = new Client(Protocol.HTTP);
		Request request = new Request(Method.GET, "http://localhost:8182/projects/p/ant");
		Response response = client.handle(request);
		
		validateResponse(response, 200);
		
		// TODO: check the JSON
	}

	@Test
	public void testPostInsert() throws Exception {
		Request request = new Request(Method.POST, "http://localhost:8182/projects/");

		ObjectMapper mapper = new ObjectMapper();
		
		ObjectNode p = mapper.createObjectNode();
		p.put("name", "test");
		p.put("shortName", "test-short");
		p.put("description", "this is a description");

		request.setEntity(p.toString(), MediaType.APPLICATION_JSON);
		
		Client client = new Client(Protocol.HTTP);
		Response response = client.handle(request);
		
		System.out.println(response.getEntity().getText() + " " + response.isEntityAvailable());
		
		validateResponse(response, 201);
		
		// Now try again, it should fail
		response = client.handle(request);
		validateResponse(response, 409);
		
		// Clean up
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("scava");
		DBCollection col = db.getCollection("projects");
		BasicDBObject query = new BasicDBObject("name", "test");
		col.remove(query);

		mongo.close();
	}
	
	@Test
	public void testPostUpdate() {
		
		
	}
	
	@Test
	public void testPostImport() {
		
		
	}
	
	@Test
	public void testPostInvalidMode() {
		
		
	}

	
}
