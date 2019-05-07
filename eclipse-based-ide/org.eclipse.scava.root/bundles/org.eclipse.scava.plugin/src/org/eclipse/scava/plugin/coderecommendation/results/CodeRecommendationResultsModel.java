/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.coderecommendation.results;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.scava.plugin.coderecommendation.CodeRecommendation;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationRequest;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationTarget;
import org.eclipse.scava.plugin.collections.AutoSortedArrayList;
import org.eclipse.scava.plugin.mvc.model.Model;

public class CodeRecommendationResultsModel extends Model {
	private final Collection<CodeRecommendationTarget> codeRecommendationTargets = new AutoSortedArrayList<>();

	public Collection<CodeRecommendationTarget> getRecommendationTargets() {
		return Collections.unmodifiableCollection(codeRecommendationTargets);
	}

	public void add(CodeRecommendationTarget target) {
		for (CodeRecommendationTarget codeRecommendationTarget : codeRecommendationTargets) {
			if (codeRecommendationTarget.equals(target)) {
				target.getCodeRecommendationsRequests()
						.forEach(codeRecommendationTarget.getCodeRecommendationsRequests()::add);
				return;
			}
		}

		codeRecommendationTargets.add(target);
	}

	public void remove(CodeRecommendation codeRecommendation) {
		codeRecommendationTargets.forEach(target -> {
			target.getCodeRecommendationsRequests()
					.forEach(request -> request.getCodeRecommendations().remove(codeRecommendation));
			target.getCodeRecommendationsRequests().removeIf(t -> t.getCodeRecommendations().isEmpty());
		});

		codeRecommendationTargets.removeIf(t -> t.getCodeRecommendationsRequests().isEmpty());
	}

	public void remove(CodeRecommendationRequest request) {
		codeRecommendationTargets.forEach(target -> target.getCodeRecommendationsRequests().remove(request));
		codeRecommendationTargets.removeIf(target -> target.getCodeRecommendationsRequests().isEmpty());
	}

	public void remove(CodeRecommendationTarget target) {
		codeRecommendationTargets.remove(target);
	}

	public void removeAll() {
		codeRecommendationTargets.clear();
	}
}
