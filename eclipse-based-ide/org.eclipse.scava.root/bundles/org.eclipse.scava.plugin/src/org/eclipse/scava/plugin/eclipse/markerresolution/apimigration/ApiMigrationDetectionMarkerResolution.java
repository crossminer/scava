/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.eclipse.markerresolution.apimigration;

import org.eclipse.scava.plugin.eclipse.markerresolution.ScavaMarkerResolution;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.highlight.DetectionHighlightData;

public abstract class ApiMigrationDetectionMarkerResolution extends ScavaMarkerResolution {

	private final DetectionHighlightData detectionHighlightData;

	public ApiMigrationDetectionMarkerResolution(DetectionHighlightData detectionHighlightData) {
		super();
		this.detectionHighlightData = detectionHighlightData;
	}

	public DetectionHighlightData getDetectionHighlightData() {
		return detectionHighlightData;
	}

}
