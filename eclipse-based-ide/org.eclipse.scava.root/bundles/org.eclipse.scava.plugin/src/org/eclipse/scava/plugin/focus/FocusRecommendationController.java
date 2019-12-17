/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.focus;

import java.net.URISyntaxException;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.ASTLocation;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.RascalLocation;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.view.IViewEventListener;
import org.eclipse.scava.plugin.mvc.view.ViewPartView;

import com.google.common.collect.Multimap;

public class FocusRecommendationController<ViewType extends ViewPartView<? extends IViewEventListener>>
		extends ModelViewController<FocusRecommendationModel, ViewType> {

	public FocusRecommendationController(Controller parent, FocusRecommendationModel model, ViewType view) {
		super(parent, model, view);
	}

	protected RascalLocation getActiveDeclaration(Set<String> allMethodDeclarations,
			Multimap<String, String> methodInvocations, CompilationUnit ast, ITextSelection textSelection)
			throws URISyntaxException {
		if (allMethodDeclarations.isEmpty()) {
			MessageDialog.openError(getView().getSite().getShell(), "FOCUS Recommendation",
					"Could not extract any method method declarations from your project.");
			return null;
		}

		NodeFinder nodeFinder = new NodeFinder(ast, textSelection.getOffset(), textSelection.getLength());
		ASTNode node = nodeFinder.getCoveringNode();

		while (node != null && node.getNodeType() != ASTNode.METHOD_DECLARATION) {
			node = node.getParent();
		}

		if (node == null) {
			MessageDialog.openInformation(getView().getSite().getShell(), "FOCUS Recommendation",
					"The cursor must be inside a method declaration");
			return null;
		}

		ASTLocation methodDeclarationLocation = new ASTLocation((MethodDeclaration) node);
		RascalLocation mostSimilarLocation = methodDeclarationLocation
				.findMostJaccardSimilar(allMethodDeclarations.stream().map(RascalLocation::new));

		boolean confirm = MessageDialog.openConfirm(getView().getSite().getShell(), "FOCUS Recommendation",
				"Do you want to request FOCUS recommendations for the following method declaration:\n"
						+ mostSimilarLocation.getLocation() + " ?");

		if (!methodInvocations.containsKey(mostSimilarLocation.getRascalLocation())) {
			MessageDialog.openError(getView().getSite().getShell(), "FOCUS Recommendation",
					"Could not extract any method invocations from the given method declaration.\nIn order to use FOCUS features the selected method declaration must have some method invocations.");
			return null;
		}

		return confirm ? mostSimilarLocation : null;
	}
}
