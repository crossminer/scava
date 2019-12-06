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

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.ApiMigrationParameters;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.ILocation;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.TreeElement;

public class DetectionHighlightData {
	private final ILocation location;
	private final String detectionType;
	private final TreeElement treeElement;
	private final ApiMigrationParameters apiMigrationParameters;
	private final ASTNode astNode;

	public DetectionHighlightData(ILocation location, String detectionType, TreeElement treeElement,
			ApiMigrationParameters apiMigrationParameters, ASTNode astNode) {
		super();
		this.location = location;
		this.detectionType = detectionType;
		this.treeElement = treeElement;
		this.apiMigrationParameters = apiMigrationParameters;
		this.astNode = astNode;
	}

	public ILocation getLocation() {
		return location;
	}

	public TreeElement getTreeElement() {
		return treeElement;
	}

	public String getDetectionType() {
		return detectionType;
	}

	public ApiMigrationParameters getApiMigrationParameters() {
		return apiMigrationParameters;
	}

	public ASTNode getAstNode() {
		return astNode;
	}

}
