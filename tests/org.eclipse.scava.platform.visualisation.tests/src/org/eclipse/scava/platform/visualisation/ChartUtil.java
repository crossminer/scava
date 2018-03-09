/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.visualisation;

import java.io.File;

import org.eclipse.scava.platform.visualisation.Chart;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class ChartUtil {
	
	public static Chart loadChart(String path) throws Exception {
		JsonNode node = loadJsonFile(path);
		System.out.println(node);
		return new Chart(node);
	}
	
	public static DBCollection getCollection(Mongo mongo, String dbName, String collectionName) {
		DB db = mongo.getDB(dbName);
		DBCollection collection = db.getCollection(collectionName);
		return collection;
	}
	
	public static JsonNode loadJsonFile(String path) throws Exception {
		File jsonFile = new File(ChartUtil.class.getResource(path).toURI());
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(jsonFile);
	}
}
