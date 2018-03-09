/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.knowledgebase.access;

import java.util.List;

import org.eclipse.scava.commons.json.Json;
import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.commons.libraryapi.LibraryAPIElement;
import org.eclipse.scava.commons.recommendation.RecommendationSet;
import org.eclipse.scava.commons.transaction.APIChanges;
import org.eclipse.scava.commons.transaction.Description;
import org.eclipse.scava.commons.transaction.Libraries;
import org.eclipse.scava.commons.transaction.ParsedSourceCodeContext;
import org.eclipse.scava.commons.transaction.Recommendations;
import org.eclipse.scava.commons.transaction.UpdatableAPIUsage;
import org.eclipse.scava.plugin.knowledgebase.access.requestsender.IRequestSender;
import org.eclipse.scava.plugin.knowledgebase.access.requestsender.LocalRequestSender;
import org.eclipse.scava.plugin.knowledgebase.access.requestsender.RemoteRequestSender;
import org.eclipse.scava.plugin.logger.Logger;
import org.eclipse.scava.plugin.logger.LoggerMessageKind;

public class KnowledgeBaseAccessManager implements ILibraryDescriptionProvider {
	private IRequestSender requestSender;
	
	public KnowledgeBaseAccessManager()
	{
		requestSender = new RemoteRequestSender();
		//requestSender = new LocalRequestSender();
	}
	
	private String sendRequest(String address, String content)
	{
		Logger.log("[CLIENT] Send request to " + address + " with content:", LoggerMessageKind.FROM_CLIENT);
		Logger.log(content, LoggerMessageKind.JSON);
		
		String response = requestSender.sendRequest(address, content);
		
		Logger.log("[CLIENT] Response received from server:", LoggerMessageKind.FROM_SERVER);
		Logger.log(response, LoggerMessageKind.JSON);
		
		return response;
	}
	
	// /api/get/library/id; {"Name": "<name>"}
	public Library requestLibraryIdByName(String name)
	{
		throw new UnsupportedOperationException();
	}
	
	// /api/get/library/<id>/<version>/description
	/* (non-Javadoc)
	 * @see org.scava.plugin.knowledgebase.access.ILibraryDescriptionProvider#requestDescriptionOfLibrary(org.scava.commons.library.Library)
	 */
	@Override
	public String requestDescriptionOfLibrary(Library library)
	{
		String address = "/api/get/library/" + library.getId() + "/" + library.getVersion() + "/description";
		String response = sendRequest(address, "");
		Description description = Json.fromJson(response, Description.class);
		return description.getDescription();
	}
	
	// /api/get/library/<id>/<version>/metrics
	public String requestMetricsOfLibrary(Library library)
	{
		throw new UnsupportedOperationException();
	}
	
	// /api/get/library/<id>/<version>/binary
	public String requestURLOfLibrary(Library library)
	{
		throw new UnsupportedOperationException();
	}
	
	// /api/get/library/<id>/<version>/alternatives; {"Filters": $Filter}
	public List<Library> requestAlternativesOfLibrary(Library library)
	{
		String address = "/api/get/library/" + library.getId() + "/" + library.getVersion() + "/alternatives";
		String response = sendRequest(address, "");
		Libraries libraries = Json.fromJson(response, Libraries.class);
		return libraries.getLibraries();
	}
	
	// /api/get/libraries; {"keywords": ["<keyword0>", "<keyword1>", ...], "filters": $Filter}
	public Library[] requestLibrariesByKeywords(List<String> keywords)
	{
		throw new UnsupportedOperationException();
	}
	
	// /api/get/library/<id>/<version>/<version>/apichanges
	public List<LibraryAPIElement> requestLibraryAPIChangesBetweenVersions(Library oldVersion, Library newVersion)
	{
		String address = "/api/get/library/" + newVersion.getId() + "/" + oldVersion.getVersion() + "/" + newVersion.getVersion() + "/apichanges";
		String response = sendRequest(address, "");
		APIChanges apiChanges = Json.fromJson(response, APIChanges.class);
		return apiChanges.getApiElements();
	}
	
	// /api/get/source/updateapiusage; {$UpdatableAPIUsageInContext}
	public RecommendationSet requestRecommendationsToUpdateAPIUsage(UpdatableAPIUsage updatableAPIUsage)
	{		
		String address = "/api/get/source/updateapiusage";
		String content = Json.toJson(updatableAPIUsage);
		String response = sendRequest(address, content);
		Recommendations recommendations = Json.fromJson(response, Recommendations.class);
		return recommendations.getRecommendationSet();
	}
	
	// /api/get/source/generalimprovements; {$ParsedSourceCodeContext}
	public RecommendationSet requestRecommendationsToGeneralImprovements(ParsedSourceCodeContext parsedSourceCodeContext)
	{		
		String address = "/api/get/source/generalimprovements";
		String content = Json.toJson(parsedSourceCodeContext);
		String response = sendRequest(address, content);
		Recommendations recommendations = Json.fromJson(response, Recommendations.class);
		return recommendations.getRecommendationSet();
	}
	
}
