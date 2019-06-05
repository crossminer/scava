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

public class PlainTextDocumentationHtmlBased
{
	private static Pattern escapedNewline;
	
	static
	{
		escapedNewline = Pattern.compile("(\\\\n|\\\\r)");
	}
	
	public static PlainTextObject process(String text)
	{
		if(text==null)
			return new PlainTextObject(new ArrayList<String>(Arrays.asList("")));
		//In case the text contain escaped newlines
		text=escapedNewline.matcher(text).replaceAll("");
		return new PlainTextObject(HtmlParser.parse(text));
	}

}
