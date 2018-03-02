/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.delta.bugtrackingsystem;

import java.text.ParseException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Temp {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		Date d1 = new Date("03/30/2012");
		System.out.println(d1 + "\t" + d1.getDate());
		Date d2 = new Date("03/30/2012 00:27:00");
		System.out.println(d2 + "\t" + d2.getDate());
		Date d3 = new Date("03/30/2012 04:27:23");
		System.out.println(d3 + "\t" + d3.getDate());
		System.out.println(d1.compareTo(d2));
		System.out.println(d1.compareTo(d3));
		System.out.println(d1.equals(d2));
		System.out.println(d1.equals(d3));
		System.out.println(d1.getDate() == d2.getDate());
		System.out.println(d1.compareTo(d3));
		org.eclipse.scava.platform.Date dd1 = new org.eclipse.scava.platform.Date(d1);
		org.eclipse.scava.platform.Date dd2 = new org.eclipse.scava.platform.Date(d2);
		org.eclipse.scava.platform.Date dd3 = new org.eclipse.scava.platform.Date(d3);
		System.out.println(dd1 + "\t" + dd1.toJavaDate());
		System.out.println(dd1.compareTo(d1));
		System.out.println(dd1.compareTo(d2));
		System.out.println(dd1.compareTo(d3));
		System.out.println(dd2);
		System.out.println(dd1.compareTo(dd2));
		System.out.println(dd3);
		System.out.println(dd1.compareTo(dd3));
		System.out.println(dd1.toJavaDate().getYear() + "/"
							+ dd1.toJavaDate().getMonth() + "/" 
								+ dd1.toJavaDate().getDay()+ " " + 
									+ dd1.toJavaDate().getHours() + ":" +
										+ dd1.toJavaDate().getMinutes() + ":" +
											+ dd1.toJavaDate().getSeconds());
		Calendar cal = new GregorianCalendar();
		cal.setTime(d3);
		System.out.println(cal.get(Calendar.YEAR) + "/"
				+ cal.get(Calendar.MONTH) + "/" 
					+ cal.get(Calendar.DAY_OF_MONTH)+ " " + 
						+ cal.get(Calendar.HOUR) + ":" +
							+ cal.get(Calendar.MINUTE) + ":" +
								+ cal.get(Calendar.SECOND));
	}

}
