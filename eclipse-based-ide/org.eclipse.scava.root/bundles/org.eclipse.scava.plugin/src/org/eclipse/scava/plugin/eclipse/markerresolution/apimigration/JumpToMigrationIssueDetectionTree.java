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

import org.eclipse.core.resources.IMarker;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.JumpToMigrationIssueRequestEvent;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.highlight.DetectionHighlightData;

public class JumpToMigrationIssueDetectionTree extends ApiMigrationDetectionMarkerResolution {

	public JumpToMigrationIssueDetectionTree(DetectionHighlightData detectionHighlightData) {
		super(detectionHighlightData);
	}

	@Override
	public String getLabel() {
		return "Jump to migration issue in tree view";
	}

	@Override
	public void run(IMarker marker) {
		Activator.getDefault().getEventBus()
				.post(new JumpToMigrationIssueRequestEvent(null, getDetectionHighlightData()));
	}

	@Override
	public String getDescription() {
		return "Jumps to the related migration issue in tree view";
	}

}
