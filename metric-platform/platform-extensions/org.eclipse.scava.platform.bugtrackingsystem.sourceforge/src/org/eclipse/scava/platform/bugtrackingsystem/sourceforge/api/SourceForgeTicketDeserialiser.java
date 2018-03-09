/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.sourceforge.api;

import java.io.IOException;

import org.eclipse.scava.jackson.ExtendedJsonDeserialiser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

public class SourceForgeTicketDeserialiser extends ExtendedJsonDeserialiser<SourceForgeTicket>{

    @Override
    public SourceForgeTicket deserialize(JsonParser parser,
            DeserializationContext context) throws IOException,
            JsonProcessingException {
        ObjectCodec oc = parser.getCodec();
        JsonNode node = oc.readTree(parser);
        
        node = node.get("ticket");
        
        SourceForgeTicket ticket = new SourceForgeTicket();
        
        JsonNode attachmentsNode = node.path("attachments");
        SourceForgeAttachment[] attachments = oc.treeToValue(attachmentsNode, SourceForgeAttachment[].class);
        
        JsonNode labelsNode = node.path("labels");
        String[] labels = oc.treeToValue(labelsNode, String[].class);
        
        String bugId = getText(node, "ticket_num");
           
        ticket.setBugId(bugId);
        ticket.setAssignee(getText(node, "assigned_to"));
        ticket.setAssigneeId(getText(node, "assigned_to_id"));
        ticket.setAttachments(attachments);
        ticket.setCommentsUrl(getText(node, "discussion_thread_url"));
        ticket.setCreationTime(getDate(node, SourceForgeConstants.RESPONSE_DATE_FORMATTER, "created_date"));
        ticket.setCreator(getText(node, "reported_by"));
        ticket.setCreatorId(getText(node, "reported_by_id"));
        ticket.setDescription(getText(node, "description"));
        ticket.setInternalId(getText(node, "_id"));
        ticket.setLabels(labels);
        ticket.setPrivate(getBoolean(node, "private"));
        ticket.setStatus(getText(node, "status"));
        ticket.setSummary(getText(node, "summary"));
        ticket.setUpdateDate(getDate(node, SourceForgeConstants.RESPONSE_DATE_FORMATTER, "mod_date"));
        ticket.setVotesDown(getInteger(node, "votes_down"));
        ticket.setVotesUp(getInteger(node, "votes_up"));
        
        JsonNode commentsNode = node.path("discussion_thread").path("posts");
        SourceForgeComment[] comments = oc.treeToValue(commentsNode, SourceForgeComment[].class);
        for (SourceForgeComment comment: comments) {
            comment.setBugId(bugId);
            ticket.getComments().add(comment);
        }
        
        return ticket;
    }

}
