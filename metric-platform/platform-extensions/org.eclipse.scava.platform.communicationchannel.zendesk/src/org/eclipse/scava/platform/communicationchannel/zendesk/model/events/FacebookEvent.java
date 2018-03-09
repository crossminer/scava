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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * @author stephenc
 * @since 05/04/2013 11:57
 */
public class FacebookEvent extends Event {
    private Map<String, Object> page;
    private Long communication;
    private String ticketVia;
    private String body;

    public Long getCommunication() {
        return communication;
    }

    public void setCommunication(Long communication) {
        this.communication = communication;
    }

    public Map<String, Object> getPage() {
        return page;
    }

    public void setPage(Map<String, Object> page) {
        this.page = page;
    }

    @JsonProperty("ticket_via")
    public String getTicketVia() {
        return ticketVia;
    }

    public void setTicketVia(String ticketVia) {
        this.ticketVia = ticketVia;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("FacebookEvent");
        sb.append("{body='").append(body).append('\'');
        sb.append(", page=").append(page);
        sb.append(", communication=").append(communication);
        sb.append(", ticketVia='").append(ticketVia).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
