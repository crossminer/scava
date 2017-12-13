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
import java.util.Iterator;

import org.eclipse.crossmeter.jackson.ExtendedJsonDeserialiser;

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
