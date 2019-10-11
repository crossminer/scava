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

import org.eclipse.scava.plugin.coderecommendation.CodeRecommendation;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationRequest;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationTarget;
import org.eclipse.scava.plugin.coderecommendation.ICodeRecommendationElement;
import org.eclipse.scava.plugin.mvc.view.IViewEventListener;

public interface ICodeRecommendationResultsViewEventListener extends IViewEventListener {
	void onCodeRecommendationSelected(ICodeRecommendationElement element);

	void onTargetDoubleClicked(CodeRecommendationTarget target);

	void onDrop(CodeRecommendation codeRecommendation);
	
	void onDrop(CodeRecommendationRequest codeRecommendationRequest);

	void onDrop(CodeRecommendationTarget target);

	void onDropAll();

}
