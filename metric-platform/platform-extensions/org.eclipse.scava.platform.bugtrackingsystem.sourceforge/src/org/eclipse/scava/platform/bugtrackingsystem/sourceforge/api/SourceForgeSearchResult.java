/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.sourceforge.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SourceForgeSearchResult {
    private List<Integer> ticketIds = new ArrayList<Integer>();
    private int count;

    public void addTicketId(int ticketId) {
        ticketIds.add(ticketId);
    }

    public List<Integer> getTicketIds() {
        return Collections.unmodifiableList(ticketIds);
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
