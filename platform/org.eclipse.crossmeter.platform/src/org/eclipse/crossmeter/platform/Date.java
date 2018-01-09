/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Date {
	
	protected java.util.Date _date;
	
	public Date() {
		_date = new java.util.Date();
	}
	
	/**
	 * 
	 * @param date YYYMMDD
	 * @throws ParseException
	 */
	public Date(String date) throws ParseException {
		_date = parseDate(date);
	}
	
	private SimpleDateFormat[] sdfList = new SimpleDateFormat[]{
		// YYYYMMDD
		new SimpleDateFormat("yyyyMMdd"),
		new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz"),
		new SimpleDateFormat("dd MMM yyyy HH:mm:ss zzz"),
		new SimpleDateFormat("EEE, dd MMM yyyy HH:mm zzz (Z)"),
		new SimpleDateFormat("EEE, dd MMM yyyy HH:mm zzz"),
		new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss"),
		new SimpleDateFormat("dd MMM yyyy HH:mm:ss"),
		new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z"),
		new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy")
	};

	public java.util.Date parseDate(String dateString) throws ParseException {
		if (dateString.equals("null"))
			return sdfList[0].parse(dateString);
		for ( SimpleDateFormat formatter: sdfList) {
			ParsePosition ps = new ParsePosition(0);
			java.util.Date result = formatter.parse(dateString, ps);
			if (result != null)
				return processDate(result);
		}
		return null;
	}

	public Date(long epoch) {
		this(new java.util.Date(epoch));
	}
	
	public Date(java.util.Date date) {
		this._date = processDate(date);
	}

	private static java.util.Date processDate(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * @param days
	 * @return
	 */
	public Date addDays(int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(_date);
		c.add(Calendar.DATE, days);
		_date = c.getTime(); // necessary?
		return this;
	}
	
	public String toString() {
		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(_date);
	}
	
	public java.util.Date toJavaDate() {
		return _date;
	}
	
	/*
	 * @return >0 means 'date' is in the past; <0 means 'date' is in the future;
	 * ==0 means 'date' is the same.
	 */
	public int compareTo(java.util.Date date) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(_date);
		
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		
		return c1.compareTo(c2);
	}
	
	public int compareTo(Date date) {
		return compareTo(date.toJavaDate());
	}
	
	@Override
	public boolean equals(Object date) {
		if (date instanceof Date) {
			return date.toString().equals(this.toString());
		}
		return false;
	}
	
	/**
	 * @param start INCLUSIVE
	 * @param end INCLUSIVE
	 * @return
	 */
	public static Date[] range(Date start, Date end) {
		List<Date> dates = new ArrayList<Date>();
		
		Calendar c = Calendar.getInstance();
		c.setTime(start.toJavaDate());
		
		java.util.Date d = c.getTime();
		dates.add(new Date(d));
		
		while (d.before(end.toJavaDate())) {
			c.add(Calendar.DATE, 1);
			d = c.getTime();
			dates.add(new Date(d)); // Need to ensure this is returning a different object each time.
		}
		
		return dates.toArray(new Date[dates.size()]);
	}
	
	public static long duration(java.util.Date earlier, java.util.Date later) {
		
		if (earlier.compareTo(later) > 0)
			return duration(later, earlier);

        Calendar earlierCalendar = Calendar.getInstance();
        earlierCalendar.setTime(earlier);

        Calendar laterCalendar   = Calendar.getInstance();
        laterCalendar.setTime(later);

        return laterCalendar.getTimeInMillis() - earlierCalendar.getTimeInMillis();
	}
	
	public static long duration(Date earlier, Date later) {
		
		if (earlier.compareTo(later) > 0)
			return duration(later, earlier);
		
		return duration(earlier.toJavaDate(), later.toJavaDate());
	}
	
}
