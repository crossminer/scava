/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.plaintext.communicationchannels;

import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.plaintext.PlainTextObject;
import org.eclipse.scava.nlp.tools.plaintext.utils.IntermadiatePlainTextObject;
import org.eclipse.scava.nlp.tools.plaintext.utils.PlainTextVerticalSplit;
import org.eclipse.scava.nlp.tools.plaintext.utils.ReplyLineDetection;
import org.eclipse.scava.nlp.tools.preprocessor.htmlparser.HtmlParser;

public class PlainTextEclipseForums
{
	private static Pattern escapedNewline;
	
	static
	{
		escapedNewline = Pattern.compile("(\\\\n|\\\\r)+");
	}
	
	public static PlainTextObject process(String text)
	{
		text=escapedNewline.matcher(text).replaceAll("\n");
		text=String.join("\n", HtmlParser.parse(text));
		IntermadiatePlainTextObject intermadiatePlainTextObject= ReplyLineDetection.process(text);
		return new PlainTextObject(PlainTextVerticalSplit.process(intermadiatePlainTextObject.getPlainText()),intermadiatePlainTextObject.hadReplies());
	}
	
}
