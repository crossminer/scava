/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree;

import org.eclipse.core.resources.IMarker;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.highlight.DetectionHighlightData;
import org.eclipse.scava.plugin.mvc.controller.Controller;

public class IgnoreOccurenceRequestEvent extends ApiMigrationRecommendationTreeEclipseInterfaceEvent {
	private final IMarker marker;

	public IgnoreOccurenceRequestEvent(Controller source, DetectionHighlightData detectionHighlightData,
			IMarker marker) {
		super(source, detectionHighlightData);
		this.marker = marker;
	}

	public IMarker getMarker() {
		return marker;
	}

}
