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
package org.eclipse.crossmeter.platform.bugtrackingsystem.cache;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.crossmeter.repository.model.BugTrackingSystem;

public class Caches<T, K> {
    private Map<String, Cache<T, K>> map = new HashMap<String, Cache<T, K>>();
    private CacheProvider<T, K> provider;

    public Caches(CacheProvider<T, K> provider) {
        if (null == provider) {
            throw new IllegalArgumentException("provider cannot be null");
        }

        this.provider = provider;
    }

    public Cache<T, K> getCache(BugTrackingSystem bugTracker,
            boolean createIfNotExists) {
        String id = bugTracker.getOSSMeterId();

        Cache<T, K> cache = null;

        cache = map.get(id);

        if (null == cache && createIfNotExists) {
            cache = new Cache<T, K>(bugTracker, provider);
            map.put(id, cache);
        }

        return cache;
    }
}
