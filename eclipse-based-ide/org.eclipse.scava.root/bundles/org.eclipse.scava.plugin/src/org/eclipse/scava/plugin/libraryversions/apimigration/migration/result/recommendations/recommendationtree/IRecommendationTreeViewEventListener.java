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

import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.CodeSnippetTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.CodeSnippetsTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.DetectionOccurenceTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.DetectionTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.DetectionTypeTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.PathPartTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.TreeElement;
import org.eclipse.scava.plugin.mvc.view.IViewEventListener;

public interface IRecommendationTreeViewEventListener extends IViewEventListener {

	void onTreeElementSelected(TreeElement treeElement);

	void onTreeElementDoubleClicked(TreeElement treeElement);

	void onIgnoreAllMigration();

	void onIgnoreAllMigrationsOfType(DetectionTypeTreeElement detectionTypeTreeElement);

	void onIgnoreAllMigrationUnder(PathPartTreeElement pathPartTreeElement);

	void onIgnoreAllMigrationUnder(DetectionTreeElement detectionTreeElement);

	void onIgnoreAllMigrationUnder(DetectionTypeTreeElement detectionTypeTreeElement);

	void onIgnore(DetectionOccurenceTreeElement detectionOccurenceTreeElement);

	void onIgnoreAllSnippets();

	void onIgnoreAllSnippetsUnder(PathPartTreeElement pathPartTreeElement);

	void onIgnoreAllSnippetsUnder(CodeSnippetsTreeElement codeSnippetsTreeElement);

	void onIgnore(CodeSnippetTreeElement codeSnippetTreeElement);

}
