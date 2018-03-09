/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.recommendation;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.commons.recommendation.RecommendationSet;
import org.eclipse.scava.plugin.context.sourcecodestatus.SourceCodeStatusException;

public interface ILibraryUpdateRecommendationProvider {
	
	RecommendationSet requestRecommendationsToUpdateLibraryInProject(IJavaProject project, Library updateFrom,
			Library updateTo) throws SourceCodeStatusException;
	
}