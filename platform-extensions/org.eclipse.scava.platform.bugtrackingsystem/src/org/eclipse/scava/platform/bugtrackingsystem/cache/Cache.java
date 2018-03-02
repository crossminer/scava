/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.cache;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.scava.platform.bugtrackingsystem.cache.provider.BasicCacheProvider;
import org.eclipse.scava.platform.bugtrackingsystem.cache.provider.DateRangeCacheProvider;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.joda.time.Interval;

public class Cache<T, K> {

	protected CacheProvider<T, K> provider;

	// TODO Could move to Google Guava Caches, if we want a smarter strategy to
	// conserve memory. For example, maybe purge cache after 24 hours?
	private Date latestDate;
	private Date earliestDate;
	private int minUpdateIntervalMillis = 3600000;
	private BugTrackingSystem bugTracker;
	private Map<K, T> cache = new HashMap<K, T>();

	public Cache(BugTrackingSystem bugTracker, CacheProvider<T, K> provider) {
		this.bugTracker = bugTracker;
		this.provider = provider;
	}

	public Cache(BugTrackingSystem bugTracker, CacheProvider<T, K> provider,
			int minUpdateIntervalSecs) {
		this.bugTracker = bugTracker;
		this.provider = provider;
		this.minUpdateIntervalMillis = minUpdateIntervalSecs * 1000;
	}

	public Iterable<T> getItemsOnDate(Date date) throws Exception {
		update(date);
		return new Items(date, false);
	}

	public Iterable<T> getItemsAfterDate(Date date) throws Exception {
		update(date);
		return new Items(date, true);
	}

	private void update(Date date) throws Exception {
		Date now = Calendar.getInstance().getTime();

		if (provider instanceof DateRangeCacheProvider<?, ?>) {

			DateRangeCacheProvider<T, K> provider = (DateRangeCacheProvider<T, K>) this.provider;

			// By passing in 'null' as the value to 'after' in
			// provider.getItems() we will possibly reduce the number of web
			// service calls required.
			if (null == latestDate) {
				Iterator<T> items = provider.getItems(date, null, bugTracker);
				cacheItems(items);
				earliestDate = date;
				latestDate = now;
			} else if (new Interval(latestDate.getTime(), now.getTime())
					.toDurationMillis() > minUpdateIntervalMillis) {
				Iterator<T> items = provider.getItems(latestDate, null,
						bugTracker);
				cacheItems(items);
				latestDate = now;
			}

			if (date.before(earliestDate)) {
				Iterator<T> items = provider.getItems(date, earliestDate,
						bugTracker);
				cacheItems(items);
				earliestDate = date;
			}
		} else {
			BasicCacheProvider<T, K> provider = (BasicCacheProvider<T, K>) this.provider;

			if (null == latestDate) {
				Iterator<T> items = provider.getItems(bugTracker);
				cacheItems(items);
				earliestDate = date;
				latestDate = now;
			} else if (new Interval(latestDate.getTime(), now.getTime())
					.toDurationMillis() > minUpdateIntervalMillis) {
				Iterator<T> items = provider.getItems(bugTracker);
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
			provider.process(item, bugTracker);
			cache.put(provider.getKey(item), item);
		}
	}

	private class Items implements Iterable<T> {
		private Date date;
		private boolean itemsAfterDate;

		public Items(Date date, boolean itemsAfterDate) {
			this.date = date;
			this.itemsAfterDate = itemsAfterDate;
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
				if (itemsAfterDate) {
					while (iterator.hasNext()) {
						next = iterator.next();
						if (provider.changedSinceDate(next, date, bugTracker)) {
							return true;
						}
					}
				} else {
					while (iterator.hasNext()) {
						next = iterator.next();
						if (provider.changedOnDate(next, date, bugTracker)) {
							return true;
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
