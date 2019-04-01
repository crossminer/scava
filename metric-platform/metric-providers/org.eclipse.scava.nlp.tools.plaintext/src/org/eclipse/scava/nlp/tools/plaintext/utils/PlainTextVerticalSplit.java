/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.plaintext.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class PlainTextVerticalSplit
{
	private static Pattern verticalSpacing;
	
	static
	{
		verticalSpacing=Pattern.compile("\\v+");
	}

	public static List<String> process(String text)
	{
		return Arrays.asList(verticalSpacing.split(text));
	}

}
