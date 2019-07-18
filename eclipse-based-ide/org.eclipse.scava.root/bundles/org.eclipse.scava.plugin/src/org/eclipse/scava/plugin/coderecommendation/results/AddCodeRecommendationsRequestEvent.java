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

import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationTarget;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.event.routed.RoutedEvent;

public class AddCodeRecommendationsRequestEvent extends RoutedEvent {
	private final CodeRecommendationTarget target;

	public AddCodeRecommendationsRequestEvent(Controller source, CodeRecommendationTarget target) {
		super(source);
		this.target = target;
	}

	public CodeRecommendationTarget getTarget() {
		return target;
	}
}
