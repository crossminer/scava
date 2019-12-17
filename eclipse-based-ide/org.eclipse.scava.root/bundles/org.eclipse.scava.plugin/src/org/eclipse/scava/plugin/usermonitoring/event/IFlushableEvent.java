/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.event;

import java.util.List;

import org.eclipse.scava.plugin.usermonitoring.event.events.IEvent;

public interface IFlushableEvent<E extends IFlushableEvent<?>> extends IEvent{

	int getMaxBufferedLength();
	
	long getMaxTimeUntilEventInsertion();
	
	E aggregate(List<E> events);
	
	
}
