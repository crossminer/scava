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

public class PlainTextNewsgroups
{
	private static Pattern newlines;
	
	static
	{
		newlines= Pattern.compile("\r\n", Pattern.MULTILINE);
	}
	
	public static PlainTextObject process(String text)
	{
		text=newlines.matcher(text).replaceAll("\n");
		IntermadiatePlainTextObject intermadiatePlainTextObject= ReplyLineDetection.process(text);
		text=intermadiatePlainTextObject.getPlainText();
		return new PlainTextObject(PlainTextVerticalSplit.process(text), intermadiatePlainTextObject.hadReplies());
	}
}
