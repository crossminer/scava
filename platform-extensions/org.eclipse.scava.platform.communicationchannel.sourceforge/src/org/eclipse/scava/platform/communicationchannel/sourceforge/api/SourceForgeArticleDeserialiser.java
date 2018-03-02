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

import org.eclipse.scava.platform.communicationchannel.sourceforge.api.SourceForgeAttachment;
import org.eclipse.scava.platform.communicationchannel.sourceforge.api.SourceForgeConstants;
import org.eclipse.scava.jackson.ExtendedJsonDeserialiser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

public class SourceForgeArticleDeserialiser extends ExtendedJsonDeserialiser<SourceForgeArticle>{

	static int articleNumber = 1;
	
    @Override
    public SourceForgeArticle deserialize(JsonParser parser,
            DeserializationContext context) throws IOException,
            JsonProcessingException {

        ObjectCodec oc = parser.getCodec();
        JsonNode node = oc.readTree(parser);
        
        JsonNode attachmentsNode = node.path("attachments");
        SourceForgeAttachment[] attachments = oc.treeToValue(attachmentsNode, SourceForgeAttachment[].class);
        
        SourceForgeArticle article = new SourceForgeArticle();
        article.setArticleNumber(articleNumber++);
        article.setSubject(getText(node, "subject"));
        article.setText(getText(node, "text"));
        article.setUser(getText(node,"author"));
        article.setAttachments(attachments);
        article.setArticleId(getText(node, "slug"));
        article.setDate(getDate(node, SourceForgeConstants.RESPONSE_DATE_FORMATTER, "timestamp"));
        article.setUpdateDate(getDate(node, SourceForgeConstants.RESPONSE_DATE_FORMATTER, "last_edited"));
        article.setReferences(new String[0]);
        return article;
    }

}
