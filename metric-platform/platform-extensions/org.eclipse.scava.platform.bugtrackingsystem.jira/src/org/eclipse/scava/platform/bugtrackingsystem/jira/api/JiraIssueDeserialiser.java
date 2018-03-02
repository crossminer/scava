/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.jira.api;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.scava.jackson.ExtendedJsonDeserialiser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

public class JiraIssueDeserialiser extends ExtendedJsonDeserialiser<JiraIssue> {

    // TODO pass in an instance manager? could be delta implementing interface!
    public JiraIssueDeserialiser() {
    }

    @Override
    public JiraIssue deserialize(JsonParser parser,
            DeserializationContext context) throws IOException,
            JsonProcessingException {

        ObjectCodec oc = parser.getCodec();
        JsonNode node = oc.readTree(parser);

        JiraIssue issue = new JiraIssue();
        String bugId = getText(node, "id");
        
        issue.setBugId(bugId);
        issue.setUrl(getText(node, "self"));
        issue.setKey(getText(node, "key"));
        issue.setExpandables(getText(node, "expand"));

        JsonNode fields = node.path("fields");
        issue.setAssignee(getText(fields, "assignee", "name"));
        issue.setCreationTime(getDate(fields, context, "created"));
        issue.setCreator(getText(fields, "reporter", "name"));
        issue.setDescription(getText(fields, "description"));
        issue.setDueDate(getDate(fields, context, "duedate"));
        issue.setIssueType(getText(fields, "issuetype", "id"));
        issue.setNumWatchers(getInteger(fields, "watches", "watchCount"));
        issue.setPriority(getText(fields, "priority", "id"));
        issue.setResolution(getText(fields, "resolution", "id"));
        issue.setResolutionDate(getDate(fields, context, "resolutiondate"));
        issue.setStatus(getText(fields, "status", "id"));
        issue.setSummary(getText(fields, "summary"));
        issue.setUpdateDate(getDate(fields, context, "updated"));
        issue.setVotes(getInteger(fields, "votes", "votes"));

        JsonNode commentsNode = fields.path("comment").path("comments");
        Iterator<JsonNode> it = commentsNode.elements();
        while (it.hasNext()) {               
            JsonNode n = it.next();
            JiraComment comment = oc.treeToValue(n, JiraComment.class);
            comment.setBugId(bugId);
            issue.getComments().add(comment);
        }

        JsonNode affectedVersionsNode = fields.path("versions");
        it = affectedVersionsNode.elements();
        while (it.hasNext()) {
            JsonNode n = it.next();
            String version = getText(n, "id");
            if (null != version) {
                issue.addAffectedVersion(version);
            }
        }

        JsonNode fixVersionsNode = fields.path("fixVersions");
        it = fixVersionsNode.elements();
        while (it.hasNext()) {
            JsonNode n = it.next();
            String version = getText(n, "id");
            if (null != version) {
                issue.addFixVersion(version);
            }
        }

        JsonNode labelsNode = fields.path("labels");
        it = labelsNode.elements();
        while (it.hasNext()) {
            JsonNode n = it.next();
            issue.addLabel(n.asText());
        }

        JsonNode subtasksNode = fields.path("subtasks");
        it = subtasksNode.elements();
        while (it.hasNext()) {
            JsonNode n = it.next();
            String subTask = getText(n, "id");
            if (null != subTask) {
                issue.addSubTask(subTask);
            }
        }
        
        return issue;
    }

}
