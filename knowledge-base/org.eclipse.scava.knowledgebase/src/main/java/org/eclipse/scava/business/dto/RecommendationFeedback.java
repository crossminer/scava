package org.eclipse.scava.business.dto;

import org.springframework.data.annotation.Id;

public class RecommendationFeedback {
	@Id
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private Query query;
	private Recommendation recommendation;
	private int stars;
	private String feedback;
	public Query getQuery() {
		return query;
	}
	public void setQuery(Query query) {
		this.query = query;
	}
	public Recommendation getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(Recommendation recommendation) {
		this.recommendation = recommendation;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
}
