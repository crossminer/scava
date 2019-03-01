package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParticipationStats {

	public ParticipationStats() {
	}

	@JsonProperty("all")
	private List<Integer> all = new ArrayList<Integer>();

	@JsonProperty("owner")
	private List<Integer> owner = new ArrayList<Integer>();

	public List<Integer> getAll() {
		return this.all;
	}

	public List<Integer> getOwner() {
		return this.owner;
	}

	@Override
	public String toString() {
		return "ParticipationStats [ " + "all = " + this.all + ", " + "owner = " + this.owner + ", " + "]";
	}
}
