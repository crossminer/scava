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

import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.highlight.DetectionHighlightData;
import org.eclipse.scava.plugin.main.EclipseInterfaceEvent;
import org.eclipse.scava.plugin.mvc.controller.Controller;

public class ApiMigrationRecommendationTreeEclipseInterfaceEvent extends EclipseInterfaceEvent {
	private final DetectionHighlightData detectionHighlightData;

	public ApiMigrationRecommendationTreeEclipseInterfaceEvent(Controller source,
			DetectionHighlightData detectionHighlightData) {
		super(source);
		this.detectionHighlightData = detectionHighlightData;
	}

	public DetectionHighlightData getDetectionHighlightData() {
		return detectionHighlightData;
	}
}
