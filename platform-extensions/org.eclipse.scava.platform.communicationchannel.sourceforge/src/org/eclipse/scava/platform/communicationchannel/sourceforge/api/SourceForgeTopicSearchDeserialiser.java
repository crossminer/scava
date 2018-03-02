/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.sourceforge.api;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SourceForgeTopicSearchDeserialiser extends
        JsonDeserializer<SourceForgeTopicSearch> {

    @Override
    public SourceForgeTopicSearch deserialize(JsonParser parser,
            DeserializationContext context) throws IOException,
            JsonProcessingException {
//        System.err.println("SourceForgeTopicSearchDeserialiser: started");
        ObjectCodec oc = parser.getCodec();
        JsonNode node = oc.readTree(parser);

        SourceForgeTopicSearch result = new SourceForgeTopicSearch();

        result.setCount(node.get("count").asInt());
//        System.err.println("SourceForgeTopicSearchDeserialiser: count " + node.get("count").asInt());

        Iterator<JsonNode> forums = node.path("forum").path("topics").iterator();
        while (forums.hasNext()) {
            JsonNode forum = forums.next();
            result.addTopicId(forum.get("_id").asText());
//            System.err.println("SourceForgeTopicSearchDeserialiser: topicId " + forum.get("_id").asText());
        }

        return result;
    }

}
