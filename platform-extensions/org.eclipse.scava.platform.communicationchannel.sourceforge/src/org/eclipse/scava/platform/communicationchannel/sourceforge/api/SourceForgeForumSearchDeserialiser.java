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
