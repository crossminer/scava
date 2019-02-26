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

import java.util.Date;

import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

public class GitHubPullRequestComment extends BugTrackingSystemComment {

    private static final long serialVersionUID = 1L;

    private Date updatedAt;
    private String url;
    

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    

}
