/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.coderecommendation;

import io.swagger.client.model.ApiCallResult;

public class CodeRecommendation implements Comparable<CodeRecommendation>, IPreviewable, ICodeRecommendationElement {
	private final CodeRecommendationRequest codeRecommendationRequest;
	private final ApiCallResult recommendation;

	public CodeRecommendation(CodeRecommendationRequest codeRecommendationRequest, ApiCallResult recommendation) {
		super();
		this.codeRecommendationRequest = codeRecommendationRequest;
		this.recommendation = recommendation;
	}

	public CodeRecommendationRequest getCodeRecommendationRequest() {
		return codeRecommendationRequest;
	}

	public ApiCallResult getRecommendation() {
		return recommendation;
	}

	@Override
	public String toString() {
		return "CodeRecommendation [codeRecommendationRequest=" + codeRecommendationRequest + ", recommendation="
				+ recommendation + "]";
	}

	@Override
	public int compareTo(CodeRecommendation o) {
		return recommendation.getPattern().compareToIgnoreCase(o.getRecommendation().getPattern());
	}

	@Override
	public String getPreviewTitle() {
		return getRecommendation().getPattern();
	}

	@Override
	public String getPreviewContent() {
		return String.join("\n", getRecommendation().getCodeLines());
	}

	@Override
	public boolean canBeInserted() {
		return true;
	}

}
