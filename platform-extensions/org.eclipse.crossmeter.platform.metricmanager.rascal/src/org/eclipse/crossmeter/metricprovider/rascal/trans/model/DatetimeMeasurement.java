/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jurgen Vinju - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.rascal.trans.model;

import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DatetimeMeasurement extends Measurement {

	public DatetimeMeasurement() { 
		super();
		super.setSuperTypes("org.eclipse.crossmeter.metricprovider.rascal.trans.model.Measurement");
		URI.setOwningType("org.eclipse.crossmeter.metricprovider.rascal.trans.model.DatetimeMeasurement");
		VALUE.setOwningType("org.eclipse.crossmeter.metricprovider.rascal.trans.model.DatetimeMeasurement");
		TIME_ZONE_HOURS.setOwningType("org.eclipse.crossmeter.metricprovider.rascal.trans.model.DatetimeMeasurement");
		TIME_ZONE_MINS.setOwningType("org.eclipse.crossmeter.metricprovider.rascal.trans.model.DatetimeMeasurement");
	}
	
	private static final String VALUE_KEY = "value";
	private static final String TIME_ZONE_HOURS_KEY = "tz_hours";
	private static final String TIME_ZONE_MINS_KEY = "tz_mins";
	
	public static StringQueryProducer URI = new StringQueryProducer("uri"); 
	public static NumericalQueryProducer VALUE = new NumericalQueryProducer(VALUE_KEY);
	public static NumericalQueryProducer TIME_ZONE_HOURS = new NumericalQueryProducer(TIME_ZONE_HOURS_KEY);
	public static NumericalQueryProducer TIME_ZONE_MINS = new NumericalQueryProducer(TIME_ZONE_MINS_KEY);
	
	public long getValue() {
		return parseLong(dbObject.get(VALUE_KEY)+"", 0);
	}
	
	public int getTimezoneHours() {
		if (dbObject.containsField(TIME_ZONE_HOURS_KEY)) {
			return parseInteger(dbObject.get(TIME_ZONE_HOURS_KEY) + "", 0);
		}
		else {
			return 0;
		}
	}
	
	public int getTimezoneMinutes() {
		if (dbObject.containsField(TIME_ZONE_MINS_KEY)) {
			return parseInteger(dbObject.get(TIME_ZONE_MINS_KEY) + "", 0);
		}
		else {
			return 0;
		}
	}
	
	public DatetimeMeasurement setValue(long value, int timezoneHours, int timezoneMinutes) {
		dbObject.put(VALUE_KEY, value);
		dbObject.put(TIME_ZONE_HOURS_KEY, timezoneHours);
		dbObject.put(TIME_ZONE_MINS_KEY, timezoneMinutes);
		notifyChanged();
		return this;
	}	
}