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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.scava.java.m3.M3Java;
import org.eclipse.scava.plugin.async.api.ApiAsyncBuilder;
import org.eclipse.scava.plugin.async.api.IApiAsyncBuilder;
import org.eclipse.scava.plugin.feedback.FeedbackResource;
import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.mvc.model.Model;
import org.eclipse.scava.plugin.preferences.Preferences;
import org.rascalmpl.interpreter.asserts.ImplementationError;

import com.google.common.collect.Multimap;

import io.swagger.client.api.RecommenderRestControllerApi;
import io.swagger.client.model.FocusInput;
import io.swagger.client.model.MethodDeclaration;
import io.swagger.client.model.Query;
import io.swagger.client.model.Recommendation;
import io.usethesource.vallang.IConstructor;
import io.usethesource.vallang.ISet;
import io.usethesource.vallang.ISourceLocation;
import io.usethesource.vallang.ITuple;
import io.usethesource.vallang.IValue;

public class FocusRecommendationModel extends Model {

	private final KnowledgeBaseAccess knowledgeBaseAccess;
	private final M3Java extractor;
	private Map<Object, FeedbackResource> feedbackResources;

	public FocusRecommendationModel(KnowledgeBaseAccess knowledgeBaseAccess) throws ImplementationError {
		super();
		this.knowledgeBaseAccess = knowledgeBaseAccess;
		extractor = new M3Java();
	}

	public IValue getM3(IProject project) throws URISyntaxException, ImplementationError {
		return extractor.createM3FromEclipseProject(project.getName());
	}

	public Multimap<String, String> getMethodInvocations(IValue m3) {
		return extractor.extractMethodInvocations(m3);
	}

	public Set<String> gettAllMethodDeclarations(IValue m3) {
		ISet declarations = ((ISet) ((IConstructor) m3).asWithKeywordParameters().getParameter("declarations"));
		return declarations.stream().map(ITuple.class::cast).map(t -> t.get(0)).map(ISourceLocation.class::cast)
				.filter(sl -> !sl.toString().isEmpty()).filter(sl -> sl.getScheme().equals("java+method"))
				.map(ISourceLocation::toString).collect(Collectors.toSet());
	}

	public IApiAsyncBuilder<Recommendation> getFocusApiCallRecommendation(String activeDeclaration,
			Multimap<String, String> methodInvocations) {
		RecommenderRestControllerApi recommenderRestController = knowledgeBaseAccess
				.getRecommenderRestController(Preferences.TIMEOUT_FOCUS_APICALL);

		Query query = buildQuery(activeDeclaration, methodInvocations);
		return ApiAsyncBuilder.build(apiCallback -> recommenderRestController
				.getNextApiCallsRecommendationUsingPOSTAsync(query, apiCallback), query);
	}

	public IApiAsyncBuilder<Recommendation> getFocusCodeSnippetRecommendation(String activeDeclaration,
			Multimap<String, String> methodInvocations) {
		RecommenderRestControllerApi recommenderRestController = knowledgeBaseAccess
				.getRecommenderRestController(Preferences.TIMEOUT_FOCUS_CODESNIPPET);

		Query query = buildQuery(activeDeclaration, methodInvocations);
		return ApiAsyncBuilder.build(apiCallback -> recommenderRestController
				.getFocusCodeSnippetRecommendationUsingPOSTAsync(query, apiCallback), query);
	}

	private Query buildQuery(String activeDeclaration, Multimap<String, String> methodInvocations) {
		FocusInput focusInput = new FocusInput();
		focusInput.setActiveDeclaration(activeDeclaration);
		for (String declaration : methodInvocations.keySet()) {
			MethodDeclaration methodDeclaration = new MethodDeclaration();
			methodDeclaration.setName(declaration);
			for (String invocation : methodInvocations.get(declaration)) {
				methodDeclaration.addMethodInvocationsItem(invocation);
			}
			focusInput.addMethodDeclarationsItem(methodDeclaration);
		}

		Query query = new Query();
		query.setFocusInput(focusInput);
		return query;
	}

	public Map<Object, FeedbackResource> getFeedbackResources() {
		return feedbackResources;
	}

	public void setFeedbackResources(Map<Object, FeedbackResource> feedbackResources) {
		this.feedbackResources = feedbackResources;
	}

}
