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

/**
 * @author stephenc
 * @since 04/04/2013 14:24
 */
public class ZendeskException extends RuntimeException {
    public ZendeskException(String message) {
        super(message);
    }

    public ZendeskException() {
    }

    public ZendeskException(Throwable cause) {
        super(cause);
    }

    public ZendeskException(String message, Throwable cause) {
        super(message, cause);
    }
}
