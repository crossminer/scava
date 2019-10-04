package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Events {

	public Events(){}

	@JsonProperty("event_type") 
	private String eventType;
	
	@JsonProperty("the id of the object (answer, comment, question, or user) the event describes") 
	private Integer theIdOfTheObjectAnswerCommentQuestionOrUserTheEventDescribes;
	
	@JsonProperty("link") 
	private String link;
	
	@JsonProperty("creation_date") 
	private Integer creationDate;
	
	@JsonProperty("excerpt") 
	private String excerpt;
	
	public String getEventType() {
		return this.eventType;
	}
	
	public Integer getTheIdOfTheObjectAnswerCommentQuestionOrUserTheEventDescribes() {
		return this.theIdOfTheObjectAnswerCommentQuestionOrUserTheEventDescribes;
	}
	
	public String getLink() {
		return this.link;
	}
	
	public Integer getCreationDate() {
		return this.creationDate;
	}
	
	public String getExcerpt() {
		return this.excerpt;
	}
	
	@Override
	public String toString() {
		return "Events [ "
			+ "eventType = " + this.eventType + ", "
			+ "theIdOfTheObjectAnswerCommentQuestionOrUserTheEventDescribes = " + this.theIdOfTheObjectAnswerCommentQuestionOrUserTheEventDescribes + ", "
			+ "link = " + this.link + ", "
			+ "creationDate = " + this.creationDate + ", "
			+ "excerpt = " + this.excerpt + ", "
			+ "]"; 
	}	
}	
