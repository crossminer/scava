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

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class MetricVisualisationExtensionPointManager {

	protected final String extensionPointId = "org.eclipse.scava.platform.visualisation.metric";
	protected Map<String, MetricVisualisation> visMap;
	
	protected static MetricVisualisationExtensionPointManager instance;
	
	private MetricVisualisationExtensionPointManager() {	}
	
	public static MetricVisualisationExtensionPointManager getInstance() {
		if (instance == null) {
			instance = new MetricVisualisationExtensionPointManager();
		}
		return instance;
	}
	
	public MetricVisualisation findVisualisationById(String id) {
		return visMap.get(id);
	}
	
	public Collection<String> getListOfVisualisationIds() {
		return visMap.keySet();
	}
	
	public synchronized Map<String, MetricVisualisation> getRegisteredVisualisations() {
		if (visMap == null) {
			visMap = new HashMap<>();
		} else {
			// FIXME, this will not allow any viss to be added at runtime
			return visMap;
		}
		
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(extensionPointId);

		if (extensionPoint != null) {
			for (IExtension element : extensionPoint.getExtensions()) {
				String name = element.getContributor().getName();
				Bundle bundle = Platform.getBundle(name);

				for (IConfigurationElement ice : element.getConfigurationElements()) {
					String path = ice.getAttribute("json");
					if (path!= null) {
						// TODO: More validation is needed here, as it's very susceptible
						// to error. Only load the chart if it passes validation.
						URL url = bundle.getResource(path);
						
						JsonNode json;
						try {
							json = loadJsonFile(url);
						} catch (Exception e) {
							e.printStackTrace(); // FIXME
							continue;
						}
						
						ArrayNode visses = (ArrayNode)json.get("vis");
						for (JsonNode vis : visses) {
							String chartType = vis.path("type").textValue();
							String visName = vis.path("id").textValue();
							
							// Legacy support
							if (visName == null && vis.path("nicename").textValue() != null) {
								visName = vis.path("nicename").textValue();
							}
							
							if (visMap.containsKey(visName)) {
								// FIXME: Use logger.
								System.err.println("Visualisation already defined for id '" + visName + "'. \nAttempted to add: " + vis + "\nPreviously registered: " + visMap.get(visName));
								continue;
							}
							
							Chart chart = ChartExtensionPointManager.getInstance().findChartByType(chartType);
							if (chart == null) {
								// FIXME: Use logger.
								System.err.println("Chart type '" + chartType + "' for visualisation '" + visName + "' not found in registry. ");
								continue;
							}
							visMap.put(visName, new MetricVisualisation(chart, json, vis));
						}
					}
				}
			}
		}
		return visMap;
	}
	
	protected JsonNode loadJsonFile(URL url) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(url);
	}
	
}
