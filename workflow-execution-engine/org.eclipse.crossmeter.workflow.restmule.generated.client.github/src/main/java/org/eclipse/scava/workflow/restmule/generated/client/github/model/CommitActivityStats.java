package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitActivityStats {

	public CommitActivityStats() {
	}

	@JsonProperty("total")
	private Integer total;

	@JsonProperty("week")
	private Integer week;

	@JsonProperty("days")
	private List<Integer> days = new ArrayList<Integer>();

	public Integer getTotal() {
		return this.total;
	}

	public Integer getWeek() {
		return this.week;
	}

	public List<Integer> getDays() {
		return this.days;
	}

	@Override
	public String toString() {
		return "CommitActivityStats [ " + "total = " + this.total + ", " + "week = " + this.week + ", " + "days = "
				+ this.days + ", " + "]";
	}
}
