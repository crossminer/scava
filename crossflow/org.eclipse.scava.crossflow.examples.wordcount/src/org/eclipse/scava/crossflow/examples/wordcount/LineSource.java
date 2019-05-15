package org.eclipse.scava.crossflow.examples.wordcount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.eclipse.scava.crossflow.examples.wordcount.Line;
import org.eclipse.scava.crossflow.examples.wordcount.LineSourceBase;

public class LineSource extends LineSourceBase {
	
	@Override
	public void produce() throws Exception {
		File file = new File("a-tale-of-two-cities.txt").getAbsoluteFile();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = reader.readLine()) != null) {
			sendToLines(new Line(line));
		}
		reader.close();
	}

}