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
package org.eclipse.crossmeter.platform.visualisation;

import java.util.Calendar;

import org.eclipse.crossmeter.metricprovider.rascal.trans.model.IntegerMeasurement;
import org.eclipse.crossmeter.metricprovider.rascal.trans.model.ListMeasurement;
import org.eclipse.crossmeter.metricprovider.rascal.trans.model.StringMeasurement;
import org.eclipse.crossmeter.metricprovider.rascal.trans.model.TupleMeasurement;
import org.eclipse.crossmeter.platform.visualisation.Chart;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class ChartTest {
	
	Mongo mongo;
	public ChartTest() throws Exception {
		mongo = new Mongo();
	}
	@Test
	public void testChartCreation() throws Exception {
		ChartUtil.loadChart("charts/linechart.json");
	}
	
 	@Test
	public void testDatatableWithoutRowDefinition() throws Exception {
		DBCollection collection = ChartUtil.getCollection(mongo, "Epsilon","org.eclipse.crossmeter.metricprovider.historic.commitsovertime.CommitsOverTimeHistoricMetricProvider");
		JsonNode node = ChartUtil.loadJsonFile("data/commitsovertime.json");
		
		ArrayNode vis = (ArrayNode) node.get("vis");
		JsonNode datatable = vis.get(0).get("datatable");
		
		Chart chart = ChartUtil.loadChart("charts/linechart.json");
		ArrayNode table = chart.createDatatable(datatable, collection, null);
		System.out.println(table);
	}
 	
 	@Test
	public void testRascalDatatable() throws Exception {
 		
 		
 		// Create test data
 		DB db = mongo.getDB("rascalloc");
 		DBCollection collection = db.getCollection("locperlanguage");

 		Calendar cal = Calendar.getInstance();
 		for (int i = 0; i < 10; i++) {
 			
 			ListMeasurement list = new ListMeasurement();
 			
 			for (String lang : new String[]{"HTML", "Java", "PHP"}) {
 				StringMeasurement l = new StringMeasurement();
 				l.setValue(lang);
 				
 				IntegerMeasurement loc = new IntegerMeasurement();
 				loc.setValue(3);
 				
 				TupleMeasurement tuple = new TupleMeasurement();
 				tuple.getValue().add(l);
 				tuple.getValue().add(loc);
 				
 				list.getValue().add(tuple);
 			}
 			
 			DBObject obj = list.getDbObject();
 			obj.put("__date", new org.eclipse.crossmeter.platform.Date(cal.getTime()).toString());
 			obj.put("__datetime", cal.getTime());
 			collection.save(obj);
 			cal.add(Calendar.DATE, 1);
 		}
 		
		JsonNode node = ChartUtil.loadJsonFile("data/rascalloc.json");
		
		ArrayNode vis = (ArrayNode) node.get("vis");
		JsonNode datatable = vis.get(0).get("datatable");
		
		Chart chart = ChartUtil.loadChart("charts/linechart.json");
		ArrayNode table = chart.createDatatable(datatable, collection, null);
		System.out.println(table);
		
		// Tidy up
		mongo.dropDatabase("rascalloc");
	}
	
	@Test
	public void testDatatableWithRowDefinition() throws Exception {
		DBCollection collection = ChartUtil.getCollection(mongo, "Epsilon", "org.eclipse.crossmeter.metricprovider.historic.avgnumberofrequestsreplies");
		JsonNode node = ChartUtil.loadJsonFile("data/articlesrequestsreplies.json");
		
		ArrayNode vis = (ArrayNode) node.get("vis");
		JsonNode datatable = vis.get(0).get("datatable");
		
		Chart chart = ChartUtil.loadChart("charts/linechart.json");
		ArrayNode table = chart.createDatatable(datatable, collection, null);
		System.out.println(table);
	}
}
