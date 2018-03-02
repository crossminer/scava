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

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;

public class JiraIssue extends BugTrackingSystemBug {

    private static final long serialVersionUID = 1L;

    private String key;
    private String url;
    private String summary;
    private String description;
    private String issueType; // id

    private String expandables;

    private Integer votes;

    private String assignee; // id

    private Integer numWatchers;

    private Set<String> subTasks = new HashSet<String>(); // id
    private Set<String> labels = new HashSet<String>(); // id
    private Set<String> affectedVersions = new HashSet<String>(); // id
    private Set<String> fixVersions = new HashSet<String>(); // id

    private Date updateDate;
    private Date dueDate;
    private Date resolutionDate;

    public String getKey() {
        return key;
    }

    public String getUrl() {
        return url;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public String getIssueType() {
        return issueType;
    }

    public Integer getVotes() {
        return votes;
    }

    public String getAssignee() {
        return assignee;
    }

    public Integer getNumWatchers() {
        return numWatchers;
    }

    public Set<String> getSubTasks() {
        return Collections.unmodifiableSet(subTasks);
    }

    public Set<String> getLabels() {
        return Collections.unmodifiableSet(labels);
    }

    public String getExpandables() {
        return expandables;
    }

    public Set<String> getAffectedVersions() {
        return Collections.unmodifiableSet(affectedVersions);
    }

    public Set<String> getFixVersions() {
        return Collections.unmodifiableSet(fixVersions);
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getResolutionDate() {
        return resolutionDate;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setNumWatchers(Integer numWatchers) {
        this.numWatchers = numWatchers;
    }

    public void addSubTask(String subTask) {
        this.subTasks.add(subTask);
    }

    public void addLabel(String label) {
        this.labels.add(label);
    }

    public void setExpandables(String expandables) {
        this.expandables = expandables;
    }

    public void addAffectedVersion(String affectedVersion) {
        this.affectedVersions.add(affectedVersion);
    }

    public void addFixVersion(String fixVersion) {
        this.fixVersions.add(fixVersion);
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setResolutionDate(Date resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

}
