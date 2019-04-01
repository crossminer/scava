/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.client.api;

import java.io.IOException;
import java.util.List;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.MetricProviderService;
import org.eclipse.scava.platform.analysis.data.model.dto.MetricProviderDTO;
import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.Mongo;

public class AnalysisMetricProvidersResource extends AbstractApiResource {

	@Override
	public Representation doRepresent() {
		Mongo mongo = null;
		Platform platform = null;
		ArrayNode listMetricProviders = mapper.createArrayNode();
		try {
			mongo = Configuration.getInstance().getMongoConnection();
			platform = new Platform(mongo);
			MetricProviderService service = platform.getAnalysisRepositoryManager().getMetricProviderService();

			List<MetricProviderDTO> metricProviders = service.getMetricProviders(platform);
			for (MetricProviderDTO metric : metricProviders) {
				ObjectNode newMetric = mapper.createObjectNode();
				ArrayNode dependOf = mapper.createArrayNode();
				for (MetricProviderDTO mp : metric.getDependOf()) {
					if (mp != null) {
						ObjectNode dependency = mapper.createObjectNode();
						dependency.put("metricProviderId", mp.getMetricProviderId());
						dependency.put("label", mp.getLabel());
						dependOf.add(dependency);
					}
				}
				newMetric.put("dependOf", dependOf);
				newMetric.put("metricProviderId", metric.getMetricProviderId());
				newMetric.put("label", metric.getLabel());
				newMetric.put("kind", metric.getKind());
				newMetric.put("description", metric.getDescription());
				listMetricProviders.add(newMetric);
			}

			return Util.createJsonRepresentation(listMetricProviders);

		} catch (IOException e) {
			return Util.generateErrorMessageRepresentation(listMetricProviders, e.getMessage());
		} finally {
			if (mongo != null)
				mongo.close();
			platform = null;
		}
	}

}
