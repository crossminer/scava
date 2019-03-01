package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Blob {

	public Blob() {
	}

	@JsonProperty("size")
	private Integer size;

	@JsonProperty("encoding")
	private String encoding;

	@JsonProperty("sha")
	private String sha;

	@JsonProperty("content")
	private String content;

	public Integer getSize() {
		return this.size;
	}

	public String getEncoding() {
		return this.encoding;
	}

	public String getSha() {
		return this.sha;
	}

	public String getContent() {
		return this.content;
	}

	@Override
	public String toString() {
		return "Blob [ " + "size = " + this.size + ", " + "encoding = " + this.encoding + ", " + "sha = " + this.sha
				+ ", " + "content = " + this.content + ", " + "]";
	}
}
