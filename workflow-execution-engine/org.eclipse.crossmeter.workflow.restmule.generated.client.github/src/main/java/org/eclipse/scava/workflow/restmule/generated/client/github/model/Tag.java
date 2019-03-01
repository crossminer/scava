package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tag {

	public Tag() {
	}

	@JsonProperty("tag")
	private String tag;

	@JsonProperty("message")
	private String message;

	@JsonProperty("sha")
	private String sha;

	@JsonProperty("url")
	private String url;

	@JsonProperty("tagger")
	private Tagger tagger;

	@JsonProperty("object")
	private Object object;

	public String getTag() {
		return this.tag;
	}

	public String getMessage() {
		return this.message;
	}

	public String getSha() {
		return this.sha;
	}

	public String getUrl() {
		return this.url;
	}

	public Tagger getTagger() {
		return this.tagger;
	}

	public Object getObject() {
		return this.object;
	}

	@Override
	public String toString() {
		return "Tag [ " + "tag = " + this.tag + ", " + "message = " + this.message + ", " + "sha = " + this.sha + ", "
				+ "url = " + this.url + ", " + "tagger = " + this.tagger + ", " + "object = " + this.object + ", "
				+ "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Tagger {

		public Tagger() {
		}

		@JsonProperty("date")
		private String date;

		@JsonProperty("name")
		private String name;

		@JsonProperty("email")
		private String email;

		public String getDate() {
			return this.date;
		}

		public String getName() {
			return this.name;
		}

		public String getEmail() {
			return this.email;
		}

		@Override
		public String toString() {
			return "Tagger [ " + "date = " + this.date + ", " + "name = " + this.name + ", " + "email = " + this.email
					+ ", " + "]";
		}
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
