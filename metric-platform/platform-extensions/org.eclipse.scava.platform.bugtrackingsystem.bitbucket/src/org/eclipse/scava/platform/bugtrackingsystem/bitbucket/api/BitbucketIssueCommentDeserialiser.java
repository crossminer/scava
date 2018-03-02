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

public class BitbucketIssueCommentDeserialiser extends ExtendedJsonDeserialiser<BitbucketIssueComment>{

	@Override
	public BitbucketIssueComment deserialize(JsonParser parser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);
		
		BitbucketIssueComment comment = new BitbucketIssueComment();
		
		comment.setCommentId(getText(node, "comment_id"));
		comment.setConvertMarkup(getBoolean(node, "convert_markup"));
		comment.setCreationTime(getDate(node, BitbucketConstants.DATE_TIME_FORMATTER, "utc_created_on"));
		comment.setCreator(getText(node, "author_info", "username"));
		comment.setSpam(getBoolean(node, "is_spam"));
		comment.setText(getText(node, "content"));
		comment.setUpdateDate(getDate(node, BitbucketConstants.DATE_TIME_FORMATTER, "utc_updated_on"));
		
		return comment;
	}

}
