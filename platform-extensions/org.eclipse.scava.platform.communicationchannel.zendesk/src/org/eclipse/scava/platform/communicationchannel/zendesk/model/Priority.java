/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.zendesk.model;

/**
 * @author stephenc
 * @since 05/04/2013 08:57
 */
public enum Priority {
    URGENT,
    HIGH,
    NORMAL,
    LOW;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
