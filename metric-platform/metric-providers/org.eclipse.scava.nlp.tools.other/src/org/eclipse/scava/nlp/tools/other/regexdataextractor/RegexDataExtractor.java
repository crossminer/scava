/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.other.regexdataextractor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDataExtractor
{
	
	public static int countPattern(Pattern pattern, String text)
	{
		int counter=0;
		Matcher m = pattern.matcher(text); 
		while(m.find())
			counter++;
		return counter;
	}
	
	//Token-based features
	
	public static int countTokenPattern(Pattern pattern, String[] textSplit)
	{
		int counter=0;
		Matcher m;
		for(String token : textSplit)
		{
			m = pattern.matcher(token);
			while(m.find())
				counter++;
		}
		return counter;
	}
	
	public static List<List<String>> capturePatterns(Pattern pattern, String text)
	{
		List<List<String>> capturedPatterns = new ArrayList<List<String>>(1);
		Matcher m = pattern.matcher(text); 
		while(m.find())
		{
			List<String> capturedPattern = new ArrayList<String>(m.groupCount());
			for(int i=1; i<=m.groupCount(); i++)
			{
				capturedPattern.add(m.group(i));
			}
			capturedPatterns.add(capturedPattern);
		}
		return capturedPatterns;
	}
}
