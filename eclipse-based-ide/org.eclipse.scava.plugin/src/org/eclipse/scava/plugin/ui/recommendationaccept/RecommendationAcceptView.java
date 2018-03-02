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

import java.util.List;

import org.eclipse.scava.commons.recommendation.Recommendation;
import org.eclipse.scava.plugin.event.notifier.INotifierEvent;
import org.eclipse.scava.plugin.event.notifier.INotifierEventSubscriber;
import org.eclipse.scava.plugin.event.notifier.NotifierEvent;
import org.eclipse.scava.plugin.ui.abstractmvc.JFaceWindowView;

public class RecommendationAcceptView extends JFaceWindowView<RecommendationAcceptDisplay> {
	private final INotifierEvent recommendationsAccepted = registerEvent(new NotifierEvent());
	private final INotifierEvent recommendationsCancelled = registerEvent(new NotifierEvent());
	
	public RecommendationAcceptView(RecommendationAcceptDisplay display) {
		super(display);
		
		getDisplay().getRecommendationsAccepted().subscribe(() -> recommendationsAccepted.invoke());
		getDisplay().getRecommendationsCancelled().subscribe(() -> recommendationsCancelled.invoke());
	}
	
	public INotifierEventSubscriber getRecommendationsAccepted() {
		return recommendationsAccepted;
	}
	
	public INotifierEventSubscriber getRecommendationsCancelled() {
		return recommendationsCancelled;
	}
	
	public void showRecommendations(List<Recommendation> recommendations) {
		getDisplay().showRecommendations(recommendations);
	}
	
}
