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

