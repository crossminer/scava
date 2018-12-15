package org.eclipse.epsilon.emc.json;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonUtil {

	public static void collectChildElements(Object root, ArrayList<Object> elements) {
		if (root instanceof JSONElement){
			Object element = ((JSONElement) root).getValue();
			Iterator<?> iterator;
			if (element instanceof JSONObject) {
				elements.add((JSONElement) root);
				iterator = ((JSONObject) element).keySet().iterator();
				while (iterator.hasNext()){
					String key = (String) iterator.next();
					Object child = ((JSONObject)element).get(key);
					if (child!=null && child instanceof JSONObject){
						JSONElement childJsonElement = new JSONElement((JSONElement) root, key, child);
						collectChildElements(childJsonElement, elements);
					}
				}
			} else if (element instanceof JSONArray){
				iterator = ((JSONArray) element).iterator();
				while (iterator.hasNext()){
					Object child = iterator.next();
					if (child != null && child instanceof JSONObject){
						JSONElement childJsonElement = new JSONElement((JSONElement) root, child);
						collectChildElements(childJsonElement, elements);
					}
				}			
			}
		}
	}
	
	public static boolean tagMatches(JSONElement element, String name) {
		if (element.getTag().equalsIgnoreCase(name)) {
			return true;
		}
		else {
			int colonIndex = element.getTag().indexOf(":");
			if (colonIndex >= 0) {
				return element.getTag().substring(colonIndex + 1).equalsIgnoreCase(name);
			}
			else {
				return false;
			}
		}
	}
}
