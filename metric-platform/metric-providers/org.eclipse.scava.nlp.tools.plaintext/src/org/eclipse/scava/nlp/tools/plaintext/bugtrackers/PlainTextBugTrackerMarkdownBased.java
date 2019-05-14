/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.plaintext.bugtrackers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.plaintext.PlainTextObject;
import org.eclipse.scava.nlp.tools.plaintext.utils.IntermadiatePlainTextObject;
import org.eclipse.scava.nlp.tools.plaintext.utils.ReplyLineDetection;
import org.eclipse.scava.nlp.tools.preprocessor.htmlparser.HtmlParser;
import org.eclipse.scava.nlp.tools.preprocessor.markdown.MarkdownParser;

public class PlainTextBugTrackerMarkdownBased
{
	private static Pattern newlines;
	private static Pattern escapedNewline;
	
	static
	{
		newlines=Pattern.compile("\\\\r\\\\n");
		escapedNewline = Pattern.compile("(\\\\n|\\\\r)");
	}
	
	public static PlainTextObject process(String text)
	{
		if(text==null)
			return new PlainTextObject(new ArrayList<String>(Arrays.asList("")));
		text=newlines.matcher(text).replaceAll("\n");
		IntermadiatePlainTextObject intermadiatePlainTextObject= ReplyLineDetection.process(text);
		text= MarkdownParser.parse(intermadiatePlainTextObject.getPlainText());
		//In case the text contain escaped newlines
		text=escapedNewline.matcher(text).replaceAll("");
		return new PlainTextObject(HtmlParser.parse(text), intermadiatePlainTextObject.hadReplies());
	}

}
