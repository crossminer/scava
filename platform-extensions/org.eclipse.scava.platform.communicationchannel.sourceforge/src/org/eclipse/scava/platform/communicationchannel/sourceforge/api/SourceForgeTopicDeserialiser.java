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

import org.eclipse.scava.jackson.ExtendedJsonDeserialiser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

public class SourceForgeTopicDeserialiser extends ExtendedJsonDeserialiser<SourceForgeTopic>{

    @Override
    public SourceForgeTopic deserialize(JsonParser parser,
            DeserializationContext context) throws IOException,
            JsonProcessingException {
        ObjectCodec oc = parser.getCodec();
        JsonNode node = oc.readTree(parser);

        node = node.get("topic");
        
        SourceForgeTopic topic = new SourceForgeTopic();
   
        String topicId = getText(node, "_id");

        topic.setTopicId(topicId);
        topic.setDiscussionId(getText(node, "discussion_id"));
        topic.setSubject(getText(node, "subject"));

        JsonNode articlesNode = node.path("posts");
        SourceForgeArticle[] articles = oc.treeToValue(articlesNode, SourceForgeArticle[].class);
        for (SourceForgeArticle article: articles) {
        	article.setMessageThreadId(topicId);
            topic.getArticles().add(article);
        }
        
        return topic;
    }

}
