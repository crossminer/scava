/*******************************************************************************
 * Copyright (c) 2018 aabherve
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.admin;

import java.net.UnknownHostException;

import org.eclipse.scava.platform.Configuration;
import org.restlet.data.Header;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class ProjectListAnalysis extends ServerResource {

	// FIXME: This is pretty horrible, but I'm tired and want something working
	@Get("json")
	public String represent() {
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET"));
		
		try {
			Mongo mongo = Configuration.getInstance().getMongoConnection();
			
			DB db = mongo.getDB("scava");
			DBCollection col = db.getCollection("metricAnalysis");
			
			ObjectMapper mapper = new ObjectMapper();
			ArrayNode results = mapper.createArrayNode();
			
			DBCursor cursor = col.find();
			try {
			   while(cursor.hasNext()) {
			       results.add(cursor.next().toString());
			   }
			} finally {
			   cursor.close();
			}
			
			
			mongo.close();
			
			return results.toString();
			
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return null;
	}
}
