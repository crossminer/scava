/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.license.processing;

import java.util.Locale;
import static java.nio.charset.StandardCharsets.*;

public class TextProcessor {
	
	private String processedText;
	
	public TextProcessor(String text) {
		
		text = text.toLowerCase(Locale.ENGLISH); //eng
		text = new String(text.getBytes(ISO_8859_1), UTF_8); //encode to UTF8
		processedText = text;
	}
	
	
	public String getProcessedText() {
		
		
		return processedText;
	}

}
