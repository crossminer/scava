/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.other.featuresextractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFeaturesExtractor
{
	
	public static int findPattern(Pattern pattern, String text)
	{
		int counter=0;
		Matcher m = pattern.matcher(text); 
		while(m.find())
			counter++;
		return counter;
	}
	

	
	//Token-based features
	
	public static int findPattern(Pattern pattern, String[] textSplit)
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
}
