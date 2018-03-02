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

import org.eclipse.scava.jackson.ExtendedJsonDeserialiser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

public class BitbucketIssueDeserialiser extends
		ExtendedJsonDeserialiser<BitbucketIssue> {

	@Override
	public BitbucketIssue deserialize(JsonParser parser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);

		BitbucketIssue issue = new BitbucketIssue();

		issue.setBugId(getText(node, "local_id"));
		issue.setCommentCount(getInteger(node, "comment_count"));
		issue.setContent(getText(node, "content"));
		issue.setCreationTime(getDate(node, BitbucketConstants.DATE_TIME_FORMATTER, "utc_created_on"));
		issue.setCreator(getText(node, "reported_by", "username"));
		issue.setFollowerCount(getInteger(node, "follower_count"));
		issue.setPriority(getText(node, "priority"));
		issue.setResourceUri(getText(node, "resource_uri"));
		issue.setResponsible(getText(node, "responsible", "username"));
		issue.setSpam(getBoolean(node, "is_spam"));
		issue.setStatus(getText(node, "status"));
		issue.setTitle(getText(node, "title"));
		issue.setUpdateDate(getDate(node, BitbucketConstants.DATE_TIME_FORMATTER, "utc_last_updated"));

		JsonNode metadataNode = node.get("metadata");
		if (null != metadataNode) {
			BitbucketIssue.Metadata metadata = oc.treeToValue(metadataNode,
					BitbucketIssue.Metadata.class);
			issue.setMetadata(metadata);
		}

		return issue;
	}

}
