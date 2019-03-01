/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.eclipseforums.utils;

import org.eclipse.scava.platform.Date;

public class EclipseForumUtils {

	public static Date convertStringToDate(String timestamp) {

		Long unixTimestamp = Long.parseLong(timestamp);
		Date platformDate = new Date(unixTimestamp*1000);
		
		return platformDate;
	}

	/**
	 * 
	 * Removes " (quotation marks from the first and last index of the string)
	 * 
	 * @param string
	 * @return fixedString
	 */
	public static String fixString(String string) {
		String fixedString = string.replaceAll("\"", "").replace("[", "").replace("]", "");
		return fixedString;
	}
}
