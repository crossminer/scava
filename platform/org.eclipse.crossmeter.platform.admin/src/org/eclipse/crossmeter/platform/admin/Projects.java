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
package org.eclipse.crossmeter.platform.admin;

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
		
		DBCollection col = mongo.getDB("crossmeter").getCollection("projects");
		
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
