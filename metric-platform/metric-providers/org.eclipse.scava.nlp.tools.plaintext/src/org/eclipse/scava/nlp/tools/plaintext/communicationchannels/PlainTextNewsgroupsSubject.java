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

import org.eclipse.scava.nlp.tools.preprocessor.normalizer.Normalizer;

public class PlainTextNewsgroupsSubject
{
	private static Pattern subject1;
	private static Pattern subject2;
	private static Pattern subject3;
	private static Pattern subject4;
	
	static
	{
		subject1= Pattern.compile("= =");
		subject2= Pattern.compile("=\\?utf-8\\?Q\\?");
		subject3= Pattern.compile("\\?=");
		subject4= Pattern.compile("=20");
	}
	
	public static String process(String subject)
	{
		subject=subject1.matcher(subject).replaceAll("==");
		subject=subject2.matcher(subject).replaceAll("");
		subject=subject3.matcher(subject).replaceAll("");
		subject=subject4.matcher(subject).replaceAll(" ");
		return Normalizer.normalize(subject);
	}
}
