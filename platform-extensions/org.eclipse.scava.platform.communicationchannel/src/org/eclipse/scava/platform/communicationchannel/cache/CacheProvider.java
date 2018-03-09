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

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.eclipse.scava.repository.model.CommunicationChannel;

public abstract class CacheProvider<T, K> {

	public abstract boolean changedOnDate(T item, Date date,
			CommunicationChannel communicationChannel);

	public abstract boolean changedSinceDate(T item, Date date,
			CommunicationChannel communicationChannel);

	public abstract K getKey(T item);

	public abstract void process(T item, CommunicationChannel communicationChannel);

	/**
	 * returns true if one of dates matches date.
	 * 
	 * @param date
	 * @param dates
	 * @return
	 */
	public static boolean findMatchOnDate(Date date, Date... dates) {
		for (Date d : dates) {
			if (null != d && DateUtils.isSameDay(date, d)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * returns true if one of dates occurred after date.
	 * 
	 * @param date
	 * @param dates
	 * @return
	 */
	public static boolean findMatchSinceDate(Date date, Date... dates) {
		for (Date d : dates) {
			if (null != d && d.after(date)) {
				return true;
			}
		}

		return false;
	}
}
