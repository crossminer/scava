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

import java.util.Random;

import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.platform.visualisation.Chart;
import org.eclipse.crossmeter.platform.visualisation.MetricVisualisation;
import org.junit.Test;
import org.eclipse.crossmeter.metricprovider.historic.commitsovertime.model.CommitsOverTime;
import org.eclipse.crossmeter.metricprovider.historic.commitsovertime.model.RepositoryData;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MetricVisualisationTest {
	
	final protected Mongo mongo;
	
	public MetricVisualisationTest() throws Exception {
		this.mongo = new Mongo();
	}

	@Test
	public void testVisualise() throws Exception {
		Chart chart = ChartUtil.loadChart("charts/linechart.json");
		JsonNode metricSpecification = ChartUtil.loadJsonFile("data/commitsovertime.json");
		JsonNode vis = metricSpecification.get("vis").get(0);
		
		MetricVisualisation mv = new MetricVisualisation(chart, metricSpecification, vis);
		
		DB db = mongo.getDB("Epsilon");
		
		System.out.println(mv.visualise(db));
	
	}

	@Test
	public void testSparky() throws Exception {
		Chart chart = ChartUtil.loadChart("charts/linechart.json");
		JsonNode metricSpecification = ChartUtil.loadJsonFile("data/commitsovertime.json");
		JsonNode vis = metricSpecification.get("vis").get(0);
		
		MetricVisualisation mv = new MetricVisualisation(chart, metricSpecification, vis);
		
		DB db = mongo.getDB("Xtext");
		DBCollection collection = db.getCollection(metricSpecification.path("metricid").textValue());
		
		Random random = new Random();
		int commits = 0;
		Date[] range = Date.range(new Date("20140101"), new Date("20140130"));
		
		for (Date d : range) {
			CommitsOverTime cot = new CommitsOverTime();
			RepositoryData rd = new RepositoryData();
			rd.setUrl("foo");
			rd.setNumberOfCommits(commits);
			commits = commits + (random.nextInt(10));
			cot.getRepositories().add(rd);
			
			DBObject dbObject = cot.getDbObject();
			
			dbObject.put("__date", d.toString());
			dbObject.put("__datetime", d.toJavaDate());
			collection.save(dbObject);
		}
		
		byte[] sparky = mv.getSparky(db, null);
		for (byte b : sparky) System.out.println(b);
		System.out.println(sparky);
		
	}
	
}
