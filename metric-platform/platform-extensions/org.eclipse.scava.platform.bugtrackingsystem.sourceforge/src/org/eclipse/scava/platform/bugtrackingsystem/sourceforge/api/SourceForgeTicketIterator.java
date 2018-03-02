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

import java.util.Iterator;

 public class SourceForgeTicketIterator implements
            Iterator<SourceForgeTicket> {
    	
    	 private final SourceForgeTrackerRestClient sourceforge;
    	    private final Iterator<Integer> ticketIds;
    	
    	public SourceForgeTicketIterator(SourceForgeTrackerRestClient sourceforge,
                Iterator<Integer> ticketIds) {
            this.sourceforge = sourceforge;
            this.ticketIds = ticketIds;
        }
    	
        @Override
        public boolean hasNext() {
            return ticketIds.hasNext();
        }

        @Override
        public SourceForgeTicket next() {
            try {
                return sourceforge.getTicket(ticketIds.next());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

