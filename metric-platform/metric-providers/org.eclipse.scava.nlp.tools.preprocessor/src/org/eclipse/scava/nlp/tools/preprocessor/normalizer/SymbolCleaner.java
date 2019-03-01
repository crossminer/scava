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

class SymbolCleaner
{
	Pattern privateSymbols;
	Pattern otherSymbols;
	Pattern otherLetters;
	Pattern otherNumbers;
	Pattern subrrogate;
	
	public SymbolCleaner()
	{
		privateSymbols = Pattern.compile("\\p{Co}");
		otherSymbols = Pattern.compile("\\p{So}");
		otherLetters=Pattern.compile("\\p{Lo}");
		otherNumbers=Pattern.compile("\\p{No}");
		subrrogate=Pattern.compile("\\p{Cs}");
	}
	
	public String apply (String text)
	{
		text=privateSymbols.matcher(text).replaceAll("");
		text=otherSymbols.matcher(text).replaceAll("");
		text=otherLetters.matcher(text).replaceAll("");
		text=otherNumbers.matcher(text).replaceAll("");
		text=subrrogate.matcher(text).replaceAll("");
		return(text);
	}
}
