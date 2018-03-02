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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class Chart {
	
	protected String chartName;
	protected List<String> requiredFields;
	protected List<String> optionalFields;
	
	// TODO: Validate the specification. 
	public Chart(JsonNode chartDefinition) {
		requiredFields = new ArrayList<>();
		optionalFields = new ArrayList<>();
		
		chartName = chartDefinition.path("name").textValue();
		
		ArrayNode fields = (ArrayNode) chartDefinition.get("fields");
		for (JsonNode f : fields) {
			String name = f.get("name").textValue();
			// TODO: the field type, for type checking the data
			boolean required = false; 
			if (f.has("required")){
				required = f.path("required").asBoolean(true);
			}
			if (required) {
				this.requiredFields.add(name);
			} else {
				this.optionalFields.add(name);
			}
		}
	}
	/*
{
	"metricid" : "akjsjadgi",
	"description" : "",
	"type" : "line",
	"datatable" : [],
	"x" : "Date",
	"y" : "Commits"

}
	 */

	protected ArrayNode createDatatable(JsonNode datatableSpec, DBCollection collection, DBObject query) {
		String rowName = null;
		if (datatableSpec.has("rows")) {
			// TODO: May need more checking here if rows can be more complex
			rowName = datatableSpec.path("rows").textValue();
			rowName = rowName.replace("$", ""); 
		}
		ArrayNode colNames = (ArrayNode) datatableSpec.path("cols");
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode results = mapper.createArrayNode();
		
		// Ensure data is sorted correctly
		DBObject orderBy = new BasicDBObject("__datetime", 1);
		
		if (rowName != null){
			Iterator<DBObject> it = collection.find(query).sort(orderBy).iterator();
			while(it.hasNext()) {
				DBObject dbobj = it.next();
				BasicDBList rows = (BasicDBList)dbobj.get(rowName);
				
				Iterator<Object> rowsIt = rows.iterator();
				while (rowsIt.hasNext()) {
					BasicDBObject row = (BasicDBObject) rowsIt.next();
					ObjectNode r = mapper.createObjectNode();
					
					boolean validRow = true;

					for (int i = 0; i < colNames.size(); i++) {
						JsonNode col = colNames.get(i);
						String name  = col.get("name").asText();
						String field = col.get("field").asText();
						
						field = field.replace("$", "");
						Object value = null;
						if (field.equals("__date")) {
							value = dbobj.get(field);
						} else {
							// U.G.L.Y. FIXME
							if (field.contains("[")) {
								String[] _ = field.split("\\[");
								String[] __ = _[1].split("\\]");
								int _index = Integer.valueOf(__[0]);
								String _field = __[1].replace(".","");
								BasicDBList _row = (BasicDBList)row.get(_[0]);
								
								BasicDBObject _entry = (BasicDBObject) _row.get(_index);
								value = _entry.get(_field);
							} else {
								value = row.get(field);
							}
						}
						
						// Fix invalid data: 
						// 	- If value is null, then we ignore row.
						//	- If value is NaN or Infinity, then we set to 0.
						if (value == null) {
							validRow = false;
							break;
						}
						if (value.toString().equals("NaN")) {
							value = 0;
						} else if (value.toString().equals("Infinity")) {
							value = -1;
						}
						
						r.put(name, mapper.valueToTree(value));
					}
					if (validRow) results.add(r);
				}
			}
		} else {
			Iterator<DBObject> it = collection.find(query).sort(orderBy).iterator();
			while(it.hasNext()) {
				DBObject dbobj = it.next();
				ObjectNode r = mapper.createObjectNode();
				
				boolean validRow = true;
				
				for (int i = 0; i < colNames.size(); i++) {
					JsonNode col = colNames.get(i);
					String name  = col.get("name").asText();
					String field = col.get("field").asText();
					
					field = field.replace("$", "");
					Object value = null;
					if (field.equals("__date")) {
						value = dbobj.get(field);
					} else {
						// U.G.L.Y. FIXME
						if (field.contains("[")) {
							String[] _ = field.split("\\[");
							String[] __ = _[1].split("\\]");
							int _index = Integer.valueOf(__[0]);
							String _field = __[1].replace(".","");
							BasicDBList _row = (BasicDBList)dbobj.get(_[0]);
							
							BasicDBObject _entry = (BasicDBObject) _row.get(_index);
							value = _entry.get(_field);
						} else {
							value = dbobj.get(field);
							
//							if (value.toString().equals("NaN")) {
//								System.out.println(value);
//							}
						}
					}
//					Object value = null;
//					value = dbobj.get(field);
//					mapper.valueToTree(value);
					
					
					// Fix invalid data: 
					// 	- If value is null, then we ignore row.
					//	- If value is NaN, then we set to 0.
					if (value == null) {
						validRow = false;
						break;
					}
					if (value.toString().equals("NaN")) {
						value = 0;
					} else if (value.toString().equals("Infinity")) {
						value = -1;
					}
					
					r.put(name, mapper.valueToTree(value));
				}
				if (validRow) results.add(r);
			}
		}
		return results;
	}

	public void completeFields(ObjectNode visualisation, JsonNode vis) {
		for (String field : requiredFields) {
				visualisation.put(field, vis.path(field));
		}
		for (String field : optionalFields) {
			if (vis.has(field)) {
				visualisation.put(field, vis.path(field));
			}
		}
	}
}
