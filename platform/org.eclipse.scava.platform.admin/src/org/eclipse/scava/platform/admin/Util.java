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

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class Util {
	
	public static StringRepresentation createJsonRepresentation(JsonNode json) {
		return createJsonRepresentation(json.toString());
	}
	public static StringRepresentation createJsonRepresentation(String json) {
		StringRepresentation resp = new StringRepresentation(json);
		resp.setMediaType(MediaType.APPLICATION_JSON);
		return resp;
	}
	
	public static Representation generateErrorMessageRepresentation(JsonNode request, String message) {
		JsonNode msg = generateErrorMessage(request, message);
		StringRepresentation rep = new StringRepresentation(msg.toString());
		rep.setMediaType(MediaType.APPLICATION_JSON);
		return rep;
	}
	
	public static JsonNode generateErrorMessage(JsonNode request, String message) {
		ObjectMapper mapper = new ObjectMapper(); // TODO: Shall we have a single mapper for all?
		ObjectNode m = mapper.createObjectNode();
		
		m.put("request", request);
		m.put("status", "error");
		m.put("message", escape(message));
		
		return m;
	}
	
	public static String escape(String msg) {
		System.err.println(msg);
		msg.replaceAll("\"", "\\\"");
		return msg;
	}
}
