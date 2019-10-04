/*******************************************************************************
 * Copyright (c) 2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributor(s):
 *      Patrick Neubauer - initial API and implementation
 ******************************************************************************/
package org.eclipse.scava.crossflow.examples.wordcount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.eclipse.scava.crossflow.examples.wordcount.Line;
import org.eclipse.scava.crossflow.examples.wordcount.LineSourceBase;

/**
 * Produces lines of text by reading a text file containing the content of the book "A Tale of Two Cities" by Charles Dickens (shortened to 3690 lines).
 * 
 * @author Patrick Neubauer
 *
 */
public class LineSource extends LineSourceBase {
	
	@Override
	public void produce() throws Exception {
		System.out.println("producer started...");
		
		File file = new File(workflow.getInputDirectory(), "a-tale-of-two-cities-short.txt").getAbsoluteFile();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = reader.readLine()) != null) {
			sendToLines(new Line(line));
		}
		reader.close();
		
		System.out.println("producer finished...");
	}// produce

}// LineSource