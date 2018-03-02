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

import java.io.Serializable;

public class GitHubLabel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String colour;
    private String name;
    private String url;
        
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof GitHubLabel))
            return false;

        String name = this.name;
        return null != name && name.equals(((GitHubLabel) obj).name);
    }

    public int hashCode() {
         String name = this.name;
        return null != name ? name.hashCode() : super.hashCode();
    }
}
