package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentsPath {

	public ContentsPath() {
	}

	@JsonProperty("path")
	private String path;

	@JsonProperty("size")
	private Integer size;

	@JsonProperty("html_url")
	private String htmlUrl;

	@JsonProperty("name")
	private String name;

	@JsonProperty("encoding")
	private String encoding;

	@JsonProperty("git_url")
	private String gitUrl;

	@JsonProperty("type")
	private String type;

	@JsonProperty("sha")
	private String sha;

	@JsonProperty("content")
	private String content;

	@JsonProperty("url")
	private String url;

	@JsonProperty("_links")
	private Links links;

	public String getPath() {
		return this.path;
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

	public String getEncoding() {
		return this.encoding;
	}

	public String getGitUrl() {
		return this.gitUrl;
	}

	public String getType() {
		return this.type;
	}

	public String getSha() {
		return this.sha;
	}

	public String getContent() {
		return this.content;
	}

	public String getUrl() {
		return this.url;
	}

	public Links getLinks() {
		return this.links;
	}

	@Override
	public String toString() {
		return "ContentsPath [ " + "path = " + this.path + ", " + "size = " + this.size + ", " + "htmlUrl = "
				+ this.htmlUrl + ", " + "name = " + this.name + ", " + "encoding = " + this.encoding + ", "
				+ "gitUrl = " + this.gitUrl + ", " + "type = " + this.type + ", " + "sha = " + this.sha + ", "
				+ "content = " + this.content + ", " + "url = " + this.url + ", " + "links = " + this.links + ", "
				+ "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Links {

		public Links() {
		}

		@JsonProperty("git")
		private String git;

		@JsonProperty("self")
		private String self;

		@JsonProperty("html")
		private String html;

		public String getGit() {
			return this.git;
		}

		public String getSelf() {
			return this.self;
		}

		public String getHtml() {
			return this.html;
		}

		@Override
		public String toString() {
			return "Links [ " + "git = " + this.git + ", " + "self = " + this.self + ", " + "html = " + this.html + ", "
					+ "]";
		}
	}

}
