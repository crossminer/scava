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
package org.eclipse.crossmeter.platform.bugtrackingsystem.cache.provider;

import java.util.Iterator;

import org.eclipse.crossmeter.platform.bugtrackingsystem.cache.CacheProvider;
import org.eclipse.crossmeter.repository.model.BugTrackingSystem;

public abstract class BasicCacheProvider<T, K> extends CacheProvider<T, K> {
	public abstract Iterator<T> getItems(BugTrackingSystem bugTracker) throws Exception;
}
