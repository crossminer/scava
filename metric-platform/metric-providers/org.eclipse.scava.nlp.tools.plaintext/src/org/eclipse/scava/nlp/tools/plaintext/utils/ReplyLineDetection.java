/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.plaintext.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplyLineDetection
{
	private static Pattern replyLine;
	
	static
	{
		replyLine = Pattern.compile("^\\h*>+.*$", Pattern.MULTILINE);
	}
	
	public static IntermadiatePlainTextObject process(String text)
	{
		Matcher m = replyLine.matcher(text);
		boolean reply=false;
		if(m.find())
		{
			reply=true;
			text=m.replaceAll("");
		}
		return new IntermadiatePlainTextObject(text, reply);
	}
}
