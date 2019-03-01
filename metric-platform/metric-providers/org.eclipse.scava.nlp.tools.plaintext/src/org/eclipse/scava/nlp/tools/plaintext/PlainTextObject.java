/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.plaintext;

import java.util.List;

public class PlainTextObject
{
	private List<String> plainText;
	private boolean hadReplies;
	
	public PlainTextObject(List<String> plainText)
	{
		this.plainText=plainText;
		this.hadReplies=false;
	}
	
	public PlainTextObject(List<String> plainText, boolean hadReplyCharacter)
	{
		this.plainText=plainText;
		this.hadReplies=hadReplyCharacter;
	}

	public List<String> getPlainTextAsList()
	{
		return plainText;
	}
	
	public String getPlainTextAsString()
	{
		return String.join(" ", plainText);
	}

	public boolean hadReplies()
	{
		return hadReplies;
	}
	
	
}
