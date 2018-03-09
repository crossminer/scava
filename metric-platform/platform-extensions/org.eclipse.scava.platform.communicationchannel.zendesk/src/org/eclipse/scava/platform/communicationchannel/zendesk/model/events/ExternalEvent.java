/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.zendesk.model.events;

/**
 * @author stephenc
 * @since 05/04/2013 11:57
 */
public class ExternalEvent extends Event {
    private String resource;
    private String body;
    private String success;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ExternalEvent");
        sb.append("{body='").append(body).append('\'');
        sb.append(", resource='").append(resource).append('\'');
        sb.append(", success='").append(success).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
