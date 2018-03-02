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

/**
 * @author Juri Di Rocco
 *
 */
public class RecommendationItem {

	private Artifact artifact;
	private double significance;
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
	
}
