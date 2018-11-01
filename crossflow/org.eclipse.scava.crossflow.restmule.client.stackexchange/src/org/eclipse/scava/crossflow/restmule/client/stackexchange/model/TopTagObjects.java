package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TopTagObjects {

	public TopTagObjects(){}

	@JsonProperty("question_count") 
	private Integer questionCount;
	
	@JsonProperty("user_id") 
	private Integer userId;
	
	@JsonProperty("tag_name") 
	private String tagName;
	
	@JsonProperty("question_score") 
	private Integer questionScore;
	
	@JsonProperty("answer_count") 
	private Integer answerCount;
	
	@JsonProperty("answer_score") 
	private Integer answerScore;
	
	public Integer getQuestionCount() {
		return this.questionCount;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	
	public String getTagName() {
		return this.tagName;
	}
	
	public Integer getQuestionScore() {
		return this.questionScore;
	}
	
	public Integer getAnswerCount() {
		return this.answerCount;
	}
	
	public Integer getAnswerScore() {
		return this.answerScore;
	}
	
	@Override
	public String toString() {
		return "TopTagObjects [ "
			+ "questionCount = " + this.questionCount + ", "
			+ "userId = " + this.userId + ", "
			+ "tagName = " + this.tagName + ", "
			+ "questionScore = " + this.questionScore + ", "
			+ "answerCount = " + this.answerCount + ", "
			+ "answerScore = " + this.answerScore + ", "
			+ "]"; 
	}	
}	
