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

public class SourceForgeCommentDeserialiser extends ExtendedJsonDeserialiser<SourceForgeComment>{

    @Override
    public SourceForgeComment deserialize(JsonParser parser,
            DeserializationContext context) throws IOException,
            JsonProcessingException {
        
        ObjectCodec oc = parser.getCodec();
        JsonNode node = oc.readTree(parser);
        
        JsonNode attachmentsNode = node.path("attachments");
        SourceForgeAttachment[] attachments = oc.treeToValue(attachmentsNode, SourceForgeAttachment[].class);
        
        SourceForgeComment comment = new SourceForgeComment();
        comment.setSubject(getText(node, "subject"));
        comment.setText(getText(node, "text"));
        comment.setCreator(getText(node,"author"));
        comment.setAttachments(attachments);
        comment.setCommentId(getText(node, "slug"));
        comment.setCreationTime(getDate(node, SourceForgeConstants.RESPONSE_DATE_FORMATTER, "timestamp"));
        comment.setUpdateDate(getDate(node, SourceForgeConstants.RESPONSE_DATE_FORMATTER, "last_edited"));
        
        return comment;
    }

}
