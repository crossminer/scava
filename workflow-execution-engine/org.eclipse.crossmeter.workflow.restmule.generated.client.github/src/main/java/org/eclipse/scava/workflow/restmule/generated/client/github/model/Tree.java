package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tree {

	public Tree() {
	}

	@JsonProperty("sha")
	private String sha;

	@JsonProperty("url")
	private String url;

	@JsonProperty("tree")
	private List<TreeInner> tree = new ArrayList<TreeInner>();

	public String getSha() {
		return this.sha;
	}

	public String getUrl() {
		return this.url;
	}

	public List<TreeInner> getTree() {
		return this.tree;
	}

	@Override
	public String toString() {
		return "Tree [ " + "sha = " + this.sha + ", " + "url = " + this.url + ", " + "tree = " + this.tree + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class TreeInner {

		public TreeInner() {
		}

		@JsonProperty("mode")
		private String mode;

		@JsonProperty("path")
		private String path;

		@JsonProperty("size")
		private Integer size;

		@JsonProperty("type")
		private String type;

		@JsonProperty("sha")
		private String sha;

		@JsonProperty("url")
		private String url;

		public String getMode() {
			return this.mode;
		}

		public String getPath() {
			return this.path;
		}

		public Integer getSize() {
			return this.size;
		}

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
			return "TreeInner [ " + "mode = " + this.mode + ", " + "path = " + this.path + ", " + "size = " + this.size
					+ ", " + "type = " + this.type + ", " + "sha = " + this.sha + ", " + "url = " + this.url + ", "
					+ "]";
		}
	}

}
