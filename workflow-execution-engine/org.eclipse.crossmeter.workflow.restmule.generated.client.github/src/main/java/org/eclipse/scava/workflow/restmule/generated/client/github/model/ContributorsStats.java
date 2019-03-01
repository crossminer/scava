package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContributorsStats {

	public ContributorsStats() {
	}

	@JsonProperty("total")
	private Integer total;

	@JsonProperty("author")
	private Author author;

	@JsonProperty("weeks")
	private List<Weeks> weeks = new ArrayList<Weeks>();

	public Integer getTotal() {
		return this.total;
	}

	public Author getAuthor() {
		return this.author;
	}

	public List<Weeks> getWeeks() {
		return this.weeks;
	}

	@Override
	public String toString() {
		return "ContributorsStats [ " + "total = " + this.total + ", " + "author = " + this.author + ", " + "weeks = "
				+ this.weeks + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Author {

		public Author() {
		}

		@JsonProperty("avatar_url")
		private String avatarUrl;

		@JsonProperty("id")
		private Integer id;

		@JsonProperty("login")
		private String login;

		@JsonProperty("gravatar_id")
		private String gravatarId;

		@JsonProperty("url")
		private String url;

		public String getAvatarUrl() {
			return this.avatarUrl;
		}

		public Integer getId() {
			return this.id;
		}

		public String getLogin() {
			return this.login;
		}

		public String getGravatarId() {
			return this.gravatarId;
		}

		public String getUrl() {
			return this.url;
		}

		@Override
		public String toString() {
			return "Author [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
					+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", " + "]";
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Weeks {

		public Weeks() {
		}

		@JsonProperty("a")
		private Integer a;

		@JsonProperty("c")
		private Integer c;

		@JsonProperty("d")
		private Integer d;

		@JsonProperty("w")
		private String w;

		public Integer getA() {
			return this.a;
		}

		public Integer getC() {
			return this.c;
		}

		public Integer getD() {
			return this.d;
		}

		public String getW() {
			return this.w;
		}

		@Override
		public String toString() {
			return "Weeks [ " + "a = " + this.a + ", " + "c = " + this.c + ", " + "d = " + this.d + ", " + "w = "
					+ this.w + ", " + "]";
		}
	}

}
