/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.crossmeter.platform.cache.communicationchannel;
import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;


public interface ICommunicationChannelDeltaCache {
	
	public CommunicationChannelDelta getCachedDelta(String communicationChannelUrl, Date date);
	
	public void putDelta(String communicationChannelUrl, Date date, CommunicationChannelDelta delta);
}
