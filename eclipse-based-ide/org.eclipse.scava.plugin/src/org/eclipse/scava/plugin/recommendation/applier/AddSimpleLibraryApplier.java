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

import org.eclipse.core.runtime.Path;
import org.eclipse.scava.commons.recommendation.library.AddSimpleLibraryRecommendation;
import org.eclipse.scava.plugin.logger.Logger;
import org.eclipse.scava.plugin.logger.LoggerMessageKind;
import org.eclipse.scava.plugin.recommendation.LibraryUpdater;
import org.eclipse.scava.plugin.recommendation.LibraryUpdaterException;

public class AddSimpleLibraryApplier implements IRecommendationApplier<AddSimpleLibraryRecommendation> {
	
	@Override
	public void apply(AddSimpleLibraryRecommendation recommendation, RecommendationSetApplier applier) {
		
		Path libraryPath = new Path(recommendation.getDownloadLink());
		try {
			LibraryUpdater.addLibraryToProject(libraryPath);
		} catch (LibraryUpdaterException e) {
			Logger.log(e.getMessage(), LoggerMessageKind.FROM_CLIENT);
			e.printStackTrace();
		}
	}
	
}
