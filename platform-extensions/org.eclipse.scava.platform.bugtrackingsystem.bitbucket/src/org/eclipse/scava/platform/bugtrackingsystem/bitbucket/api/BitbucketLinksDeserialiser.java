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
import java.util.Iterator;

import org.eclipse.scava.jackson.ExtendedJsonDeserialiser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

public class BitbucketLinksDeserialiser extends ExtendedJsonDeserialiser<BitbucketLinks>{

	@Override
	public BitbucketLinks deserialize(JsonParser parser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);
		
		BitbucketLinks links = new BitbucketLinks();
		
		Iterator<String> it = node.fieldNames();
		while (it.hasNext()) {
			String key = it.next();
			JsonNode keyNode = node.get(key);
			JsonNode valueNode = keyNode.get("href");
			if (null != valueNode) {
				String value = valueNode.asText();
				if (null != value) {
					links.getValues().put(key, value);
				}

			}
		}
		
		return links;
	}

}
