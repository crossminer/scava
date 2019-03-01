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

class SymbolNormalizer
{
	Pattern singleQuotes;
	Pattern doubleQuotes;
	Pattern newLine;
	
	public SymbolNormalizer()
	{
		singleQuotes = Pattern.compile("‘|’|′|΄|❜|'|`");
		doubleQuotes = Pattern.compile("”|“|„|″|‶|\"");
		newLine = Pattern.compile("\r");
	}
	
	public String apply(String text)
	{
		text=singleQuotes.matcher(text).replaceAll("'");
		text=doubleQuotes.matcher(text).replaceAll("\"");
		text=newLine.matcher(text).replaceAll("\n");
		return text;
	}
}
