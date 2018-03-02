/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.recommendation.applier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.scava.commons.recommendation.Recommendation;
import org.eclipse.scava.commons.recommendation.RecommendationSet;
import org.eclipse.scava.commons.recommendation.library.AddAntLibraryRecommendation;
import org.eclipse.scava.commons.recommendation.library.AddMavenLibraryRecommendation;
import org.eclipse.scava.commons.recommendation.library.AddSimpleLibraryRecommendation;
import org.eclipse.scava.commons.recommendation.library.RemoveLibraryRecommendation;
import org.eclipse.scava.commons.recommendation.source.SourceRecommendation;
import org.eclipse.scava.commons.recommendation.source.SourceReplaceRecommendation;

public class RecommendationSetApplier {
	private static Map<Class<? extends Recommendation>, IRecommendationApplier<? extends Recommendation>> appliers;
	private IProject project;
	private RecommendationSet recommendationSet;

	static {
		appliers = new HashMap<>();
		appliers.put(AddAntLibraryRecommendation.class, new AddAntLibraryApplier());
		appliers.put(AddMavenLibraryRecommendation.class, new AddMavenLibraryApplier());
		appliers.put(AddSimpleLibraryRecommendation.class, new AddSimpleLibraryApplier());
		appliers.put(RemoveLibraryRecommendation.class, new RemoveLibraryApplier());
		appliers.put(SourceReplaceRecommendation.class, new SourceReplaceApplier());
	}

	public RecommendationSetApplier(IProject project, RecommendationSet recommendationSet) {
		this.project = project;
		this.recommendationSet = recommendationSet;
	}

	public IProject getProject() {
		return project;
	}

	public void apply() {
		Iterator<Recommendation> recommendationIterator = recommendationSet.iterator();
		while (recommendationIterator.hasNext()) {
			process(recommendationIterator);
		}
	}

	private Recommendation process(Iterator<Recommendation> recommendationIterator) {
		Recommendation recommendation = recommendationIterator.next();

		applyWithProperApplier(recommendation);
		recommendationIterator.remove();

		return recommendation;
	}

	private void applyWithProperApplier(Recommendation recommendation) {
		IRecommendationApplier<? extends Recommendation> specificApplier = appliers.get(recommendation.getClass());
		executeApplier(specificApplier, recommendation);
	}

	@SuppressWarnings("unchecked")
	private <RecommendationType extends Recommendation> void executeApplier(
			IRecommendationApplier<RecommendationType> applier, Recommendation recommendation) {
		RecommendationType castedRecommendation = (RecommendationType) recommendation;
		applier.apply(castedRecommendation, this);
	}

	public void offsetSourceRecommendationsPositionsAfterThan(SourceReplaceRecommendation recommendation) {
		int length = recommendation.getEndChar() - recommendation.getStartChar() + 1;
		int offsetPlus = recommendation.getNewCode().length() - length;

		recommendationSet.forEach(laterRecommendation -> {
			if (laterRecommendation instanceof SourceRecommendation) {
				SourceRecommendation sourceRecommendation = (SourceRecommendation) laterRecommendation;

				if (recommendation.getTargetFile().equals(sourceRecommendation.getTargetFile())
						&& recommendation.getEndChar() < sourceRecommendation.getStartChar()) {
					sourceRecommendation.offsetPosition(offsetPlus);
				}
			}
		});
	}
}
