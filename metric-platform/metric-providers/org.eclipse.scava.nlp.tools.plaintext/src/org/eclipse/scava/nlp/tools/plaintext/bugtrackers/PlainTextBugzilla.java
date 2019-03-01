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

import java.util.Arrays;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.plaintext.PlainTextObject;

public class PlainTextBugzilla
{
	private static Pattern horizontalSpacing;
	
	static
	{
		horizontalSpacing=Pattern.compile("\\h\\h+");
	}
	
	//This method should be temporal while the new reader for Bugzilla arrives
	public static PlainTextObject process(String text)
	{
		return new PlainTextObject(Arrays.asList(horizontalSpacing.split(text)));
	}
			
}
