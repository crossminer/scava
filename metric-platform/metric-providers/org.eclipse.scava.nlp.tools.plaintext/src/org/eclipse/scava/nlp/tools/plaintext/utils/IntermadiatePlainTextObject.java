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

public class IntermadiatePlainTextObject
{
	private String plainText;
	private boolean hadReplies;
	
	public IntermadiatePlainTextObject(String plainText, boolean hadReplyCharacter)
	{
		this.plainText=plainText;
		this.hadReplies=hadReplyCharacter;
	}
	
	public String getPlainText()
	{
		return plainText;
	}

	public boolean hadReplies()
	{
		return hadReplies;
	}
}
