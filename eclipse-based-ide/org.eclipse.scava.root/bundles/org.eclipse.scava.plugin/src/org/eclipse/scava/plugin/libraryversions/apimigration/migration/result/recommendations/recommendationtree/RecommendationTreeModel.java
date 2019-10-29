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

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.ILocation;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.PathPart;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.PathPartTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.TreeElement;
import org.eclipse.scava.plugin.mvc.model.Model;

public class RecommendationTreeModel extends Model {
	private final TreeElement rootElement = new PathPartTreeElement(null);
	private final IJavaProject javaProject;

	public RecommendationTreeModel(IJavaProject javaProject) {
		super();
		this.javaProject = javaProject;
	}

	public IJavaProject getJavaProject() {
		return javaProject;
	}

	public TreeElement getRoot() {
		return rootElement;
	}

	public void clear() {
		rootElement.removeAllChildren();
	}

	public TreeElement getTreeElementOf(ILocation location) {
		List<PathPart> locationParts = location.getLocationParts();
		return getOrBuildPathPartTree(locationParts, rootElement);
	}

	private TreeElement getOrBuildPathPartTree(Iterable<PathPart> pathParts, TreeElement parentElement) {
		TreeElement treeElement = parentElement;
		Iterator<PathPart> iterator = pathParts.iterator();

		while (iterator.hasNext()) {
			PathPart nextPathPart = iterator.next();
			treeElement = treeElement.GetEqualChildOrAdd(new PathPartTreeElement(nextPathPart));
		}

		return treeElement;
	}

	public Object[] getTreeElements() {
		return rootElement.getChildren();
	}

	public void visitTreeElements(Function<TreeElement, Boolean> visitor) {
		for (TreeElement treeElement : rootElement.getChildren()) {
			visitTreeElement(treeElement, visitor);
		}
	}

	private void visitTreeElement(TreeElement treeElement, Function<TreeElement, Boolean> visitor) {
		Boolean visitChildren = visitor.apply(treeElement);
		if (visitChildren.booleanValue() && treeElement.hasChildren()) {
			TreeElement[] children = treeElement.getChildren();
			for (TreeElement child : children) {
				visitTreeElement(child, visitor);
			}
		}
	}
}
