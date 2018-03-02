/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.tests;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.eclipse.scava.platform.Date;
import org.junit.Test;

public class TestDate {

	@Test
	public void testToString() throws ParseException {
		Date date = new Date("19860812");
		assertEquals("19860812", date.toString());
	}
	
	@Test
	public void testAddDays() throws ParseException {
		Date date = new Date("19860812");
		date.addDays(1);
		assertEquals("19860813", date.toString());

		date.addDays(1);
		assertEquals("19860814", date.toString());

		date.addDays(365);
		assertEquals("19870814", date.toString());
	}
	
	@Test
	public void testCompare() throws ParseException {
		Date jBirthday = new Date("19860812");
		Date j18thBirthday = new Date("20020812");
		
		assertEquals(-1, jBirthday.compareTo(j18thBirthday.toJavaDate()));
		assertEquals(-1, jBirthday.compareTo(j18thBirthday));
		
		assertEquals(1, j18thBirthday.compareTo(jBirthday));
		assertEquals(1, j18thBirthday.compareTo(jBirthday.toJavaDate()));
		
		assertEquals(0, jBirthday.compareTo(jBirthday));
		assertEquals(0, jBirthday.compareTo(jBirthday.toJavaDate()));
	
		java.util.Date today = new java.util.Date(); 
		Date jToday = new Date(today);
		Date sToday = new Date(jToday.toString());
		assertEquals(0, jToday.compareTo(sToday));
	}
	
	@Test
	public void testEquals() throws ParseException {
		Date jBirthday = new Date("19860812");
		Date jBirthday2 = new Date("19860812");
		
		assertEquals(jBirthday, jBirthday2);
	}

	@Test
	public void testRange() throws Exception {
		Date d1 = new Date("19860812");
		Date d2 = new Date("19860813");
		
		assertEquals(1, Date.range(d1, d1).length);
		assertEquals(2, Date.range(d1, d2).length);
		
		Date[] dates = Date.range(d1, d2);
		assertEquals("19860812", dates[0].toString());
		assertEquals("19860813", dates[1].toString());
		
		dates = Date.range(d1, d1.addDays(-1));
		assertEquals(0, dates.length);
	}
	
}
