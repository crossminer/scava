package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Branches {

	public Branches() {
	}

	@JsonProperty("name")
	private String name;

	@JsonProperty("commit")
	private Commit commit;

	public String getName() {
		return this.name;
	}

	public Commit getCommit() {
		return this.commit;
	}

	@Override
	public String toString() {
		return "Branches [ " + "name = " + this.name + ", " + "commit = " + this.commit + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Commit {

		public Commit() {
		}

		@JsonProperty("sha")
		private String sha;

		@JsonProperty("url")
		private String url;

		public String getSha() {
			return this.sha;
		}

		public String getUrl() {
			return this.url;
		}

		@Override
		public String toString() {
			return "Commit [ " + "sha = " + this.sha + ", " + "url = " + this.url + ", " + "]";
		}
	}

}
