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

import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Projects extends AbstractApiResource {

	@Override
	public Representation doRepresent() {
		String view = (String) getRequest().getAttributes().get("view");
		
		DBCollection col = mongo.getDB("scava").getCollection("projects");
		
		BasicDBObject query = new BasicDBObject();
		switch (view) {
			case "error":
				query.put("executionInformation.inErrorState", true);
				break;
			case "analysed":
				query.put("executionInformation.analysed", true);
				break;
			default:
				break;
		}
		
		DBCursor cursor = col.find(query);

		ArrayNode res = mapper.createArrayNode();
		
		while (cursor.hasNext()) {
			DBObject project = cursor.next();
			ObjectNode node = mapper.createObjectNode();
			
			node.put("id", project.get("shortName").toString());
			node.put("name", project.get("name").toString());
			
			BasicDBObject ei = (BasicDBObject)project.get("executionInformation");
			
			node.put("lastExecuted", (String)ei.get("lastExecuted"));
			node.put("analysed", (Boolean)ei.get("analysed"));
			node.put("inErrorState", (Boolean)ei.get("inErrorState"));
			node.put("monitor", (Boolean)ei.get("monitor"));
			
			res.add(node);
		}
		
		return Util.createJsonRepresentation(res);
	}
}
