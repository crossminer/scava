/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.client.api.cache;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.ConcurrentNavigableMap;

import org.eclipse.scava.platform.Configuration;
import org.mapdb.DBMaker;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * Provides two caches for sparks:
 * 	1. Caches the image file
 * 	2. Caches the JSON (which references the first cache)
 * 
 * @author jimmy
 *
 */
public class SparkCache {
	
	private static SparkCache cache;
	public static SparkCache getSparkCache() {
		if (cache == null) {
			cache = new SparkCache();
		}
		return cache;
	}

	protected ConcurrentNavigableMap<String, byte[]> sparkMap;
	protected ConcurrentNavigableMap<String, String> dataMap;
	
	private SparkCache() {
		sparkMap = DBMaker.newTempTreeMap();
		dataMap = DBMaker.newTempTreeMap();
		
		// If we're running in a cluster, where the API is load balanced, we actually
		// need to store the spark bytes in a DB. In memory cache wouldn't work.
		Mongo mongo;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		}
		DB db = mongo.getDB("scava");
		DBCollection col = db.getCollection("sparks");
		col.ensureIndex("sparkid");
		col.ensureIndex(new BasicDBObject("created_at", 1), new BasicDBObject("expireAfterSeconds", 3600));
		mongo.close();
	}

	public synchronized byte[] getSpark(String sparkId) {
//		return sparkMap.get(sparkId);
		byte[] spark = null;
		Mongo mongo;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
		
		DB db = mongo.getDB("scava");
		DBCollection col = db.getCollection("sparks");
		
		DBObject obj = col.findOne(new BasicDBObject("sparkid", sparkId));
		if (obj != null) {
			spark = (byte[])obj.get("bytes");
		}
		mongo.close();
		return spark;
	}
	
	// FIXME: This still results in the spark image being created multiple times - 
	// because the spark resource is cached one machine, if it is requested on another, it
	// will regenerated the spark data AND the spark image. Make the key the URL (including
	// any params, e.g. data filter) and store the spark stats with the byte array
	public synchronized void putSpark(String sparkId, byte[] img) {
//		sparkMap.put(sparkId, img);
		Mongo mongo;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		}
		DB db = mongo.getDB("scava");
		DBCollection col = db.getCollection("sparks");
		
		BasicDBObject obj = new BasicDBObject();
		obj.put("sparkid", sparkId);
		obj.put("bytes", img);
		obj.put("created_at", new Date());
		
		col.insert(obj);
		
		mongo.close();
	}
	
	public synchronized String getSparkData(String query) {
		return dataMap.get(query);
	}
	
	public synchronized void putSparkData(String query, JsonNode obj) {
		dataMap.put(query, obj.toString());
	}
}
