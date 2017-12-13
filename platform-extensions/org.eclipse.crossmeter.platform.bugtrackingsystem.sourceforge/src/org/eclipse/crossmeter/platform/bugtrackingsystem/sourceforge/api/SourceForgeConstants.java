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
package org.eclipse.crossmeter.platform.bugtrackingsystem.sourceforge.api;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.ISODateTimeFormat;

class SourceForgeConstants {
    public static final int DEFAULT_PAGE_SIZE = 100;

    public static final DateTimeFormatter REQUEST_DATE_FORMATTER = ISODateTimeFormat
            .dateTime();

    public static final DateTimeFormatter RESPONSE_DATE_FORMATTER;

    static {

        final DateTimeParser optional = new DateTimeFormatterBuilder()
                .appendLiteral('.').appendFractionOfSecond(1, 6).toParser();

        RESPONSE_DATE_FORMATTER = new DateTimeFormatterBuilder()
                .appendYear(4, 4).appendLiteral('-').appendMonthOfYear(2)
                .appendLiteral('-').appendDayOfMonth(2).appendLiteral(' ')
                .appendHourOfDay(2).appendLiteral(':').appendMinuteOfHour(2)
                .appendLiteral(':').appendSecondOfMinute(2)
                .appendOptional(optional).toFormatter();
    }

    public static final String LAST_MODIFIED_QUERY_FIELD = "mod_date_dt";
    
    public static final String CREATED_DATE_QUERY_FIELD = "created_date";
}
