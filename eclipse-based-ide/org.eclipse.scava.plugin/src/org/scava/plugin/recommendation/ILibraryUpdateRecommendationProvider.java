/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.scava.plugin.recommendation;

import org.scava.plugin.context.sourcecodestatus.SourceCodeStatusException;
import org.scava.commons.library.Library;
import org.scava.commons.recommendation.RecommendationSet;
import org.eclipse.jdt.core.IJavaProject;

public interface ILibraryUpdateRecommendationProvider {
	
	RecommendationSet requestRecommendationsToUpdateLibraryInProject(IJavaProject project, Library updateFrom,
			Library updateTo) throws SourceCodeStatusException;
	
}