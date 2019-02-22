/*******************************************************************************
 * Copyright (c) 2019 University of Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.bitbucket.model.issue;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "priority",
    "kind",
    "repository",
    "links",
    "reporter",
    "title",
    "component",
    "votes",
    "watches",
    "content",
    "assignee",
    "state",
    "version",
    "edited_on",
    "created_on",
    "milestone",
    "updated_on",
    "type",
    "id"
})
public class Issue{

    @JsonProperty("priority")
    private String priority;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("repository")
    private Repository repository;
    @JsonProperty("links")
    private Links links;
    @JsonProperty("reporter")
    private Reporter reporter;
    @JsonProperty("title")
    private String title;
    @JsonProperty("component")
    private Object component;
    @JsonProperty("votes")
    private int votes;
    @JsonProperty("watches")
    private int watches;
    @JsonProperty("content")
    private Content content;
    @JsonProperty("assignee")
    private Assignee assignee;
    @JsonProperty("state")
    private String state;
    @JsonProperty("version")
    private Object version;
    @JsonProperty("edited_on")
    private Object editedOn;
    @JsonProperty("created_on")
    private String createdOn;
    @JsonProperty("milestone")
    private Object milestone;
    @JsonProperty("updated_on")
    private String updatedOn;
    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private String id;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("priority")
    public String getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(String priority) {
        this.priority = priority;
    }

    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    @JsonProperty("repository")
    public Repository getRepository() {
        return repository;
    }

    @JsonProperty("repository")
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @JsonProperty("links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(Links links) {
        this.links = links;
    }

    @JsonProperty("reporter")
    public Reporter getReporter() {
        return reporter;
    }

    @JsonProperty("reporter")
    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("component")
    public Object getComponent() {
        return component;
    }

    @JsonProperty("component")
    public void setComponent(Object component) {
        this.component = component;
    }

    @JsonProperty("votes")
    public int getVotes() {
        return votes;
    }

    @JsonProperty("votes")
    public void setVotes(int votes) {
        this.votes = votes;
    }

    @JsonProperty("watches")
    public int getWatches() {
        return watches;
    }

    @JsonProperty("watches")
    public void setWatches(int watches) {
        this.watches = watches;
    }

    @JsonProperty("content")
    public Content getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(Content content) {
        this.content = content;
    }

    @JsonProperty("assignee")
    public Assignee getAssignee() {
        return assignee;
    }

    @JsonProperty("assignee")
    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("version")
    public Object getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(Object version) {
        this.version = version;
    }

    @JsonProperty("edited_on")
    public Object getEditedOn() {
        return editedOn;
    }

    @JsonProperty("edited_on")
    public void setEditedOn(Object editedOn) {
        this.editedOn = editedOn;
    }

    @JsonProperty("created_on")
    public String getCreatedOn() {
        return createdOn;
    }

    @JsonProperty("created_on")
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @JsonProperty("milestone")
    public Object getMilestone() {
        return milestone;
    }

    @JsonProperty("milestone")
    public void setMilestone(Object milestone) {
        this.milestone = milestone;
    }

    @JsonProperty("updated_on")
    public String getUpdatedOn() {
        return updatedOn;
    }

    @JsonProperty("updated_on")
    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
