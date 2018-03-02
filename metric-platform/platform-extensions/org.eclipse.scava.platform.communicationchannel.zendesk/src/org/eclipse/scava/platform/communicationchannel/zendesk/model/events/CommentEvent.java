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

import org.eclipse.scava.platform.communicationchannel.zendesk.model.Attachment;

import java.util.List;

/**
 * @author stephenc
 * @since 05/04/2013 11:54
 */
public class CommentEvent extends PublicPrivateEvent {
    private String body;
    private String htmlBody;
    private Boolean trusted;
    private Long authorId;
    private List<Attachment> attachments;

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @JsonProperty("author_id")
    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @JsonProperty("html_body")
    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public boolean getTrusted() {
        return trusted;
    }

    public void setTrusted(Boolean trusted) {
        this.trusted = trusted;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CommentEvent");
        sb.append("{attachments=").append(attachments);
        sb.append(", body='").append(body).append('\'');
        sb.append(", htmlBody='").append(htmlBody).append('\'');
        sb.append(", trusted=").append(trusted);
        sb.append(", authorId=").append(authorId);
        sb.append('}');
        return sb.toString();
    }
}
