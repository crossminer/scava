package org.eclipse.scava.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.eclipse.scava.platform.factoids.*;
import org.eclipse.scava.repository.model.*;
import org.eclipse.scava.repository.model.bts.bugzilla.*;
import org.eclipse.scava.repository.model.cc.forum.*;
import org.eclipse.scava.repository.model.cc.nntp.*;
import org.eclipse.scava.repository.model.cc.wiki.*;
import org.eclipse.scava.repository.model.eclipse.*;
import org.eclipse.scava.repository.model.github.*;
import org.eclipse.scava.repository.model.googlecode.*;
import org.eclipse.scava.repository.model.metrics.*;
import org.eclipse.scava.repository.model.redmine.*;
import org.eclipse.scava.repository.model.sourceforge.*;
import org.eclipse.scava.repository.model.vcs.cvs.*;
import org.eclipse.scava.repository.model.vcs.git.*;
import org.eclipse.scava.repository.model.vcs.svn.*;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class OssmeterClient {
	final String connectionUrl;
	final ObjectMapper mapper;
	
	public OssmeterClient(String connectionUrl) throws Exception {
		this.connectionUrl = connectionUrl;
		mapper = new ObjectMapper();
		
		// Test connection
		URL url = new URL(connectionUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() != 200) {
			conn.disconnect();
			throw new Exception("Unable to connect to OSSMETER REST API. Check URL.");
		}
		conn.disconnect();
	}
	public List<Factoid> getFactoidList(int size) throws Exception {
		String result = doGetRequest(connectionUrl + "/factoids?size="+size+"&");
		return mapper.readValue(result, TypeFactory.defaultInstance().constructCollectionType(List.class, Factoid.class));
	}
	public List<Factoid> getFactoidList(int page, int size) throws Exception {
		String result = doGetRequest(connectionUrl + "/factoids?page="+page+"&size="+size+"&");
		return mapper.readValue(result, TypeFactory.defaultInstance().constructCollectionType(List.class, Factoid.class));
	}
	public List<Metric> getMetricList(int size) throws Exception {
		String result = doGetRequest(connectionUrl + "/metrics?size="+size+"&");
		return mapper.readValue(result, TypeFactory.defaultInstance().constructCollectionType(List.class, Metric.class));
	}
	public List<Metric> getMetricList(int page, int size) throws Exception {
		String result = doGetRequest(connectionUrl + "/metrics?page="+page+"&size="+size+"&");
		return mapper.readValue(result, TypeFactory.defaultInstance().constructCollectionType(List.class, Metric.class));
	}
	public List<Project> getProjectList(int size) throws Exception {
		String result = doGetRequest(connectionUrl + "/projects?size="+size+"&");
		return mapper.readValue(result, TypeFactory.defaultInstance().constructCollectionType(List.class, Project.class));
	}
	public List<Project> getProjectList(int page, int size) throws Exception {
		String result = doGetRequest(connectionUrl + "/projects?page="+page+"&size="+size+"&");
		return mapper.readValue(result, TypeFactory.defaultInstance().constructCollectionType(List.class, Project.class));
	}
	public Project getProject(String projectId) throws Exception {
		String result = doGetRequest(connectionUrl + "/projects/p/" + projectId + "?");
		return mapper.readValue(result, Project.class);
	}
	public Metric getMetric(String projectId, String metricId) throws Exception {
		String result = doGetRequest(connectionUrl + "/projects/p/" + projectId + "/m/" + metricId + "?");
		return mapper.readValue(result, Metric.class);
	}
	public Metric getMetric(String projectId, String metricId, String agg, String startDate, String endDate) throws Exception {
		String result = doGetRequest(connectionUrl + "/projects/p/" + projectId + "/m/" + metricId + "?agg="+agg+"&startDate="+startDate+"&endDate="+endDate+"&");
		return mapper.readValue(result, Metric.class);
	}
	public Factoid getFactoid(String projectId, String factoidId) throws Exception {
		String result = doGetRequest(connectionUrl + "/projects/p/" + projectId + "/f/" + factoidId + "?");
		return mapper.readValue(result, Factoid.class);
	}
	
	public Project postImportProject(String projectUrl) throws Exception {
		ObjectNode n = mapper.createObjectNode();
		n.put("url", projectUrl);
		JsonNode result = doPostRequest(connectionUrl + "/projects/import", n);
		
		return mapper.readValue(result.toString(), Project.class);
	}
	
	public String doGetRequest(String urlString) throws Exception {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
		conn.connect();
		
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		StringBuffer sb = new StringBuffer();
		
		String output;
		while ((output = br.readLine()) != null) {
			sb.append(output);
		}
		conn.disconnect();
		
		return sb.toString();
	}
	
	public JsonNode doPostRequest(String urlString, JsonNode json) throws Exception {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		
		conn.setDoInput(true);
		conn.setDoOutput(true);
		
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(json.toString());
		wr.flush();
		wr.close();
		
		conn.connect();
		
		if (conn.getResponseCode() != 201) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		StringBuffer sb = new StringBuffer();
		
		String output;
		while ((output = br.readLine()) != null) {
			sb.append(output);
		}
		conn.disconnect();
		
		return mapper.readTree(sb.toString());
	}
	
	/**
	 *	This is a test method to ensure that the generated client works ok.
	 */
	public static void main(String[] args) throws Exception{
		OssmeterClient c = new OssmeterClient("http://localhost:8182");
		
		System.out.println(c.postImportProject("https://projects.eclipse.org/projects/modeling.epsilon"));
		//System.out.println(c.getProjectList("0", "5"));
		
		Project p = c.getProject("modeling.emf");
		//Metric m2 = c.getMetric("ant", "avgnumberofreplies");
		
	}
}
