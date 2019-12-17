/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.focus.apicall;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.scava.plugin.async.api.ApiAsyncRequestController;
import org.eclipse.scava.plugin.feedback.FeedbackController;
import org.eclipse.scava.plugin.feedback.FeedbackModel;
import org.eclipse.scava.plugin.feedback.FeedbackResource;
import org.eclipse.scava.plugin.feedback.FeedbackView;
import org.eclipse.scava.plugin.focus.FocusRecommendationController;
import org.eclipse.scava.plugin.focus.FocusRecommendationModel;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.RascalLocation;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.swt.widgets.Display;

import com.google.common.collect.Multimap;

import io.swagger.client.ApiException;
import io.swagger.client.model.Query;
import io.swagger.client.model.Recommendation;
import io.swagger.client.model.RecommendationItem;
import io.usethesource.vallang.IValue;

public class FocusApiCallRecommendationController extends FocusRecommendationController<FocusApiCallRecommendationView>
		implements IFocusApiCallRecommendationViewEventListener {
	private final ApiAsyncRequestController<Recommendation> recommendationRequestController;

	public FocusApiCallRecommendationController(Controller parent, FocusRecommendationModel model,
			FocusApiCallRecommendationView view) {
		super(parent, model, view);

		recommendationRequestController = new ApiAsyncRequestController<>(this, b -> b
				.onSuccessWithQuery(this::onSuccess).onFail(this::onFail).executeWith(Display.getDefault()::asyncExec));
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
			RascalLocation activeDeclaration = getActiveDeclaration(gettAllMethodDeclarations, methodInvocations, ast,
					textSelection);
			if (activeDeclaration != null) {
				recommendationRequestController.execute(getModel()
						.getFocusApiCallRecommendation(activeDeclaration.getRascalLocation(), methodInvocations));
				getView().showLoading();
			}
		} catch (Throwable e) {
			getView().showErrorMessage(ErrorHandler.logAndGetMessage(e));
		}
	}

	private void onSuccess(Recommendation recommendation, Query query) {
		RecommendationItem recommendationItem = recommendation.getRecommendationItems().get(0);

		if (recommendationItem == null || recommendationItem.getApiFunctionCallFOCUS().isEmpty()) {
			getView().showErrorMessage("There are no recommended API calls");
		} else {
			List<Entry<String, Float>> apiCalls = recommendationItem.getApiFunctionCallFOCUS().entrySet().stream()
					.map(this::processApiCall).collect(Collectors.toList());

			getModel().setFeedbackResources(apiCalls.stream().collect(
					Collectors.toMap(Function.identity(), apiCall -> new FeedbackResource(query, recommendationItem))));

			getView().showFunctionCallRecommendations(apiCalls);
		}
	}

	private static class ApiCallEntry implements Entry<String, Float> {
		private String key;
		private Float value;

		public ApiCallEntry(String key, Float value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public String getKey() {
			return key;
		}

		@Override
		public Float getValue() {
			return value;
		}

		@Override
		public Float setValue(Float value) {
			return this.value = value;
		}
	}

	private Entry<String, Float> processApiCall(Entry<String, Float> entry) {
		String call = entry.getKey();

		if (call.startsWith("|") && call.endsWith("|")) {
			call = call.substring(1, call.length() - 1);
		}

		int pos;
		if ((pos = call.indexOf(":///")) != -1) {
			call = call.substring(pos + 4);
		}

		call = call.replaceAll("/", ".").replaceAll("%5B", "\\[").replaceAll("%5D", "\\]");

		return new ApiCallEntry(call, entry.getValue());
	}

	private void onFail(ApiException e) {
		getView().showErrorMessage(ErrorHandler.logAndGetMessage(e));
	}

	@Override
	public void onLeaveFeedback() {
		FeedbackResource feedbackResource = getModel().getFeedbackResources().values().iterator().next();
		FeedbackModel feedbackModel = new FeedbackModel(feedbackResource);
		FeedbackView feedbackView = new FeedbackView(getView().getSite().getShell());
		FeedbackController feedbackController = new FeedbackController(this, feedbackModel, feedbackView);
		feedbackController.init();
	}
}
