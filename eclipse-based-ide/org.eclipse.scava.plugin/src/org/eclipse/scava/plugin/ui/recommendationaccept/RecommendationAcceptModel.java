/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.recommendationaccept;

import org.eclipse.core.resources.IProject;
import org.eclipse.scava.commons.recommendation.RecommendationSet;
import org.eclipse.scava.plugin.ui.abstractmvc.AbstractModel;

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
