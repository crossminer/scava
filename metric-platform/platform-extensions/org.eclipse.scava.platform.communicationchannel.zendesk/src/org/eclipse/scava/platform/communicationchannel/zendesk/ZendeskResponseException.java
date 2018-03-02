/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.zendesk;

import com.ning.http.client.Response;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * {@link ZendeskException} specialisation for HTTP non-2xx responses
 */
public class ZendeskResponseException extends ZendeskException {
    private int statusCode;
    private String statusText;
    private String body;

    public ZendeskResponseException(Response resp) throws IOException {
        this(resp.getStatusCode(), resp.getStatusText(), resp.getResponseBody());
    }

    public ZendeskResponseException(int statusCode, String statusText, String body) {
        super(MessageFormat.format("HTTP/{0}: {1}", statusCode, statusText));
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getBody() {
        return body;
    }
}
