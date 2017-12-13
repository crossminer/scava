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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChartExtensionPointManager {

	protected final String extensionPointId = "org.eclipse.crossmeter.platform.visualisation.type";
	protected Map<String, Chart> chartMap;
	
	protected static ChartExtensionPointManager instance;
	
	private ChartExtensionPointManager() {	}
	
	public static ChartExtensionPointManager getInstance() {
		if (instance == null) {
			instance = new ChartExtensionPointManager();
		}
		return instance;
	}
	
	public Chart findChartByType(String type) {
		if (chartMap == null) {
			getRegisteredCharts();
		}
		
		return chartMap.get(type);
	}
	
	public Map<String, Chart> getRegisteredCharts() {
		if (chartMap == null) {
			chartMap = new HashMap<>();
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
						chartMap.put(json.path("name").textValue(), new Chart(json));
					}
				}
			}
		}
		return chartMap;
	}
	
	protected JsonNode loadJsonFile(URL url) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(url);
	}
}
