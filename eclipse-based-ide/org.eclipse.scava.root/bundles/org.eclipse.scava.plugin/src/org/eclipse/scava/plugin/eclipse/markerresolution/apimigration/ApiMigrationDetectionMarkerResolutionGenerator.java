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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.highlight.DetectionHighlightData;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.highlight.DetectionHighlightDefinition;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;
import org.eclipse.ui.IMarkerResolutionGenerator2;

public class ApiMigrationDetectionMarkerResolutionGenerator
		implements IMarkerResolutionGenerator, IMarkerResolutionGenerator2 {

	private static final IMarkerResolution[] empty = new IMarkerResolution[0];

	@Override
	public boolean hasResolutions(IMarker marker) {
		return true;
	}

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		try {
			DetectionHighlightData detectionHighlightData = DetectionHighlightDefinition
					.getDetectionHighlightData(marker);

			IMarkerResolution[] resolution = new IMarkerResolution[] {
					new ShowApiDocumentationMarkerResolution(detectionHighlightData),
					new JumpToMigrationIssueDetectionTree(detectionHighlightData),
					new IgnoreDetectionMarkerResolution(detectionHighlightData) };

			return resolution;
		} catch (CoreException e) {
			e.printStackTrace();
			return empty;
		}
	}

}
