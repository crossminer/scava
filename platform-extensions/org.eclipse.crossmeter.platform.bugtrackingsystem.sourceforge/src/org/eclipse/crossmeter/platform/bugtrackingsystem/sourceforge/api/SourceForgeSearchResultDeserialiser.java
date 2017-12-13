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
package org.eclipse.crossmeter.platform.bugtrackingsystem.sourceforge.api;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SourceForgeSearchResultDeserialiser extends
        JsonDeserializer<SourceForgeSearchResult> {

    @Override
    public SourceForgeSearchResult deserialize(JsonParser parser,
            DeserializationContext context) throws IOException,
            JsonProcessingException {
        ObjectCodec oc = parser.getCodec();
        JsonNode node = oc.readTree(parser);

        SourceForgeSearchResult result = new SourceForgeSearchResult();

        result.setCount(node.get("count").asInt());

        Iterator<JsonNode> tickets = node.path("tickets").iterator();
        while (tickets.hasNext()) {
            JsonNode ticket = tickets.next();
            result.addTicketId(ticket.get("ticket_num").asInt());
        }

        return result;
    }

}
