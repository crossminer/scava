/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.communicationchannel.cache.provider;

import java.util.Date;
import java.util.Iterator;

import org.eclipse.crossmeter.platform.communicationchannel.cache.CacheProvider;
import org.eclipse.crossmeter.repository.model.CommunicationChannel;

public abstract class DateRangeCacheProvider<T, K>  extends CacheProvider<T, K>{
	public abstract Iterator<T> getItems(Date after, Date before,
			CommunicationChannel communicationChannel) throws Exception;
}
