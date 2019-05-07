package org.eclipse.scava.business.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class APIMigrationResponse {

	private List<String> stackoverflowPosts = Lists.newArrayList();
	private Map<String, Float> apiFunctionCalls = new HashMap<String, Float>();
	private List<String> patterns = Lists.newArrayList();
	public List<String> getStackoverflowPosts() {
		return stackoverflowPosts;
	}
	public void setStackoverflowPosts(List<String> stackoverflowPosts) {
		this.stackoverflowPosts = stackoverflowPosts;
	}
	public Map<String, Float> getApiFunctionCalls() {
		return apiFunctionCalls;
	}
	public void setApiFunctionCalls(Map<String, Float> apiFunctionCalls) {
		this.apiFunctionCalls = apiFunctionCalls;
	}
	public List<String> getPatterns() {
		return patterns;
	}
	public void setPatterns(List<String> patterns) {
		this.patterns = patterns;
	}
	
}
