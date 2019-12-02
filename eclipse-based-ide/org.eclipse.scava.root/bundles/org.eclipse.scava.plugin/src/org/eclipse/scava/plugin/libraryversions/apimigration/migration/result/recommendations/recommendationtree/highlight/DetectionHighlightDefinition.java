/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.highlight;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.scava.plugin.codehighlighter.HighlightDefinition;

public class DetectionHighlightDefinition extends HighlightDefinition {

	private static final String detectionHighlightDataAttribute = "detectiondata";
	public static final String markerID = "org.eclipse.scava.plugin.markers.apimigrationdetection";

	public DetectionHighlightDefinition(DetectionHighlightData detectionHighlightData) {
		super(getResource(detectionHighlightData.getAstNode()));

		String compatibilityIssue = "Migration issue near " + detectionHighlightData.getLocation().getLocation()
				+ " during API migration from "
				+ detectionHighlightData.getApiMigrationParameters().getOldLibraryVersion().toMavenCoord() + " to "
				+ detectionHighlightData.getApiMigrationParameters().getNewLibraryVersion().toMavenCoord();

		setAttribute(IMarker.CHAR_START, detectionHighlightData.getAstNode().getStartPosition());
		setAttribute(IMarker.CHAR_END, detectionHighlightData.getAstNode().getStartPosition()
				+ detectionHighlightData.getAstNode().getLength());
		setAttribute(IMarker.MESSAGE,
				"This code chunk has been marked suspicious by the CROSSMINER Eclipse-based IDE.\n" + compatibilityIssue
						+ "\n" + "Detection type: " + detectionHighlightData.getDetectionType());
		setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
		setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
		setAttribute(IMarker.LINE_NUMBER, ((CompilationUnit) detectionHighlightData.getAstNode().getRoot())
				.getLineNumber(detectionHighlightData.getAstNode().getStartPosition()));
		// setAttribute(IMarker.SOURCE_ID, );
		setAttribute(IMarker.LOCATION, compatibilityIssue);
		setAttribute(IMarker.TRANSIENT, true);
		setAttribute(IMarker.USER_EDITABLE, false);
		setAttribute(detectionHighlightDataAttribute, detectionHighlightData);
	}

	private static IResource getResource(ASTNode node) {
		return ((CompilationUnit) node.getRoot()).getJavaElement().getResource();
	}

	public static DetectionHighlightData getDetectionHighlightData(IMarker marker) throws CoreException {
		return (DetectionHighlightData) marker.getAttribute(detectionHighlightDataAttribute);
	}
}
