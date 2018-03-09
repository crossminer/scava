/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.scava.plugin.ui.recommendationaccept;

import org.scava.plugin.ui.abstractmvc.AbstractModel;
import org.scava.commons.recommendation.RecommendationSet;
import org.eclipse.core.resources.IProject;

public class RecommendationAcceptModel extends AbstractModel {
	private final RecommendationSet recommendations;
	private final IProject project;

	public RecommendationAcceptModel(RecommendationSet recommendations, IProject project) {
		this.recommendations = recommendations;
		this.project = project;
	}

	public RecommendationSet getRecommendations() {
		return recommendations;
	}

	protected final IProject getProject() {
		return project;
	}

}
