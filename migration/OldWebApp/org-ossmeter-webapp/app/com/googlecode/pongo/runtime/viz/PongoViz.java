package com.googlecode.pongo.runtime.viz;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public abstract class PongoViz {
	
	protected DBCollection collection;
	
	public PongoViz() {  }
	
	abstract public void setProjectDB(DB db);
	
	abstract public String getViz(String type);
	
	protected String lastValue = "";
	
	public String getLastValue() {
		return lastValue;
	}
	
	/**
	 * Short term hack to filter different size data sets.
	 * @author jimmy
	 *
	 */
	public enum DateFilter {DAY, MONTH, YEAR, NONE};
	
	/**
	 * Also sets lastValue.
	 * @param seriesKind
	 * @param seriesLabel
	 * @param xAxis
	 * @param yAxis
	 * @param xtext
	 * @param ytext
	 * @return
	 */
	protected String createD3DataTable(String seriesKind, String seriesLabel, String xAxis, String yAxis, String xtext, String ytext, DateFilter filter) {
		Iterator<DBObject> it = collection.find().iterator();
		
		String table = "[";
		while (it.hasNext()) {
			DBObject dbobj = it.next();
			String xval = String.valueOf(dbobj.get(xAxis));
			
			// FIXME HARDCODED for dates - one a month
			if (
					(xAxis.equals("__date") && filter.equals(DateFilter.MONTH) && xval.trim().endsWith("01")) ||
					(xAxis.equals("__date") && filter.equals(DateFilter.DAY))
				) {
				BasicDBList rd = (BasicDBList) dbobj.get(seriesKind); 
				if (rd.size() == 0) continue; // Stops empty fields being added
				
				Iterator<Object> seriesIt = rd.iterator();
				while (seriesIt.hasNext()) {
					BasicDBObject bdbo = (BasicDBObject)seriesIt.next();
					String yval = String.valueOf(bdbo.get(yAxis));
					String seriesName = String.valueOf(bdbo.get(seriesLabel));
					String row = "{'"+xtext+"': '"+xval+"', '"+ytext+"': "+yval+", '"+seriesLabel+"' : '"+seriesName+"'}"; // TODO Add series
					table += row;
					if (seriesIt.hasNext()) table+=",";
					
					//
					lastValue = yval;
				}
				
				if (it.hasNext()) table+=",";
			} else if (!xAxis.equals("__date")) {
				String yval = String.valueOf(dbobj.get(yAxis));;
				String row = "{'"+xtext+"': '"+xval+"', '"+ytext+"': "+yval+"}"; // TODO Add series
				table += row;
				if (it.hasNext()) table+=",";
			}
		}
		if (table.endsWith(",")) table = table.substring(0, table.lastIndexOf(","));
		table+="]";
		return table;
	}
	
	protected String createCSVDataTable(String seriesKind, String seriesLabel, String xAxis, String yAxis, String xtext, String ytext, DateFilter filter) {
		Iterator<DBObject> it = collection.find().iterator();
		
		String table = xtext + "," + ytext + "," + seriesLabel + "\n";
		
		while (it.hasNext()) {
			DBObject dbobj = it.next();
			String xval = String.valueOf(dbobj.get(xAxis));
			if (
					(xAxis.equals("__date") && filter.equals(DateFilter.MONTH) && xval.trim().endsWith("01")) ||
					(xAxis.equals("__date") && filter.equals(DateFilter.DAY))
				) {
				BasicDBList rd = (BasicDBList) dbobj.get(seriesKind); 
				if (rd.size() == 0) continue; // Stops empty fields being added
				
				Iterator<Object> seriesIt = rd.iterator();
				while (seriesIt.hasNext()) {
					BasicDBObject bdbo = (BasicDBObject)seriesIt.next();
					String yval = String.valueOf(bdbo.get(yAxis));
					String seriesName = String.valueOf(bdbo.get(seriesLabel));
					String row = xval+","+yval+","+seriesName+"\n"; 
					table += row;
					
					lastValue = yval;
				}
				
			} else if (!xAxis.equals("__date")) {
				throw new RuntimeException("Not implemented.");
			}
		}
		return table;
	}
	
	/**
	 * @param seriesKind
	 * @param seriesLabel
	 * @param xaxis
	 * @param yaxis
	 * @return
	 */
	protected String createDataTable(String seriesKind, String seriesLabel, String xaxis, String yaxis) {
		
		Iterator<DBObject> it = collection.find().iterator();
		
		List<List<Object>> dataTable = new ArrayList<List<Object>>();
		List<Object> headerRow = new ArrayList<Object>();
		dataTable.add(headerRow);
		
		while (it.hasNext()) {
			List<Object> row = new ArrayList<Object>();
			dataTable.add(row);
			
			// x-axis
			if (!headerRow.contains("\"Date\"")) headerRow.add("\"Date\"");
			DBObject dbobj = it.next();
			
			String x = (String)dbobj.get(xaxis);
			row.add("\"" + x + "\"");
			BasicDBList rd = (BasicDBList) dbobj.get(seriesKind);
			
			for (BasicDBObject bdbo : rd.toArray(new BasicDBObject[0])) {
				Object srs;
				if (seriesLabel.equals("")) {
					srs = "Series " + rd.indexOf(bdbo); 
				} else {
					srs = bdbo.get(seriesLabel);
				}
				String hdrName = "\"" + srs.toString() + "\"";
				if (!headerRow.contains(hdrName)) headerRow.add(hdrName);
				row.add(bdbo.get(yaxis));
			}
		}
		return dataTable.toString(); //FIXME: properly format this
	}
}
