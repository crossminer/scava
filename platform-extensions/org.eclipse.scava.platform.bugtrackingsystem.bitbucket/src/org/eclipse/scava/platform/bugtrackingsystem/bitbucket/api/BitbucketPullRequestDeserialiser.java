/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.bitbucket.api;

import java.io.IOException;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.eclipse.scava.jackson.ExtendedJsonDeserialiser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

public class BitbucketPullRequestDeserialiser extends
		ExtendedJsonDeserialiser<BitbucketPullRequest> {

	private static final DateTimeFormatter DATE_FORMATTER = ISODateTimeFormat
			.dateTime();

	@Override
	public BitbucketPullRequest deserialize(JsonParser parser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);

		BitbucketPullRequest pullRequest = new BitbucketPullRequest();
		pullRequest.setId(getLong(node, "id"));
		pullRequest.setAuthor(getText(node, "author", "username"));
		pullRequest.setClosed_by(getText(node, "closed_by", "username"));
		pullRequest
				.setCloseSourceBranch(getBoolean(node, "close_source_branch"));
		pullRequest.setCreatedOn(getDate(node, DATE_FORMATTER, "created_on"));
		pullRequest.setDescription(getText(node, "description"));
		pullRequest.setReason(getText(node, "reason"));
		pullRequest.setState(getText(node, "state"));
		pullRequest.setTitle(getText(node, "title"));
		pullRequest.setUpdatedOn(getDate(node, DATE_FORMATTER, "updated_on"));

		JsonNode linksNode = node.get("links");
		if (null != linksNode) {
			pullRequest.setLinks(oc
					.treeToValue(linksNode, BitbucketLinks.class));
		}

		JsonNode destNode = node.get("destination");
		if (null != destNode) {
			pullRequest.setDestination(oc.treeToValue(destNode,
					BitbucketPullRequestRepository.class));
		}

		JsonNode sourceNode = node.get("source");
		if (null != sourceNode) {
			pullRequest.setSource(oc.treeToValue(sourceNode,
					BitbucketPullRequestRepository.class));
		}

		JsonNode mergeCommitNode = node.get("merge_commit");
		if (null != mergeCommitNode) {
			pullRequest.setMergeCommit(oc.treeToValue(mergeCommitNode,
					BitbucketCommitSummary.class));
		}

		return pullRequest;
	}
}
