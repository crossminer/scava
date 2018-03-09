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

import java.util.Date;

import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

public class JiraComment extends BugTrackingSystemComment {

    private static final long serialVersionUID = 1L;

    private String url;
    private String updateAuthor;
    private Date updateDate;

    public String getUrl() {
        return url;
    }

    public String getUpdateAuthor() {
        return updateAuthor;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUpdateAuthor(String updateAuthor) {
        this.updateAuthor = updateAuthor;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
