/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.core;

import edu.emory.mathcs.nlp.decode.DecodeConfig;
import edu.emory.mathcs.nlp.decode.NLPDecoder;


class NLPCoreAnalyzerSingleton
{
	private static NLPCoreAnalyzerSingleton singleton = new NLPCoreAnalyzerSingleton();
	private static NLPDecoder decoder;
	
	private NLPCoreAnalyzerSingleton()
	{
		String configurationFile = "/edu/emory/mathcs/nlp/configuration/config-decode-pos.xml";
		decoder = new NLPDecoder(new DecodeConfig(NLPCoreAnalyzerSingleton.class.getResourceAsStream(configurationFile)));
	}
	
	public static NLPCoreAnalyzerSingleton getInstance()
	{
		return singleton;
	}
	
	public NLPDecoder getDecoder()
	{
		return decoder;
	}
	
}
