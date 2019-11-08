/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.webreferenceviewer.reference.sites;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.scava.plugin.webreferenceviewer.reference.WebReferenceModel;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class StackOverflowReferenceModel extends WebReferenceModel {

	private StackOverflowReferenceModel(String url, String title) {
		super(url, title);
	}

	public static void getModelsAsync(Collection<String> postIds, Consumer<StackOverflowReferenceModel> successConsumer,
			Consumer<Exception> failConsumer) {
		new Thread(() -> {
			try {
				String postsAPIAddress = "/2.2/posts/{ids}?site=stackoverflow";
				JsonArray postItems = requestItems(postsAPIAddress, postIds);

				Map<String, String> postIdToLink = new HashMap<>();
				Map<String, String> postIdToQuestionId = new HashMap<>();

				Set<String> answerTypePostIds = new HashSet<>();

				postItems.forEach(jsonElement -> {
					JsonObject postItem = jsonElement.getAsJsonObject();

					String postId = postItem.get("post_id").getAsString();

					String link = postItem.get("link").getAsString();
					postIdToLink.put(postId, link);

					String postType = postItem.get("post_type").getAsString();
					if (postType.equals("answer")) {
						answerTypePostIds.add(postId);
					} else {
						postIdToQuestionId.put(postId, postId);
					}
				});

				if (!answerTypePostIds.isEmpty()) {
					String answerAPIAddress = "/2.2/answers/{ids}?site=stackoverflow";
					JsonArray answerItems = requestItems(answerAPIAddress, answerTypePostIds);
					answerItems.forEach(jsonElement -> {
						JsonObject answerItem = jsonElement.getAsJsonObject();

						String answerId = answerItem.get("answer_id").getAsString();
						String questionId = answerItem.get("question_id").getAsString();

						postIdToQuestionId.put(answerId, questionId);
					});
				}

				Map<String, String> questionIdToTitle = new HashMap<>();

				String questionsAPIAddress = "/2.2/questions/{ids}?site=stackoverflow";
				JsonArray questionItems = requestItems(questionsAPIAddress, postIdToQuestionId.values());
				questionItems.forEach(jsonElement -> {
					JsonObject questionItem = jsonElement.getAsJsonObject();

					String title = questionItem.get("title").getAsString();
					String questionId = questionItem.get("question_id").getAsString();

					questionIdToTitle.put(questionId, title);
				});

				postIds.forEach(postId -> {
					String url = postIdToLink.get(postId);
					String questionId = postIdToQuestionId.get(postId);
					String title = StringEscapeUtils.unescapeHtml4(questionIdToTitle.get(questionId));
					
					successConsumer.accept(new StackOverflowReferenceModel(url, title));
				});

			} catch (JsonSyntaxException | IOException | StackOverflowErrorResponseException e) {
				failConsumer.accept(e);
			}
		}).start();
	}

	private static JsonArray requestItems(String api, Iterable<? extends CharSequence> ids)
			throws MalformedURLException, IOException, StackOverflowErrorResponseException {
		String address = "https://api.stackexchange.com" + api;
		String injectedAddress = address.replace("{ids}", String.join(";", ids));
		String apiResponse = getContent(injectedAddress);
		JsonObject apiResponseParsed = new GsonBuilder().setLenient().create().fromJson(apiResponse, JsonObject.class);
		if (apiResponseParsed.has("error_id")) {
			throw new StackOverflowErrorResponseException(apiResponseParsed.get("error_id").getAsInt(),
					apiResponseParsed.get("error_message").getAsString(),
					apiResponseParsed.get("error_name").getAsString());
		}
		JsonArray postItems = apiResponseParsed.get("items").getAsJsonArray();
		return postItems;
	}

	private static String getContent(String address) throws MalformedURLException, IOException {
		URL url = new URL(address);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		int responseCode = connection.getResponseCode();
		InputStream openStream;

		try {
			openStream = connection.getInputStream();
		} catch (Exception e) {
			openStream = connection.getErrorStream();
		}

		if ("gzip".equalsIgnoreCase(connection.getContentEncoding())) {
			openStream = new GZIPInputStream(openStream);
		}

		Scanner scanner = new Scanner(openStream);
		StringBuilder stringBuilder = new StringBuilder();
		while (scanner.hasNext()) {
			stringBuilder.append(scanner.nextLine());
		}
		scanner.close();

		return stringBuilder.toString();
	}
}
