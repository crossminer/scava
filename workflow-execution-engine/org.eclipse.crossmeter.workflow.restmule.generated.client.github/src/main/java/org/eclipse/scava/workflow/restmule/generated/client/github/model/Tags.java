package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tags {

	public Tags() {
	}

	@JsonProperty("tag")
	private String tag;

	@JsonProperty("message")
	private String message;

	@JsonProperty("type")
	private String type;

	@JsonProperty("object")
	private String object;

	@JsonProperty("tagger")
	private Tagger tagger;

	public String getTag() {
		return this.tag;
	}

	public String getMessage() {
		return this.message;
	}

	public String getType() {
		return this.type;
	}

	public String getObject() {
		return this.object;
	}

	public Tagger getTagger() {
		return this.tagger;
	}

	@Override
	public String toString() {
		return "Tags [ " + "tag = " + this.tag + ", " + "message = " + this.message + ", " + "type = " + this.type
				+ ", " + "object = " + this.object + ", " + "tagger = " + this.tagger + ", " + "]";
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

}
