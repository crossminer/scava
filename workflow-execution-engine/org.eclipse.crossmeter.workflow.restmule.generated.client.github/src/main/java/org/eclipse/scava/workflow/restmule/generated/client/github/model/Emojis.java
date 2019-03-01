package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Emojis {

	public Emojis() {
	}

	@JsonProperty("100")
	private String sanitized100;

	@JsonProperty("a")
	private String a;

	@JsonProperty("ab")
	private String ab;

	@JsonProperty("-1")
	private String sanitized1_0;

	@JsonProperty("8ball")
	private String sanitized8ball;

	@JsonProperty("1234")
	private String sanitized1234;

	@JsonProperty("+1")
	private String sanitized1;

	public String getSanitized100() {
		return this.sanitized100;
	}

	public String getA() {
		return this.a;
	}

	public String getAb() {
		return this.ab;
	}

	public String getSanitized1_0() {
		return this.sanitized1_0;
	}

	public String getSanitized8ball() {
		return this.sanitized8ball;
	}

	public String getSanitized1234() {
		return this.sanitized1234;
	}

	public String getSanitized1() {
		return this.sanitized1;
	}

	@Override
	public String toString() {
		return "Emojis [ " + "sanitized100 = " + this.sanitized100 + ", " + "a = " + this.a + ", " + "ab = " + this.ab
				+ ", " + "sanitized1_0 = " + this.sanitized1_0 + ", " + "sanitized8ball = " + this.sanitized8ball + ", "
				+ "sanitized1234 = " + this.sanitized1234 + ", " + "sanitized1 = " + this.sanitized1 + ", " + "]";
	}
}
