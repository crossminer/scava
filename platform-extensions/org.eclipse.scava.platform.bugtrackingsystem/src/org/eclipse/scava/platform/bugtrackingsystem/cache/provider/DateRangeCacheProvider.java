/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.cache.provider;

import java.util.Date;
import java.util.Iterator;

import org.eclipse.scava.platform.bugtrackingsystem.cache.CacheProvider;
import org.eclipse.scava.repository.model.BugTrackingSystem;

public abstract class DateRangeCacheProvider<T, K>  extends CacheProvider<T, K>{
	public abstract Iterator<T> getItems(Date after, Date before,
			BugTrackingSystem bugTracker) throws Exception;
}
