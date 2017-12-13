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
package org.eclipse.crossmeter.platform.bugtrackingsystem.github;

import java.io.Serializable;
import java.util.Date;

public class GitHubMilestone implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int number;
    private String title;
    private Date createdAt;
    private Date dueOn;
    private int closedIssues;
    private int openIssues;
    private String description;
    private String status;
    private String url;
    private String creator; 
    
    public GitHubMilestone() {
    }
    
    public GitHubMilestone(int number) {
       this.number = number;
    }
    
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getDueOn() {
        return dueOn;
    }
    public void setDueOn(Date dueOn) {
        this.dueOn = dueOn;
    }
    public int getClosedIssues() {
        return closedIssues;
    }
    public void setClosedIssues(int closedIssues) {
        this.closedIssues = closedIssues;
    }
    public int getOpenIssues() {
        return openIssues;
    }
    public void setOpenIssues(int openIssues) {
        this.openIssues = openIssues;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    
    
    
}
