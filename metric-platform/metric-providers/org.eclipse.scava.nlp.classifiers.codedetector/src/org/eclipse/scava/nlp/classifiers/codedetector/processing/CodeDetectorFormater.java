/*******************************************************************************
 * Copyright (C) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.classifiers.codedetector.processing;

import java.util.Locale;
import java.util.regex.Pattern;

public class CodeDetectorFormater
{
	private static Pattern comma;
	private static Pattern spacedChars;
	private static Pattern label;
	private static Pattern dot;
	private static Pattern special;
	private static Pattern newline;
	private static Pattern spaces;
	private static Pattern spacesStart;
	private static Pattern spacesEnd;
	private static String[] tokens;
	
	static
	{
		special= Pattern.compile("</s>");
		comma = Pattern.compile(",");
		spacedChars = Pattern.compile("(;|\\(|\\)|\\]|\\[|\\!|\\?|:|'|\")");
		label=Pattern.compile("__label__");
		dot=Pattern.compile("(?=[^ ])\\.( |$)");
		newline=Pattern.compile("\\v+");
		spaces=Pattern.compile("\\h+");
		spacesStart=Pattern.compile("^ ");
		spacesEnd=Pattern.compile(" $");
	}
	
	public static String apply (String text)
	{
		//Keep this order order
		text=text.toLowerCase(Locale.ENGLISH);
		
		text=special.matcher(text).replaceAll("<\\/s>");
		text=comma.matcher(text).replaceAll(" ");
		text=spacedChars.matcher(text).replaceAll(" $1 ");
		text=label.matcher(text).replaceAll(" __\\label__");
		text=dot.matcher(text).replaceAll(" . ");
		text=newline.matcher(text).replaceAll(" ");
		text=spaces.matcher(text).replaceAll(" ");
		//The next part is to check the length of the tokens
		double textLength=text.length();
		if(textLength>500)
		{
			tokens=text.split("\\h+");
			double tokensNumber= tokens.length;
			//The next formula gives a rough approximation of the length of the tokens
			//If the proportion is too large (>20) so it might exist a token very large
			//that the code detector might not be able to analyze
			double proportionTokensText=(textLength-tokensNumber+1.0)/tokensNumber;
			if(proportionTokensText>20.0)
			{
				text="";
				for(String token : tokens)
				{
					if(token.length() > 500)
					{
						//We do a substring to leave at least a part of the original information
						//Although it might not exist in the Code Detector model
						token=token.substring(0,500); 
					}
					text=text.concat(token+" ");				
				}
			}
		}
		text=spacesStart.matcher(text).replaceAll("");
		text=spacesEnd.matcher(text).replaceAll("");
		return(text);
	}
}
