/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.plaintext.documentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.plaintext.PlainTextObject;
import org.eclipse.scava.nlp.tools.preprocessor.htmlparser.HtmlParser;
import org.eclipse.scava.nlp.tools.preprocessor.markdown.MarkdownParser;

public class PlainTextDocumentationMarkdownBased
{
	private static Pattern newlines;
	
	static
	{
		newlines=Pattern.compile("\\\\r\\\\n");
	}
	
	public static PlainTextObject process(String text)
	{
		if(text==null)
			return new PlainTextObject(new ArrayList<String>(Arrays.asList("")));
		text=newlines.matcher(text).replaceAll("\n");
		text= MarkdownParser.parse(text);
		return new PlainTextObject(HtmlParser.parse(text));
	}

}
