/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.github;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;

public class GitHubIssue extends BugTrackingSystemBug {

    private static final long serialVersionUID = 1L;

    private String title; // TODO should this be in superclass?

    private Date closedTime;
    private Date updatedTime;
    private int numComments;
    private List<GitHubLabel> labels = new ArrayList<GitHubLabel>();
    private Integer milestone;
    private String body;
    private String bodyHtml;
    private String bodyText;
    private String url;
    private String htmlUrl;
    private String assignee;

    public GitHubIssue() {
    }

    public GitHubIssue(long id) {
        setBugId(Long.toString(id));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(Date closedTime) {
        this.closedTime = closedTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public List<GitHubLabel> getLabels() {
        return labels;
    }

    public void addLabel(GitHubLabel label) {
        labels.add(label);
    }

    public Integer getMilestone() {
        return milestone;
    }

    public void setMilestone(Integer milestone) {
        this.milestone = milestone;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

}
