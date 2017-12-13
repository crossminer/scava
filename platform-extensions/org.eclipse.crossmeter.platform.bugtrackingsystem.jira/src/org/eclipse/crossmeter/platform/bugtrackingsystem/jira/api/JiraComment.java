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
package org.eclipse.crossmeter.platform.bugtrackingsystem.jira.api;

import java.util.Date;

import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

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
