/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.redmine.api;

import java.io.IOException;

import org.eclipse.scava.jackson.ExtendedJsonDeserialiser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;

class RedmineBasicSearchResultDeserialiser extends
		ExtendedJsonDeserialiser<RedmineBasicSearchResult> {

	@Override
	public RedmineBasicSearchResult deserialize(JsonParser parser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {

		RedmineBasicSearchResult result = new RedmineBasicSearchResult();

		while (parser.nextToken() != JsonToken.END_OBJECT) {
			String fieldName = parser.getCurrentName();
			if ("total_count".equals(fieldName)) {
				parser.nextToken();
				result.setTotalCount(parser.getIntValue());
			} else if ("offset".equals(fieldName)) {
				parser.nextToken();
				result.setOffset(parser.getValueAsInt());
			} else if ("limit".equals(fieldName)) {
				parser.nextToken();
				result.setLimit(parser.getValueAsInt());
			} else if ("issues".equals(fieldName)) {
				if (parser.nextToken() != JsonToken.START_ARRAY) {
					throw context.wrongTokenException(parser,
							JsonToken.START_ARRAY,
							"Expecting an array of issues");
				}
				while (parser.nextToken() != JsonToken.END_ARRAY) {
					while (parser.nextToken() != JsonToken.END_OBJECT) {
						String name = parser.getCurrentName();
						if (name.equals("id")) {
							parser.nextToken();
							result.getIssueIds().add(parser.getValueAsInt());
						} else {
							parser.skipChildren();
						}
					}
				}
			}
		}

		return result;
	}
}
