package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Downloads {

	public Downloads() {
	}

	@JsonProperty("content_type")
	private String contentType;

	@JsonProperty("size")
	private Integer size;

	@JsonProperty("html_url")
	private String htmlUrl;

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("url")
	private String url;

	@JsonProperty("download_count")
	private Integer downloadCount;

	public String getContentType() {
		return this.contentType;
	}

	public Integer getSize() {
		return this.size;
	}

	public String getHtmlUrl() {
		return this.htmlUrl;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public Integer getId() {
		return this.id;
	}

	public String getUrl() {
		return this.url;
	}

	public Integer getDownloadCount() {
		return this.downloadCount;
	}

	@Override
	public String toString() {
		return "Downloads [ " + "contentType = " + this.contentType + ", " + "size = " + this.size + ", " + "htmlUrl = "
				+ this.htmlUrl + ", " + "name = " + this.name + ", " + "description = " + this.description + ", "
				+ "id = " + this.id + ", " + "url = " + this.url + ", " + "downloadCount = " + this.downloadCount + ", "
				+ "]";
	}
}
