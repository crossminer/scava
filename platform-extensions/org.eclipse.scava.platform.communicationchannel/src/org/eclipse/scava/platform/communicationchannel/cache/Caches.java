/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.cache;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.scava.repository.model.CommunicationChannel;

public class Caches<T, K> {
    private Map<String, Cache<T, K>> map = new HashMap<String, Cache<T, K>>();
    private CacheProvider<T, K> provider;

    public Caches(CacheProvider<T, K> provider) {
        if (null == provider) {
            throw new IllegalArgumentException("provider cannot be null");
        }

        this.provider = provider;
    }

    public Cache<T, K> getCache(CommunicationChannel communicationChannel,
            boolean createIfNotExists) {
        String id = communicationChannel.getUrl();

        Cache<T, K> cache = null;

        cache = map.get(id);

        if (null == cache && createIfNotExists) {
            cache = new Cache<T, K>(communicationChannel, provider);
            map.put(id, cache);
        }
        return cache;
    }
}
