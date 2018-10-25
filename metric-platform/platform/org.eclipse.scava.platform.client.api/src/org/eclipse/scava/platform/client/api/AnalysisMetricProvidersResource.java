package org.eclipse.scava.platform.client.api;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.data.model.MetricProvider;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mongodb.Mongo;

public class AnalysisMetricProvidersResource extends AbstractApiResource {

	@Override
	public Representation doRepresent() {
		Mongo mongo = null;
		Platform platform = null;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
			platform = new Platform(mongo);		
			org.eclipse.scava.platform.analysis.MetricProviderService service = platform.getAnalysisRepositoryManager().getMetricProviderService();
			
			List<MetricProvider> metricProviders = service.getMetricProviders();

			ArrayNode listMetricProviders = mapper.createArrayNode();
			for (MetricProvider metric : metricProviders) {
				try {
					metric.getDbObject().removeField("_id");
					metric.getDbObject().removeField("_type");
					metric.getDbObject().removeField("storages");
					
					List<Object> dependingMetrics = new ArrayList<Object>();
					for (MetricProvider mp : metric.getDependOf()) {
						Map<String, String> newMetric = new HashMap<>();
						newMetric.put("metricProviderId", mp.getDbObject().get("metricProviderId").toString());
						newMetric.put("label", mp.getDbObject().get("label").toString());
						dependingMetrics.add(newMetric);
					}
					metric.getDbObject().put("dependOf", dependingMetrics);
					listMetricProviders.add(mapper.readTree(metric.getDbObject().toString()));
				} catch (IOException e) {
					e.printStackTrace();
					StringRepresentation rep = new StringRepresentation("");
					rep.setMediaType(MediaType.APPLICATION_JSON);
					getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
					return rep;
				}
			}

			StringRepresentation rep = new StringRepresentation(listMetricProviders.toString());
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.SUCCESS_OK);

			return rep;

		} catch (UnknownHostException e) {
			e.printStackTrace();
			StringRepresentation rep = new StringRepresentation("");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return rep;
		} finally {
			if (mongo != null) mongo.close();
			platform = null;
		}
	}

}
