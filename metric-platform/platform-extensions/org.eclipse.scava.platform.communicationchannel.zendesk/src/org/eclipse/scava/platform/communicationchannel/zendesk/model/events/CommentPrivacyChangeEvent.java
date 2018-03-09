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

/**
 * @author stephenc
 * @since 05/04/2013 11:55
 */
public class CommentPrivacyChangeEvent extends PublicPrivateEvent {
    private Long commentId;

    @JsonProperty("comment_id")
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CommentPrivacyChangeEvent");
        sb.append("{commentId=").append(commentId);
        sb.append('}');
        return sb.toString();
    }
}
