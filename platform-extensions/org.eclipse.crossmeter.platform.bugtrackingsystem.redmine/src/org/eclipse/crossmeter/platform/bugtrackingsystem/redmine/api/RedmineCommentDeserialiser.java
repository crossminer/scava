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
package org.eclipse.crossmeter.platform.bugtrackingsystem.redmine.api;

import java.io.IOException;

import org.eclipse.crossmeter.jackson.ExtendedJsonDeserialiser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

class RedmineCommentDeserialiser extends
		ExtendedJsonDeserialiser<RedmineComment> {

	@Override
	public RedmineComment deserialize(JsonParser parser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {

		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);

		RedmineComment comment = new RedmineComment();
		comment.setCommentId(getText(node, "id"));
		comment.setText(getText(node, "notes"));
		comment.setCreationTime(getDate(node, context, "created_on"));
		comment.setCreator(getText(node, "user", "name"));
		comment.setCreatorId(getInteger(node, "user", "id") );

		JsonNode detailsNode = node.get("details");
		if (null != detailsNode) {
			RedmineCommentDetails[] details = oc.treeToValue(detailsNode,
					RedmineCommentDetails[].class);
			for (RedmineCommentDetails d : details) {
				comment.getDetails().add(d);
			}
		}

		return comment;
	}

}
