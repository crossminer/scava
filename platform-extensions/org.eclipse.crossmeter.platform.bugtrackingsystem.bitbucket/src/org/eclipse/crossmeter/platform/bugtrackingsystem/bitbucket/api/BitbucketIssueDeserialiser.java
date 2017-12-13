/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jacob Carter - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.bugtrackingsystem.bitbucket.api;

import java.io.IOException;

import org.eclipse.crossmeter.jackson.ExtendedJsonDeserialiser;

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
