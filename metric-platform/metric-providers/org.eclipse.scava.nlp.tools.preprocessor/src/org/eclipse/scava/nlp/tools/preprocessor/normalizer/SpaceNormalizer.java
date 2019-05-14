/*******************************************************************************
 * Copyright (C) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.preprocessor.normalizer;

import java.util.regex.Pattern;

class SpaceNormalizer
{
	Pattern tabs;
	Pattern multSpaces;
	Pattern startSpaces;
	Pattern middleSpaces;
	Pattern endSpaces;
	Pattern newLines;
	Pattern emptyLine;
	Pattern endNewline;
	
	public SpaceNormalizer()
	{
		newLines = Pattern.compile("\\v+");
		tabs = Pattern.compile("\\t+");
		multSpaces = Pattern.compile("\\h+");
		startSpaces = Pattern.compile("(?m)^ ");
		emptyLine=Pattern.compile("(?m)^$\n");
		endSpaces=Pattern.compile("(?m) $");
		endNewline=Pattern.compile("\n$");
	}
	
	public String apply (String text)
	{
		text=newLines.matcher(text).replaceAll("\n");
		text=tabs.matcher(text).replaceAll(" ");
		text=multSpaces.matcher(text).replaceAll(" ");
		text=startSpaces.matcher(text).replaceAll("");
		//text=middleSpaces.matcher(text).replaceAll("\n");
		text=emptyLine.matcher(text).replaceAll("");
		text=endSpaces.matcher(text).replaceAll("");
		text=endNewline.matcher(text).replaceAll("");
		return(text);
	}
}
