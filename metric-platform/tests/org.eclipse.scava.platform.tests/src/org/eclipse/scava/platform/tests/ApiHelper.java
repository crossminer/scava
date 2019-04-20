package org.eclipse.scava.platform.tests;

import java.io.IOException;
import java.util.List;

import org.eclipse.scava.platform.analysis.data.model.dto.MetricProviderDTO;
import org.eclipse.scava.platform.analysis.data.types.AnalysisExecutionMode;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ApiHelper {
	Client client;

	public ApiHelper() {
		client = new Client(Protocol.HTTP);
	}

	public Response setProperty(String key, String value) {
		Request request = new Request(Method.POST, "http://localhost:8182/platform/properties/create");

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode n = mapper.createObjectNode();
		n.put("key", key);
		n.put("value", value);

		request.setEntity(n.toString(), MediaType.APPLICATION_JSON);

		return client.handle(request);
	}

	public Response importProject(String url) {
		Request request = new Request(Method.POST, "http://localhost:8182/projects/import");

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode n = mapper.createObjectNode();
		n.put("url", url);

		request.setEntity(n.toString(), MediaType.APPLICATION_JSON);

		return client.handle(request);
	}

	public Response createTask(String projectId, String taskId, String label, AnalysisExecutionMode mode,
			List<String> metrics, String start, String end) {
		Request request = new Request(Method.POST, "http://localhost:8182/analysis/task/create");

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode n = mapper.createObjectNode();
		n.put("analysisTaskId", taskId);
		n.put("label", label);
		n.put("type", mode.name());
		n.put("startDate", start);
		n.put("endDate", end);
		n.put("projectId", projectId);
		ArrayNode arr = n.putArray("metricProviders");
		for (String m : metrics)
			arr.add(m);

		request.setEntity(n.toString(), MediaType.APPLICATION_JSON);

		return client.handle(request);
	}

	public Response startTask(String taskId) {
		Request request = new Request(Method.POST, "http://localhost:8182/analysis/task/start");

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode n = mapper.createObjectNode();
		n.put("analysisTaskId", taskId);

		request.setEntity(n.toString(), MediaType.APPLICATION_JSON);

		return client.handle(request);
	}

	public List<MetricProviderDTO> getMetricProviders() {
		Request request = new Request(Method.GET, "http://localhost:8182/analysis/metricproviders");
		Response res = client.handle(request);

		String json = res.getEntityAsText();

		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, new TypeReference<List<MetricProviderDTO>>() {
			});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
