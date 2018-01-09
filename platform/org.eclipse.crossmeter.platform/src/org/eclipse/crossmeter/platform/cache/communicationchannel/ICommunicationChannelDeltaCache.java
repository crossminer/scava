/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform.cache.communicationchannel;
import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;


public interface ICommunicationChannelDeltaCache {
	
	public CommunicationChannelDelta getCachedDelta(String communicationChannelUrl, Date date);
	
	public void putDelta(String communicationChannelUrl, Date date, CommunicationChannelDelta delta);
}
