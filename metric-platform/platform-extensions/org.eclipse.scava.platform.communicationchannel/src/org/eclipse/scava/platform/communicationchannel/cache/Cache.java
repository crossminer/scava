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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.scava.platform.communicationchannel.cache.provider.BasicCacheProvider;
import org.eclipse.scava.platform.communicationchannel.cache.provider.DateRangeCacheProvider;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.joda.time.Interval;

public class Cache<T, K> {

	protected CacheProvider<T, K> provider;

	// TODO Could move to Google Guava Caches, if we want a smarter strategy to
	// conserve memory. For example, maybe purge cache after 24 hours?
	private Date latestDate;
	private Date earliestDate;
	private int minUpdateIntervalMillis = 3600000;
	private CommunicationChannel communicationChannel;
	private Map<K, T> cache = new HashMap<K, T>();

	public Cache(CommunicationChannel communicationChannel, CacheProvider<T, K> provider) {
		this.communicationChannel = communicationChannel;
		this.provider = provider;
	}

	public Cache(CommunicationChannel communicationChannel, CacheProvider<T, K> provider,
			int minUpdateIntervalSecs) {
		this.communicationChannel = communicationChannel;
		this.provider = provider;
		this.minUpdateIntervalMillis = minUpdateIntervalSecs * 1000;
	}

	public Iterable<T> getItems() throws Exception {
		update(Calendar.getInstance().getTime());
		return new Items(null, true, true);
	}

	public Iterable<T> getItemsOnDate(Date date) throws Exception {
		update(date);
		return new Items(date, false, false);
	}

	public Iterable<T> getItemsAfterDate(Date date) throws Exception {
		update(date);
		return new Items(date, false, true);
	}

	private void update(Date date) throws Exception {
		Date now = Calendar.getInstance().getTime();

		if (provider instanceof DateRangeCacheProvider<?, ?>) {

			DateRangeCacheProvider<T, K> provider = (DateRangeCacheProvider<T, K>) this.provider;

			// By passing in 'null' as the value to 'after' in
			// provider.getItems() we will possibly reduce the number of web
			// service calls required.
			if (null == latestDate) {
				Iterator<T> items = provider.getItems(date, null, communicationChannel);
				cacheItems(items);
				earliestDate = date;
				latestDate = now;
			} else if (new Interval(latestDate.getTime(), now.getTime())
					.toDurationMillis() > minUpdateIntervalMillis) {
				Iterator<T> items = provider.getItems(latestDate, null,
						communicationChannel);
				cacheItems(items);
				latestDate = now;
			}

			if (date.before(earliestDate)) {
				Iterator<T> items = provider.getItems(date, earliestDate,
						communicationChannel);
				cacheItems(items);
				earliestDate = date;
			}
		} else {
			BasicCacheProvider<T, K> provider = (BasicCacheProvider<T, K>) this.provider;

			if (null == latestDate) {
				Iterator<T> items = provider.getItems(communicationChannel);
				cacheItems(items);
				earliestDate = date;
				latestDate = now;
			} else if (new Interval(latestDate.getTime(), now.getTime())
					.toDurationMillis() > minUpdateIntervalMillis) {
				Iterator<T> items = provider.getItems(communicationChannel);
				cacheItems(items);
				latestDate = now;
			}

		}
	}
	
	private void cacheItems(Iterator<T> itemsIterator) {
		while (itemsIterator.hasNext()) {
			// Will automatically overwrite any existing representation of the
			// item, so we'll always have the latest version
			T item = itemsIterator.next();
			provider.process(item, communicationChannel);
			cache.put(provider.getKey(item), item);
		}
	}

	private class Items implements Iterable<T> {
		private Date date;
		private boolean itemsAfterDate;
		private boolean returnAll;
		

		public Items(Date date, boolean returnAll, boolean itemsAfterDate) {
			this.date = date;
			this.itemsAfterDate = itemsAfterDate;
			this.returnAll = returnAll;
		}

		@Override
		public Iterator<T> iterator() {
			return new ItemsIterator(cache.values().iterator());
		}

		private class ItemsIterator implements Iterator<T> {
			private T next;
			private Iterator<T> iterator;

			public ItemsIterator(Iterator<T> iterator) {
				this.iterator = iterator;
			}

			@Override
			public boolean hasNext() {
				if (returnAll) {
					while (iterator.hasNext()) {
						next = iterator.next();
						return true;
					}
				} else { 
					if (itemsAfterDate) {
						while (iterator.hasNext()) {
							next = iterator.next();
							if (provider.changedSinceDate(next, date, communicationChannel)) {
								return true;
							}
						}
					} else {
						while (iterator.hasNext()) {
							next = iterator.next();
							if (provider.changedOnDate(next, date, communicationChannel)) {
								return true;
							}
						}
					}
				}

				return false;
			}

			@Override
			public T next() {
				return next;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		}
	}

}
