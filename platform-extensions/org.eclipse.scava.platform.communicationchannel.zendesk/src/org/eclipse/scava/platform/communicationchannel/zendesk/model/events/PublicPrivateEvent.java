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
 * @since 05/04/2013 11:53
 */
abstract class PublicPrivateEvent extends Event {
    private Boolean publicComment;

    public Boolean getPublic() {
        return publicComment;
    }

    public void setPublic(Boolean publicComment) {
        this.publicComment = publicComment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PublicPrivateEvent");
        sb.append("{publicComment=").append(publicComment);
        sb.append('}');
        return sb.toString();
    }
}
