/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.requestreply.processing;

import java.util.Locale;
import java.util.regex.Pattern;

class TextPostProcessor
{
	private static Pattern spaces;
	private static Pattern spacesStart;
	private static Pattern spacesEnd;
	private static Pattern numbers;
	
	static
	{
		numbers=Pattern.compile("\\b\\d+((\\.|,)\\d+)*\\b");
		spaces=Pattern.compile("\\h+");
		spacesStart=Pattern.compile("^ ");
		spacesEnd=Pattern.compile(" $");
	}
	
	public static String apply (String text)
	{
		//Keep this order order
		text=text.toLowerCase(Locale.ENGLISH);
		text=numbers.matcher(text).replaceAll(" ");
		text=spaces.matcher(text).replaceAll(" ");
		text=spacesStart.matcher(text).replaceAll("");
		text=spacesEnd.matcher(text).replaceAll("");
		return(text);
	}
}
