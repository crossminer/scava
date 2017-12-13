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
package org.eclipse.crossmeter.platform.bugtrackingsystem.sourceforge.api;

public class SourceForgeAttachment {
    private String url;
    private long bytes;
    
    public String getUrl() {
        return url;
    }
    
    public long getBytes() {
        return bytes;
    }
}
