/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business.dto;

import org.eclipse.scava.business.model.Artifact;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Juri Di Rocco
 *
 */
public class RecommendationItem {

	private String apiDocumentationLink;
	private ApiCallResult apiCallRecommendation;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private RecommendedLibrary recommendedLibrary;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Artifact artifact;
	private double significance;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object relatedTo;
	private String recommendationType;
	public String getRecommendationType() {
		return recommendationType;
	}
	public void setRecommendationType(String recommendationType) {
		this.recommendationType = recommendationType;
	}
	public Artifact getArtifact() {
		return artifact;
	}
	public void setArtifact(Artifact artifact) {
		this.artifact = artifact;
	}
	public double getSignificance() {
		return significance;
	}
	public void setSignificance(double significance) {
		this.significance = significance;
	}
	public Object getRelatedTo() {
		return relatedTo;
	}
	public void setRelatedTo(Object relatedTo) {
		this.relatedTo = relatedTo;
	}
	public ApiCallResult getApiCallRecommendation() {
		return apiCallRecommendation;
	}
	public void setApiCallRecommendation(ApiCallResult apiCallRecommendation) {
		this.apiCallRecommendation = apiCallRecommendation;
	}
	public RecommendedLibrary getRecommendedLibrary() {
		return recommendedLibrary;
	}
	public void setRecommendedLibrary(RecommendedLibrary recommendedApi) {
		this.recommendedLibrary = recommendedApi;
	}
	public String getApiDocumentationLink() {
		return apiDocumentationLink;
	}
	public void setApiDocumentationLink(String apiDocumentationRecommendation) {
		this.apiDocumentationLink = apiDocumentationRecommendation;
	}
	
}
