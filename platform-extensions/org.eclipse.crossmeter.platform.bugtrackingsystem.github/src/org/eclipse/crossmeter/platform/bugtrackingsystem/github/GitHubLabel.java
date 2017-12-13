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
