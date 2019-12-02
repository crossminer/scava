/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.focus.codesnippet;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.scava.plugin.async.api.ApiAsyncRequestController;
import org.eclipse.scava.plugin.focus.FocusRecommendationController;
import org.eclipse.scava.plugin.focus.FocusRecommendationModel;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.RascalLocation;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.swt.widgets.Display;

import com.google.common.collect.Multimap;

import io.swagger.client.ApiException;
import io.swagger.client.model.Recommendation;
import io.swagger.client.model.RecommendationItem;
import io.usethesource.vallang.IValue;

public class FocusCodeSnippetRecommendationController
		extends FocusRecommendationController<FocusCodeSnippetRecommendationView>
		implements IFocusCodeSnippetRecommendationViewEventListener {
	private final ApiAsyncRequestController<Recommendation> recommendationRequestController;

	public FocusCodeSnippetRecommendationController(Controller parent, FocusRecommendationModel model,
			FocusCodeSnippetRecommendationView view) {
		super(parent, model, view);

		recommendationRequestController = new ApiAsyncRequestController<>(this,
				b -> b.onSuccess(this::onSuccess).onFail(this::onFail).executeWith(Display.getDefault()::asyncExec));
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	public void request(IProject project, CompilationUnit ast, ITextSelection textSelection) {
		try {
			IValue m3 = getModel().getM3(project);
			Multimap<String, String> methodInvocations = getModel().getMethodInvocations(m3);
			Set<String> gettAllMethodDeclarations = getModel().gettAllMethodDeclarations(m3);
			RascalLocation activeDeclaration = getActiveDeclaration(gettAllMethodDeclarations, methodInvocations, ast, textSelection);
			if (activeDeclaration != null) {
				recommendationRequestController.execute(getModel()
						.getFocusCodeSnippetRecommendation(activeDeclaration.getRascalLocation(), methodInvocations));
				getView().showLoading();
			}
		} catch (Throwable e) {
			getView().showErrorMessage(ErrorHandler.logAndGetMessage(e));
		}
	}

	private void onSuccess(Recommendation recommendation) {
		List<String> snippets = recommendation.getRecommendationItems().stream().map(RecommendationItem::getCodeSnippet)
				.collect(Collectors.toList());
		if (!snippets.isEmpty()) {
			getView().showCodeSnippets(snippets);
		} else {
			getView().showErrorMessage("There are no recommended code snippets.");
		}
	}

	private void onFail(ApiException e) {
		getView().showErrorMessage(ErrorHandler.logAndGetMessage(e));
	}
}
