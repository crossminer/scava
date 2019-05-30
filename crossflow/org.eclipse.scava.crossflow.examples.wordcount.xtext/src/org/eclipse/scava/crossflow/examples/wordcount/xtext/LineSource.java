package org.eclipse.scava.crossflow.examples.wordcount.xtext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.eclipse.scava.crossflow.examples.wordcount.xtext.Line;
import org.eclipse.scava.crossflow.examples.wordcount.xtext.LineSourceBase;

public class LineSource extends LineSourceBase {
	
	@Override
	public void produce() throws Exception {
		//System.out.println("producer started...");
		
		File file = new File(workflow.getInputDirectory(), "a-tale-of-two-cities-short.txt").getAbsoluteFile();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = reader.readLine()) != null) {
			//System.out.println("source sending: "+line);
			
			sendToLines(new Line(line));
		}
		reader.close();
		
		//System.out.println("producer finished...");
	}

}