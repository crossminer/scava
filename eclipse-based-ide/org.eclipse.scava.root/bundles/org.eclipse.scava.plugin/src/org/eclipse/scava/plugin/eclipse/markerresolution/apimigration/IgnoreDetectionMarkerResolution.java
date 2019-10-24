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

import java.util.Arrays;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.IgnoreOccurenceRequestEvent;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.highlight.DetectionHighlightData;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.highlight.DetectionHighlightDefinition;

public class IgnoreDetectionMarkerResolution extends ApiMigrationDetectionMarkerResolution {

	public IgnoreDetectionMarkerResolution(DetectionHighlightData detectionHighlightData) {
		super(detectionHighlightData);
	}

	@Override
	public String getLabel() {
		return "Ignore detection";
	}

	@Override
	public void run(IMarker marker) {
		Activator.getDefault().getEventBus()
				.post(new IgnoreOccurenceRequestEvent(null, getDetectionHighlightData(), marker));
	}

	@Override
	public String getDescription() {
		return "Removes highlight of detection of " + getDetectionHighlightData().getLocation().getLocation();
	}

	@Override
	public IMarker[] findOtherMarkers(IMarker[] markers) {
		return Arrays.stream(markers).filter(marker -> {
			try {
				return marker.getType().equals(DetectionHighlightDefinition.markerID);
			} catch (CoreException e) {
				e.printStackTrace();
				return false;
			}
		}).toArray(IMarker[]::new);
	}

}
