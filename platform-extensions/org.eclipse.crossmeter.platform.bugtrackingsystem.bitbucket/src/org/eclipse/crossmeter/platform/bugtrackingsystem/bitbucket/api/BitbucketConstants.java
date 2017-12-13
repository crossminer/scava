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
package org.eclipse.crossmeter.platform.bugtrackingsystem.bitbucket.api;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class BitbucketConstants {
	public static int DEFAULT_PAGE_SIZE = 50;
	public static int MAX_PAGE_SIZE = 50;
	
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
			.forPattern("YYYY-MM-dd HH:mm:ssZ");
}
