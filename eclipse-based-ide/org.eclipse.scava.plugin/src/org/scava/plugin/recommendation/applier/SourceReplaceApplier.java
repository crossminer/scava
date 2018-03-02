/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.scava.plugin.recommendation.applier;

import org.scava.plugin.logger.Logger;
import org.scava.plugin.logger.LoggerMessageKind;
import org.scava.plugin.recommendation.SourceCodeUpdater;
import org.scava.plugin.recommendation.SourceCodeUpdaterException;
import org.scava.commons.recommendation.source.SourceReplaceRecommendation;

public class SourceReplaceApplier implements IRecommendationApplier<SourceReplaceRecommendation> {
	
	@Override
	public void apply(SourceReplaceRecommendation recommendation, RecommendationSetApplier applier) {
		try {
			SourceCodeUpdater.apply(recommendation, applier.getProject());
			applier.offsetSourceRecommendationsPositionsAfterThan(recommendation);
		} catch (SourceCodeUpdaterException e) {
			Logger.log(e.getMessage(), LoggerMessageKind.FROM_CLIENT);
			e.printStackTrace();
		}
	}
}
