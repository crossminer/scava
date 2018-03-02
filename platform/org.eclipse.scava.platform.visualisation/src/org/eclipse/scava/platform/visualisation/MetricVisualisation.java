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

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import sparkle.Sparkle;
import sparkle.dimensions.DateDimension;
import sparkle.dimensions.LinearDimension;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class MetricVisualisation {
	
	/*
	 * The associated chart.
	 */
	protected final Chart chart;
	/*
	 * The global definition of the visualisation for the metric
	 */
	protected final JsonNode metricSpecification;
	/* 
	 * The specific visualisation for the metric.
	 */
	protected final JsonNode vis;
	
	protected final String metricId;
	
	/*
	 * This stores the high/low etc values of the spark
	 */
	protected ObjectNode sparkData;
	
	public String getMetricId() {
		return metricId;
	}
	
	public MetricVisualisation(Chart chart, JsonNode metricSpecification, JsonNode vis) {
		this.chart = chart;
		this.metricSpecification = metricSpecification;
		this.vis = vis;
		this.metricId = metricSpecification.path("metricid").textValue();
	}
	
	public JsonNode getVis() {
		return vis;
	}
	
	public JsonNode visualise(DB db) {
		return visualise(db, new BasicDBObject());
		
	}
	
	public JsonNode visualise(DB db, BasicDBObject query) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode visualisation = mapper.createObjectNode();
		
		DBCollection collection = getCollection(db);
		ArrayNode datatable = chart.createDatatable(vis.get("datatable"), collection, query);
		
		visualisation.put("id", vis.path("id").textValue());
		visualisation.put("name", vis.path("name").textValue());
		visualisation.put("description", vis.path("description").textValue());
		visualisation.put("type", vis.path("type").textValue());
		visualisation.put("datatable", datatable);
		visualisation.put("timeSeries", vis.path("timeSeries").asBoolean());
		visualisation.put("ordinal", vis.path("ordinal").asBoolean());
		
		chart.completeFields(visualisation, vis);
		
		return visualisation;
	}
	
	public byte[] getSparky(DB db, BasicDBObject query) throws IOException, ParseException, UnsparkableVisualisationException {
		
		if (vis.get("timeSeries") == null || !vis.get("timeSeries").asBoolean()) {
			throw new UnsparkableVisualisationException();
		}
		
		if (vis.get("series") != null) {
			throw new UnsparkableVisualisationException();
		}
		
		if (vis.get("y").getNodeType().equals(JsonNodeType.ARRAY)) {
			throw new UnsparkableVisualisationException();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode visualisation = mapper.createObjectNode();
		chart.completeFields(visualisation, vis);
		
		String xColName = vis.get("x").asText();
		String yColName = vis.get("y").asText();
		yColName = yColName.replace("\"", "");
		
		DBCollection collection = getCollection(db); 
		
		ArrayNode datatable = chart.createDatatable(vis.get("datatable"), collection, query);
		Iterator<JsonNode> it = datatable.iterator();
		
		List<Date> xdata = new ArrayList<>();
		List<Double> ydata = new ArrayList<>(); // FIXME: This is hardcoded to Doubles
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		
		while (it.hasNext()) {
			JsonNode obj = it.next();
			
			Date x = format.parse(obj.get(xColName).asText());
			xdata.add(x);

			Double y = obj.path(yColName).asDouble();
			ydata.add(y);
		}
		
		if (xdata.isEmpty()) {
			sparkData = mapper.createObjectNode();
			sparkData.put("id", vis.path("id").textValue());
			sparkData.put("name", vis.path("name").textValue());
			sparkData.put("description", vis.path("description").textValue());
			sparkData.put("status", "error");
			sparkData.put("msg", "No data for metric.");
			return null;
		}
			
		// Spark config
		int height = 60;
		int width = 300;
		int padding = 12;
		
		// FIXME: Actually, for SCAVA sparklines, they HAVE to have 
		// Date as the X-axis! 
		DateDimension xdim = new DateDimension(xdata, width-padding, padding);
		LinearDimension ydim = new LinearDimension(ydata, height-padding, padding);
		
		Sparkle sparkle = new Sparkle(height, width, padding);
		sparkle.setHeadless(true);

		byte[] bytes = sparkle.renderToByteArray(xdim, ydim);
		
		DateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yy");
		
		// Set the spark data
		sparkData = mapper.createObjectNode();
		sparkData.put("status", "ok");
		sparkData.put("id", vis.path("id").textValue());
		sparkData.put("name", vis.path("name").textValue());
		sparkData.put("description", vis.path("description").textValue());
		sparkData.put("low", ydim.getMinValue());
		sparkData.put("high", ydim.getMaxValue());
		sparkData.put("first", ydata.get(0));
		sparkData.put("last", ydata.get(ydata.size()-1));
		sparkData.put("firstDate", outputDateFormat.format(xdata.get(0)));
		sparkData.put("lastDate", outputDateFormat.format(xdata.get(xdata.size()-1)));
		sparkData.put("months", (int)((xdata.get(xdata.size()-1).getTime() - xdata.get(0).getTime())/(365.24 * 24 * 60 * 60 * 1000 / 12)));
		
		return bytes;
	}

	private DBCollection getCollection(DB db) {
		// TODO: Look up metric provider and get collection name from there.
		// 		 If we're visualising transients, this may need amending 
		
		String id = metricId.replace("org.eclipse.scava.metricprovider.", "");
		if (!db.collectionExists(id)) {
			System.err.println("ERROR: Could not find collection: " + metricId);
		}
		
		DBCollection collection = db.getCollection(id);
		return collection;
	}
	
	public ObjectNode getSparkData() {
		return sparkData;	
	}
}
