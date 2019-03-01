package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeadBranch {

	public HeadBranch() {
	}

	@JsonProperty("ref")
	private String ref;

	@JsonProperty("url")
	private String url;

	@JsonProperty("object")
	private Object object;

	public String getRef() {
		return this.ref;
	}

	public String getUrl() {
		return this.url;
	}

	public Object getObject() {
		return this.object;
	}

	@Override
	public String toString() {
		return "HeadBranch [ " + "ref = " + this.ref + ", " + "url = " + this.url + ", " + "object = " + this.object
				+ ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Object {

		public Object() {
		}

		@JsonProperty("type")
		private String type;

		@JsonProperty("sha")
		private String sha;

		@JsonProperty("url")
		private String url;

		public String getType() {
			return this.type;
		}

		public String getSha() {
			return this.sha;
		}

		public String getUrl() {
			return this.url;
		}

		@Override
		public String toString() {
			return "Object [ " + "type = " + this.type + ", " + "sha = " + this.sha + ", " + "url = " + this.url + ", "
					+ "]";
		}
	}

}
