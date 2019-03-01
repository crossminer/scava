package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {

	public Meta() {
	}

	@JsonProperty("git")
	private List<String> git = new ArrayList<String>();

	@JsonProperty("hooks")
	private List<String> hooks = new ArrayList<String>();

	public List<String> getGit() {
		return this.git;
	}

	public List<String> getHooks() {
		return this.hooks;
	}

	@Override
	public String toString() {
		return "Meta [ " + "git = " + this.git + ", " + "hooks = " + this.hooks + ", " + "]";
	}
}
