/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.communicationchannel.sourceforge.api;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SourceForgeForumSearchDeserialiser extends
        JsonDeserializer<SourceForgeForumSearch> {

    @Override
    public SourceForgeForumSearch deserialize(JsonParser parser,
            DeserializationContext context) throws IOException,
            JsonProcessingException {
//        System.err.println("SourceForgeGetDeserialiser: started");
        ObjectCodec oc = parser.getCodec();
        JsonNode node = oc.readTree(parser);

        SourceForgeForumSearch result = new SourceForgeForumSearch();

        result.setCount(node.get("count").asInt());
//        System.err.println("SourceForgeGetDeserialiser: count " + node.get("count").asInt());

        Iterator<JsonNode> forums = node.path("forums").iterator();
        while (forums.hasNext()) {
            JsonNode forum = forums.next();
            result.addForumId(forum.get("shortname").asInt());
//            System.err.println("SourceForgeGetDeserialiser: forumId " + forum.get("shortname").asInt());
        }

        return result;
    }

}
